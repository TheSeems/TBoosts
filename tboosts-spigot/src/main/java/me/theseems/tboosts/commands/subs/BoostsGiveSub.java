package me.theseems.tboosts.commands.subs;

import me.theseems.tboosts.Booster;
import me.theseems.tboosts.TBoostsAPI;
import me.theseems.tboosts.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class BoostsGiveSub implements SubCommand {
  @Override
  public void pass(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("§7Укажите бустер и игрока, которому хотите его выдать.");
      return;
    }

    UUID player;
    String booster = args[0];

    if (args.length == 1) {
      if (!(sender instanceof Player)) {
        sender.sendMessage(
            "§7Укажите ник игрока, которому хотите выдать ему бустер. (Вы не игрок)");
        return;
      }

      player = ((Player) sender).getUniqueId();
    } else {
      Player actual = Bukkit.getPlayer(args[1]);
      if (actual == null) {
        sender.sendMessage("§7Этот игрок оффлайн");
        return;
      }

      player = actual.getUniqueId();
    }

    Optional<Booster> optionalBooster = TBoostsAPI.getBoosterManager().produce(player, booster);
    if (!optionalBooster.isPresent()) {
      sender.sendMessage("§7Бустер §6'" + booster + "' §7не найден в системе");
      return;
    }

    sender.sendMessage(
            "§aВыдан бустер '"
                    + booster
                    + "' игроку "
                    + Objects.requireNonNull(Bukkit.getPlayer(player)).getDisplayName());
    Booster actual = optionalBooster.get();
    TBoostsAPI.getBoosterStorage().add(actual);

    actual.apply();
  }

  @Override
  public String getPermission() {
    return "tboosts.give";
  }
}
