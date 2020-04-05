package me.theseems.tboosts.commands.subs;

import me.theseems.tboosts.Main;
import me.theseems.tboosts.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MetaRemoveSub implements SubCommand {
  @Override
  public void pass(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("§7Покажуйста, укажите мета-ключ");
      return;
    }

    Player actual = (Player) sender;
    if (actual.hasMetadata(args[0])) {
      sender.sendMessage("§7Удалено значение по мета-ключу §6'" + args[0] + "' §7: " + actual.getMetadata(args[0]).toString());
      actual.removeMetadata(args[0], Main.getPlugin());
    } else {
      sender.sendMessage("§7Для вас не найдено значения по мета-ключу §6'" + args[0] + "'");
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
