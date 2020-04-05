package me.theseems.tboosts.boosters;

import me.theseems.tboosts.*;
import me.theseems.tboosts.storage.SpigotBoosterMeta;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.stream.Collectors;

public abstract class HealthBooster implements TemporaryBooster, Booster {
  private BoosterMeta meta;
  private UUID player;

  @Override
  public void apply() {
    Player actual = Bukkit.getPlayer(player);
    if (actual == null) return;

    if (actual.hasPermission("tb.debug"))
      actual.sendMessage(
          ChatColor.BLUE
              + getName()
              + " # Apply with meta: "
              + meta.getKeys().stream()
                  .map(s -> s + " --> " + meta.get(s))
                  .collect(Collectors.joining(" ; ")));
    actual
        .getAttribute(Attribute.GENERIC_MAX_HEALTH)
        .setBaseValue(getMeta().getDouble("amount").orElse(20D));
  }

  @Override
  public void take() {
    Player actual = Bukkit.getPlayer(player);
    if (actual == null) return;

    actual.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
    if (actual.hasPermission("tb.debug"))
      actual.sendMessage(ChatColor.BLUE + getName() + " # Take");

    if (meta instanceof SpigotBoosterMeta) {
      if (actual.hasPermission("tb.debug"))
        actual.sendMessage(
            ChatColor.BLUE + getName() + " # Spigot meta removed. Were " + meta.getKeys());
      ((SpigotBoosterMeta) meta).remove();
    } else {
      for (String key : meta.getKeys()) {
        if (actual.hasPermission("tb.debug"))
          actual.sendMessage(ChatColor.BLUE + getName() + " # From other meta removed: " + key);
        meta.remove(key);
      }
    }
  }

  private void initMeta(UUID player, double amount) {
    meta = new SpigotBoosterMeta(Bukkit.getPlayer(player), getName());
    if (!getMeta().getKeys().contains("amount")) getMeta().set("amount", amount);
    if (!getMeta().getKeys().contains("date")) getMeta().set("date", System.currentTimeMillis());
    if (!getMeta().getKeys().contains("expire")) getMeta().set("expire", 30);
    if (!getMeta().getKeys().contains("display_name"))
      getMeta().set("display_name", "§4§lСЕРДЦЕ ГЕРОЯ");
  }

  public HealthBooster(UUID player) {
    this.player = player;
  }

  @Override
  public BoosterMeta getMeta() {
    return meta;
  }

  @Override
  public void setMeta(BoosterMeta meta) {
    this.meta = meta;
  }

  @Override
  public void setPlayer(UUID player) {
    if (this.player == null) {
      this.player = player;
      initMeta(player, getMeta().getDouble("amount").orElse(20D));
    } else if (player != this.player) {
      throw new IllegalStateException("Cannot apply booster to player other than " + player);
    }
  }

  @Override
  public UUID getPlayer() {
    return player;
  }

  @Override
  public void onExpire(UUID player) {
    Player actual = Bukkit.getPlayer(player);
    if (actual == null) return;

    take();
    TBoostsAPI.getBoosterStorage().remove(player, getName());
  }

  @Override
  public int getExpireSeconds(UUID player) {
    return getMeta().getInteger("expire").orElse(30);
  }
}
