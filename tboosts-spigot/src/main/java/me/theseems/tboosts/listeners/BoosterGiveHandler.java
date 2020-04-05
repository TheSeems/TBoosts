package me.theseems.tboosts.listeners;

import me.theseems.tboosts.Booster;
import me.theseems.tboosts.Main;
import me.theseems.tboosts.TBoostsAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class BoosterGiveHandler implements Listener {
  @EventHandler(priority = EventPriority.LOWEST)
  public void onJoin(PlayerJoinEvent e) {
    UUID player = e.getPlayer().getUniqueId();
    for (Booster booster : TBoostsAPI.getBoosterStorage().getBoosters(player)) {
      booster.apply();
    }
  }

  public void init() {
    Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
  }
}
