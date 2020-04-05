package me.theseems.tboosts.commands.subs;

import me.theseems.tboosts.Main;
import me.theseems.tboosts.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MetaRemoveSub implements SubCommand {
  @Override
  public void pass(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("§7Please, specify meta key to remove");
      return;
    }

    Player actual = (Player) sender;
    if (actual.hasMetadata(args[0])) {
      actual.removeMetadata(args[0], Main.getPlugin());
      sender.sendMessage("§aOk.");
    } else {
      sender.sendMessage("§7No metadata found by that key for you");
    }
  }

  @Override
  public String getPermission() {
    return "tboost.*";
  }

  @Override
  public boolean allowConsole() {
    return false;
  }
}
