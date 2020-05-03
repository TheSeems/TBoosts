package me.theseems.tboosts.commands.subs;

import me.theseems.tboosts.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHealthSub implements SubCommand {
    @Override
    public void pass(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Недоступно.");
        } else if (args.length != 0) {
            ((Player) sender).setHealth(Integer.parseInt(args[0]));
            sender.sendMessage("§7Установлено " + Integer.parseInt(args[0]) + " §8(" + ((Player) sender).getHealth() + ")");
        } else {
            sender.sendMessage("§7Укажите количество здоровья");
        }
    }

    @Override
    public String getPermission() {
        return "tboosts.health";
    }
}
