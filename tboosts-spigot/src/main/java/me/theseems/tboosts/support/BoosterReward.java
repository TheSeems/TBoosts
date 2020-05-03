package me.theseems.tboosts.support;

import me.theseems.tboosts.TBoostsAPI;
import me.theseems.tcrates.CrateMeta;
import me.theseems.tcrates.rewards.IconReward;
import me.theseems.tcrates.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BoosterReward implements IconReward {
  private CrateMeta meta;
  private ItemStack stack;

  @Override
  public void give(UUID uuid) {
    String name = meta.getString("booster").orElse("<NONE>");
    Player player = Bukkit.getPlayer(uuid);
    if (player == null) {
      System.out.println("No player found to give booster " + name);
      return;
    }

    TBoostsAPI.getBoosterManager()
            .produce(uuid, name)
            .ifPresentOrElse(
                    booster -> {
                      booster.apply();
                      TBoostsAPI.getBoosterStorage().add(booster);
                      player.sendTitle(
                              "Вам выдан бустер",
                              booster.getMeta().getString("display_name").orElse(booster.getName()),
                              10,
                              10,
                              10);
                    },
                    () -> {
                      String link =
                              Utils.encode(
                                      new IllegalStateException(
                                              "Cannot find booster '" + name + "' for player '" + player + "'"));

                      TextComponent textComponent =
                              new TextComponent("§7Не можем выдать награду (бустер).");

                      TextComponent linkComponent = new TextComponent("§c§l[СООБЩИТЬ ОБ ОШИБКЕ]");
                      linkComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, link));
                      player.spigot().sendMessage(textComponent);
                      player.spigot().sendMessage(linkComponent);
                    });
  }

  @Override
  public String getName() {
    return meta.getString("booster").orElse("<NONE>");
  }

  @Override
  public CrateMeta getMeta() {
    return meta;
  }

  @Override
  public void setMeta(CrateMeta crateMeta) {
    this.meta = crateMeta;
  }

  @Override
  public ItemStack getIcon(UUID uuid) {
    return stack;
  }

  public void setStack(ItemStack stack) {
    this.stack = stack;
  }
}
