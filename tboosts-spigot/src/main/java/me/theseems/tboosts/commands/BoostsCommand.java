package me.theseems.tboosts.commands;

import me.theseems.tboosts.Main;
import me.theseems.tboosts.commands.subs.BoostsGiveSub;
import me.theseems.tboosts.commands.subs.BoostsListSub;
import me.theseems.tboosts.commands.subs.MetaGetSub;
import me.theseems.tboosts.commands.subs.MetaRemoveSub;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BoostsCommand extends SubHost {

  public BoostsCommand() {
    attach("list", new BoostsListSub());
    attach("give", new BoostsGiveSub());
    attach("mtest", new MetaGetSub());
    attach("mrem", new MetaRemoveSub());
  }

  private void sendBanner(CommandSender sender) {
    sender.sendMessage("§c§lTBoosts §fby TheSeems<me@theseems.ru> §7v" + Main.getPlugin().getDescription().getVersion());
  }

  @Override
  public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
    propagate(commandSender, strings);
    return true;
  }

  @Override
  public void onNotFound(CommandSender sender) {
    sendBanner(sender);
  }

  @Override
  public void onPermissionLack(CommandSender sender, String node) {
    sendBanner(sender);
  }
}
