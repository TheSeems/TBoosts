package me.theseems.tboosts.boosters;

import me.theseems.tboosts.*;
import me.theseems.tboosts.storage.SpigotBoosterMeta;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class DamageBooster implements TemporaryBooster, Booster {
  private BoosterMeta meta;
  private UUID player;

  @Override
  public void apply() {
    Player actual = Bukkit.getPlayer(player);
    if (actual == null) return;

    actual
        .getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)
        .setBaseValue(getMeta().getDouble("amount").orElse(1D));
  }

  @Override
  public void take() {
    Player actual = Bukkit.getPlayer(player);
    if (actual == null) return;

    actual.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(1D);
    actual.sendMessage(">?> TAKE!");
    if (meta instanceof SpigotBoosterMeta) {
      ((SpigotBoosterMeta) meta).remove();
    } else for (String key : meta.getKeys()) {
      meta.remove(key);
    }
  }

  private void initMeta(UUID player, double amount) {
    meta = new SpigotBoosterMeta(Bukkit.getPlayer(player), getName());
    if (!getMeta().getKeys().contains("amount")) getMeta().set("amount", amount);
    if (!getMeta().getKeys().contains("date")) getMeta().set("date", System.currentTimeMillis());
  }

  public DamageBooster(UUID player) {
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
      initMeta(player, getMeta().getDouble("amount").orElse(1D));
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
