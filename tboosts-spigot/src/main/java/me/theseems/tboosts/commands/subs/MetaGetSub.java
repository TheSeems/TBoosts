package me.theseems.tboosts.commands.subs;

import me.theseems.tboosts.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;

import java.util.List;

public class MetaGetSub implements SubCommand {
  @Override
  public void pass(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("§7Please, specify key in order to get meta");
      return;
    }

    Player player = (Player) sender;
    List<MetadataValue> valueList = player.getMetadata(args[0]);
    sender.sendMessage("§7Results: §6" + valueList.size());
    valueList.forEach(metadataValue -> sender.sendMessage("§8» §7" + metadataValue.value()));
  }

  @Override
  public String getPermission() {
    return "tboosts.*";
  }

  @Override
  public boolean allowConsole() {
    return false;
  }
}
