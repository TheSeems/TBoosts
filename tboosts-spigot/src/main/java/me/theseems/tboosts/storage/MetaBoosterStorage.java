package me.theseems.tboosts.storage;

import me.theseems.tboosts.Booster;
import me.theseems.tboosts.BoosterStorage;
import me.theseems.tboosts.Main;
import me.theseems.tboosts.TBoostsAPI;
import me.theseems.tboosts.events.BoosterGiveEvent;
import me.theseems.tboosts.events.BoosterTakeEvent;
import me.theseems.tboosts.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class MetaBoosterStorage implements BoosterStorage {

  Optional<Booster> get(UUID player, String string) {
    Player actual = Bukkit.getPlayer(player);
    if (actual == null)
      return Optional.empty();

    Optional<Booster> optionalBooster = TBoostsAPI.getBoosterManager().produce(player, string);
    if (!optionalBooster.isPresent()) {
      return Optional.empty();
    }

    Booster booster = optionalBooster.get();
    booster.setMeta(new SpigotBoosterMeta(actual, booster.getName()));
    return Optional.of(booster);
  }

  @Override
  public Collection<Booster> getBoosters(UUID player) {
    Player actual = Bukkit.getPlayer(player);
    if (actual == null)
      return Collections.emptyList();

    List<Booster> boosters = new ArrayList<>();
    for (String string : Utils.StringList.ofMeta(actual, "tbbl-list").getStrings()) {
      Optional<Booster> booster = get(player, string);
      if (!booster.isPresent())
        continue;

      boosters.add(booster.get());
    }

    return boosters;
  }

  @Override
  public boolean hasBooster(UUID player, String name) {
    Player actual = Bukkit.getPlayer(player);
    if (actual == null)
      return false;

    return Utils.StringList.ofMeta(actual, "tbbl-list").getStrings().contains(name);
  }

  @Override
  public Collection<String> getBoosterNames(UUID player) {
    Player actual = Bukkit.getPlayer(player);
    if (actual == null)
      return Collections.emptyList();

    return Utils.StringList.ofMeta(actual, "tbbl-list").getStrings();
  }

  @Override
  public Optional<Booster> getBooster(UUID player, String name) {
    Player actual = Bukkit.getPlayer(player);
    if (actual == null)
      return Optional.empty();

    return get(player, name);
  }

  @Override
  public void add(Booster booster) {
    Player actual = Bukkit.getPlayer(booster.getPlayer());
    if (actual == null)
      return;

    BoosterGiveEvent event = new BoosterGiveEvent(booster);
    Main.getPlugin().getServer().getPluginManager().callEvent(event);

    if (!event.isCancelled()) {
      booster.setPlayer(booster.getPlayer());
      System.out.println("[Meta] Adding " + booster.getName() + " to a " + actual.getName());
      Utils.StringList.ofMeta(actual, "tbbl-list").add(booster.getName()).setAsMeta();
    }
  }

  @Override
  public void remove(UUID player, String booster) {
    Player actual = Bukkit.getPlayer(player);
    if (actual == null)
      return;

    if (!Utils.StringList.ofMeta(actual, "tbbl-list").getStrings().contains(booster))
      return;

    //noinspection OptionalGetWithoutIsPresent
    Booster bstr = get(player, booster).get();
    BoosterTakeEvent event = new BoosterTakeEvent(bstr);
    Main.getPlugin().getServer().getPluginManager().callEvent(event);

    System.out.println("[Meta] Removing " + booster + " from a " + actual.getName());
    Utils.StringList.ofMeta(actual, "tbbl-list").remove(booster).setAsMeta();
  }


}
