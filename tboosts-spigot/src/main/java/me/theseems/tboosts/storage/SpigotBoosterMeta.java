package me.theseems.tboosts.storage;

import me.theseems.tboosts.BoosterMeta;
import me.theseems.tboosts.Main;
import me.theseems.tboosts.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SpigotBoosterMeta implements BoosterMeta {
    private static final String META_PREFIX = "tbbl-meta";

    private final Player player;
    private final String prefix;

    public SpigotBoosterMeta(Player player, String name) {
        this.player = player;
        this.prefix = META_PREFIX + "-" + name;
    }

    public void remove() {
        if (player == null) return;

        for (String key : getKeys()) {
            player.removeMetadata(prefix + key, Main.getPlugin());
        }
        player.removeMetadata(prefix + "$-l", Main.getPlugin());
    }

  @Override
  public void set(String key, Object value) {
      if (player == null) return;

      player.setMetadata(prefix + key, new FixedMetadataValue(Main.getPlugin(), value));
      Utils.StringList.ofMeta(player, prefix + "$-l").add(key).setAsMeta();
  }

  @Override
  public Optional<Object> get(String key) {
      if (player == null) return Optional.empty();

      if (!player.hasMetadata(prefix + key)) return Optional.empty();

      List<MetadataValue> valueList = player.getMetadata(prefix + key);
      return Optional.ofNullable(valueList.iterator().next().value());
  }

  @Override
  public Optional<Integer> getInteger(String key) {
      if (player == null) return Optional.empty();

      if (!player.hasMetadata(prefix + key)) return Optional.empty();

      List<MetadataValue> valueList = player.getMetadata(prefix + key);
      return Optional.of(valueList.iterator().next().asInt());
  }

  @Override
  public Optional<Double> getDouble(String key) {
      if (player == null) return Optional.empty();

      if (!player.hasMetadata(prefix + key)) return Optional.empty();

      List<MetadataValue> valueList = player.getMetadata(prefix + key);
      return Optional.of(valueList.iterator().next().asDouble());
  }

  @Override
  public Optional<String> getString(String key) {
      if (player == null) return Optional.empty();

      if (!player.hasMetadata(prefix + key)) return Optional.empty();

      List<MetadataValue> valueList = player.getMetadata(prefix + key);
      return Optional.of(valueList.iterator().next().asString());
  }

  @Override
  public Collection<String> getKeys() {
      if (player == null) return Collections.emptyList();
    return Utils.StringList.ofMeta(player, prefix + "$-l").getStrings();
  }

  @Override
  public void remove(String key) {
      if (player == null) return;

      player.removeMetadata(prefix + key, Main.getPlugin());
      Utils.StringList.ofMeta(player, prefix + "$-l").remove(key).setAsMeta();
  }
}
