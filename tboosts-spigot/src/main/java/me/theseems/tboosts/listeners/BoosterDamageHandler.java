package me.theseems.tboosts.listeners;

import me.theseems.tboosts.Main;
import me.theseems.tboosts.TBoostsAPI;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Arrays;
import java.util.List;

public class BoosterDamageHandler implements Listener {
  private final List<String> names = Arrays.asList("hulk-1", "hulk-2", "hulk-3", "hulk-4");

  public void init() {
    Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
  }

  @EventHandler
  public void onDamage(EntityDamageByEntityEvent e) {
    if (e.isAsynchronous() || !(e.getDamager() instanceof Player)) return;

    Player player = (Player) e.getDamager();
    for (String boosterName :
        TBoostsAPI.getBoosterStorage().getBoosterNames(player.getUniqueId())) {

      if (names.contains(boosterName)) {
        TBoostsAPI.getBoosterStorage()
            .getBooster(player.getUniqueId(), boosterName)
            .ifPresent(
                booster -> {
                  e.setDamage(
                      e.getFinalDamage() * (booster.getMeta().getDouble("amount").orElse(1D)));
                  e.getEntity()
                      .getLocation()
                      .getWorld()
                      .playEffect(
                          e.getEntity().getLocation().add(0.0D, .3D, 0.0D),
                          Effect.STEP_SOUND,
                          Material.REDSTONE_BLOCK);
                });
        break;
      }
    }
  }
}
