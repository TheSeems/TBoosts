package me.theseems.tboosts.storage;

import me.theseems.tboosts.BoosterMeta;
import me.theseems.tboosts.Main;
import me.theseems.tboosts.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class SpigotBoosterMeta implements BoosterMeta {
  private static final String META_PREFIX = "tbbl-meta";

  private Player player;
  private String prefix;

  public SpigotBoosterMeta(Player player, String name) {
    this.player = player;
    this.prefix = META_PREFIX + "-" + name;
  }

  public void remove() {
    for (String key : getKeys()) {
      System.out.println(">> Removed meta '" + prefix + key + "'");
      player.removeMetadata(prefix + key, Main.getPlugin());
    }
    player.removeMetadata(prefix + "$-1", Main.getPlugin());
  }

  @Override
  public void set(String key, Object value) {
    player.setMetadata(prefix + key, new FixedMetadataValue(Main.getPlugin(), value));
    System.out.println(prefix + key + " => " + value);
    System.out.println(prefix + "$-l" + " => " + Utils.StringList.ofMeta(player, prefix + "$-l"));
    Utils.StringList.ofMeta(player, prefix + "$-l").add(key).setAsMeta();
  }

  @Override
  public Optional<Object> get(String key) {
    if (!player.hasMetadata(prefix + key))
      return Optional.empty();

    List<MetadataValue> valueList = player.getMetadata(prefix + key);
    return Optional.ofNullable(valueList.iterator().next().value());
  }

  @Override
  public Optional<Integer> getInteger(String key) {
    if (!player.hasMetadata(prefix + key))
      return Optional.empty();

    List<MetadataValue> valueList = player.getMetadata(prefix + key);
    return Optional.of(valueList.iterator().next().asInt());
  }

  @Override
  public Optional<Double> getDouble(String key) {
    if (!player.hasMetadata(prefix + key))
      return Optional.empty();

    List<MetadataValue> valueList = player.getMetadata(prefix + key);
    return Optional.of(valueList.iterator().next().asDouble());
  }

  @Override
  public Optional<String> getString(String key) {
    if (!player.hasMetadata(prefix + key))
      return Optional.empty();

    List<MetadataValue> valueList = player.getMetadata(prefix + key);
    return Optional.of(valueList.iterator().next().asString());
  }

  @Override
  public Collection<String> getKeys() {
    return Utils.StringList.ofMeta(player, prefix + "$-l").getStrings();
  }

  @Override
  public void remove(String key) {
    player.removeMetadata(prefix + key, Main.getPlugin());
    Utils.StringList.ofMeta(player, prefix + "$-l").remove(key).setAsMeta();
  }
}
