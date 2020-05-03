package me.theseems.tboosts.features;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.theseems.tboosts.TBoostsAPI;
import me.theseems.tboosts.TemporaryBooster;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoosterExpansion extends PlaceholderExpansion {
  @Override
  public String getIdentifier() {
    return "tboosts";
  }

  @Override
  public String getAuthor() {
    return "TheSeems<me@theseems.ru>";
  }

  @Override
  public String getVersion() {
    return "0.1D";
  }

  private String format(long secs) {
    long minutes = secs / 60;
    long seconds = secs % 60;
    String minutesString = (minutes >= 10 ? "" + minutes : "0" + minutes);
    String secondsString = (seconds >= 10 ? "" + seconds : "0" + seconds);
    return minutesString + ":" + secondsString;
  }

  @Override
  public String onPlaceholderRequest(Player p, String params) {
    if (params.equals("list")) {
      List<String> boosters = new ArrayList<>();
      TBoostsAPI.getBoosterStorage()
          .getBoosters(p.getUniqueId())
          .forEach(
              booster ->
                  boosters.add(
                      booster
                              .getMeta()
                              .getString("display_name")
                              .orElse("§e" + booster.getName())));
      StringBuilder returnValue = new StringBuilder();
      for (String booster : boosters) {
        returnValue.append(booster).append(", ");
      }
      return returnValue.toString();
    }

    if (params.startsWith("list_temp")) {
      boolean flag = params.endsWith("@");
      List<String> boosters = new ArrayList<>();
      TBoostsAPI.getBoosterStorage()
              .getBoosters(p.getUniqueId())
              .forEach(
                      booster -> {
                        if (!(booster instanceof TemporaryBooster)) return;

                        Date were = new Date((Long) booster.getMeta().get("date").orElse(0L));
                        long secondsPast =
                                ChronoUnit.SECONDS.between(were.toInstant(), new Date().toInstant());
                        String name =
                                booster.getMeta().getString("display_name").orElse("§e" + booster.getName());

                        if (flag) {
                          String[] list = name.split(" ");
                          if (list.length == 1) {
                            name =
                                    ChatColor.getLastColors(list[0])
                                            + ChatColor.stripColor(list[0]).substring(0, 1);
                          } else {
                            name =
                                    ChatColor.getLastColors(list[0])
                                            + ChatColor.stripColor(list[0]).substring(0, 1)
                                            + list[list.length - 1];
                          }
                        }

                        boosters.add(
                                name
                                        + " §7: "
                                        + format(
                                        booster.getMeta().getInteger("expire").orElse(100) - secondsPast + 1));
                      });

      StringBuilder returnValue = new StringBuilder();
      for (String booster : boosters) {
        returnValue.append(booster).append("  ");
      }

      if (returnValue.length() > 2)
        returnValue.delete(returnValue.length() - 2, returnValue.length());

      return returnValue.toString();
    }

    if (params.startsWith("has")) {
      String name = params.substring(3);
      return TBoostsAPI.getBoosterStorage().hasBooster(p.getUniqueId(), name) ? "true" : "false";
    }

    return "§0<?>";
  }
}
