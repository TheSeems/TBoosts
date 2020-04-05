package me.theseems.tboosts.listeners;

import me.theseems.tboosts.Booster;
import me.theseems.tboosts.Main;
import me.theseems.tboosts.TBoostsAPI;
import me.theseems.tboosts.TemporaryBooster;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

public class BoosterTimeHandler {
  private BukkitTask task;

  public void handleBooster(TemporaryBooster temporaryBooster, UUID player) {
    Date from = new Date((Long) temporaryBooster.getMeta().get("date").orElse(0L));
    Date now = new Date();
    if (ChronoUnit.SECONDS.between(from.toInstant(), now.toInstant()) > temporaryBooster.getExpireSeconds(player)) {
      temporaryBooster.onExpire(player);
    }
  }

  public void run() {
    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
      for (Booster booster : TBoostsAPI.getBoosterStorage().getBoosters(onlinePlayer.getUniqueId())) {
        if (!(booster instanceof TemporaryBooster)) {
          continue;
        }

        handleBooster((TemporaryBooster) booster, onlinePlayer.getUniqueId());
      }
    }
  }

  public void init() {
    task = Main.getPlugin()
      .getServer()
      .getScheduler()
      .runTaskTimer(Main.getPlugin(), this::run, 30, 30);
  }

  public BukkitTask getTask() {
    return task;
  }
}
