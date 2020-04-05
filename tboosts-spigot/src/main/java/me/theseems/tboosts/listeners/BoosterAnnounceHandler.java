package me.theseems.tboosts.listeners;

import me.theseems.tboosts.Main;
import me.theseems.tboosts.TemporaryBooster;
import me.theseems.tboosts.events.BoosterGiveEvent;
import me.theseems.tboosts.events.BoosterTakeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BoosterAnnounceHandler implements Listener {
  @EventHandler
  public void onGive(BoosterGiveEvent e) {
    System.out.println("[BAHandler] Event: " + e.toString());
    if (!e.isCancelled()) {
      Player player = Bukkit.getPlayer(e.getBooster().getPlayer());
      if (player == null) return;

      player.sendMessage(
          "§aПолучен бустер: §r"
              + e.getBooster()
                  .getMeta()
                  .getString("display_name")
                  .orElse(e.getBooster().getName()));
      if (e.getBooster() instanceof TemporaryBooster) {
        player.sendMessage(
            "§7Его действие закончится через: §e"
                + e.getBooster().getMeta().getInteger("expire").orElse(10)
                + "§7 секунд(ы)");
      }
    }
  }

  @EventHandler
  public void onTake(BoosterTakeEvent e) {
    System.out.println("[BAHandler] Event: " + e.toString());
    Player player = Bukkit.getPlayer(e.getBooster().getPlayer());
    if (player == null) return;

    player.sendMessage(
        "§cБустер больше не действует: §r"
            + e.getBooster().getMeta().getString("display_name").orElse(e.getBooster().getName()));
  }

  public void init() {
    Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
  }
}
