package me.theseems.tboosts.commands.subs;

import me.theseems.tboosts.Booster;
import me.theseems.tboosts.TBoostsAPI;
import me.theseems.tboosts.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;

public class BoostsListSub implements SubCommand {
  @Override
  public void pass(CommandSender sender, String[] args) {
    Player actual = (Player) sender;
    Collection<Booster> boosterList = TBoostsAPI.getBoosterStorage().getBoosters(actual.getUniqueId());

    actual.sendMessage("§7У вас имеется бустеров: §6" + boosterList.size());
    for (Booster booster : boosterList) {
      actual.sendMessage(ChatColor.GRAY + booster.getMeta().getString("display_name").orElse(booster.getName()));
    }
  }

  @Override
  public String getPermission() {
    return "tboosts.view";
  }

  @Override
  public boolean allowConsole() {
    return false;
  }
}
