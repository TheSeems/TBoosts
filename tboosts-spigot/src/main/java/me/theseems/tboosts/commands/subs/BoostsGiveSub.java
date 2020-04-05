package me.theseems.tboosts.commands.subs;

import me.theseems.tboosts.Booster;
import me.theseems.tboosts.TBoostsAPI;
import me.theseems.tboosts.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class BoostsGiveSub implements SubCommand {
  @Override
  public void pass(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("§7Please, specify player and booster in arguments");
      return;
    }

    UUID player;
    String booster;

    if (args.length == 1) {
      if (!(sender instanceof Player)) {
        sender.sendMessage("§7Please, specify player to give booster to");
        return;
      }

      player = ((Player) sender).getUniqueId();
      booster = args[0];
    } else {
      Player actual = Bukkit.getPlayer(args[0]);
      if (actual == null) {
        sender.sendMessage("§7Player is offline..");
        return;
      }

      player = actual.getUniqueId();
      booster = args[1];
    }

    Optional<Booster> optionalBooster = TBoostsAPI.getBoosterManager().produce(player, booster);
    if (!optionalBooster.isPresent()) {
      sender.sendMessage("§7Booster §6'" + booster + "' §7does not exist");
      return;
    }

    Booster actual = optionalBooster.get();
    TBoostsAPI.getBoosterStorage().add(actual);

    sender.sendMessage("§aВыдан бустер '" + actual.getName() + "'");
    actual.apply();
  }

  @Override
  public String getPermission() {
    return "tboosts.give";
  }
}
