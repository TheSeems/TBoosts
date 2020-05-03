package me.theseems.tboosts.listeners;

import me.theseems.tboosts.Booster;
import me.theseems.tboosts.Main;
import me.theseems.tboosts.TBoostsAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class BoosterImmortalHandler implements Listener {
  private final List<String> names = Arrays.asList("immo-1", "immo-2", "immo-3");

  public void init() {
    Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
  }

  @EventHandler
  public void onDamage(EntityDamageByEntityEvent e) {
    if (e.isAsynchronous() || !(e.getEntity() instanceof Player)) return;

    Player player = (Player) e.getEntity();
    final Booster[] calculated = {null};
    for (String boosterName :
            TBoostsAPI.getBoosterStorage().getBoosterNames(player.getUniqueId())) {

      if (names.contains(boosterName)) {
        TBoostsAPI.getBoosterStorage()
                .getBooster(player.getUniqueId(), boosterName)
                .ifPresent(
                        candidate -> {
                          if (calculated[0] == null) {
                            calculated[0] = candidate;
                          } else if (candidate.getMeta().getInteger("amount").orElse(0)
                                  > calculated[0].getMeta().getInteger("amount").orElse(0)) {
                            calculated[0] = candidate;
                          }
                        });
      }
    }

    Booster booster = calculated[0];
    if (booster == null) return;

    int current = booster.getMeta().getInteger("hits").orElse(0) + 1;
    player
            .spigot()
            .sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Удары: " + current));

    booster.getMeta().set("hits", current % 3);
    if (current == 3) {
      int hearts =
              new SecureRandom().nextInt(booster.getMeta().getInteger("amount").orElse(0) - 1) + 1;

      double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
      ((Player) e.getEntity()).setHealth(Math.min(player.getHealth() + hearts * 2.0, maxHealth));

      player
              .spigot()
              .sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7+ §c" + hearts + " ❤"));
    }
  }
}
