package me.theseems.tboosts.utils;

import me.theseems.tboosts.Main;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.*;

public class Utils {

  public static class StringList {
    private Set<String> strings;
    private String key;
    private Player player;

    public StringList(String value) {
      strings = new HashSet<>();
      if (value.equals(""))
        strings = new HashSet<>();
      else
        strings.addAll(Arrays.asList(value.split(";")));
    }

    public StringList add(String name) {
      strings.add(name);
      return this;
    }

    public StringList remove(String name) {
      strings.remove(name);
      return this;
    }

    public static StringList of(String old) {
      return new StringList(old);
    }

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    public Player getPlayer() {
      return player;
    }

    public void setPlayer(Player player) {
      this.player = player;
    }

    public static StringList ofMeta(Player player, String key) {
      StringList list;
      if (player != null && player.hasMetadata(key))
        list = StringList.of(player.getMetadata(key).iterator().next().asString());
      else
        list = new StringList("");

      list.setKey(key);
      list.setPlayer(player);
      return list;
    }

    public String get() {
      StringBuilder end = new StringBuilder();
      for (String string : strings) {
        end.append(string).append(";");
      }
      return end.toString();
    }

    public void setAsMeta(Player player, String key) {
      if (player == null)
        return;
      player.setMetadata(key, new FixedMetadataValue(Main.getPlugin(), get()));
    }

    public void setAsMeta() {
      assert player != null && key != null;
      setAsMeta(player, key);
    }

    public List<String> getStrings() {
      return new ArrayList<>(strings);
    }
  }
}
