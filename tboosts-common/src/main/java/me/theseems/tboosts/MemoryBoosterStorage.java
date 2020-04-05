package me.theseems.tboosts;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MemoryBoosterStorage implements BoosterStorage {
  private Map<UUID, Collection<Booster>> players;

  public MemoryBoosterStorage() {
    players = new ConcurrentHashMap<>();
  }

  @Override
  public Collection<Booster> getBoosters(UUID player) {
    return players.getOrDefault(player, Collections.emptyList());
  }

  @Override
  public boolean hasBooster(UUID player, String name) {
    if (!players.containsKey(player)) return false;
    return players.get(player).stream()
        .anyMatch(booster -> booster.getName().equals(booster.getName()));
  }

  @Override
  public Collection<String> getBoosterNames(UUID player) {
    if (!players.containsKey(player)) return Collections.emptyList();
    return players.get(player).stream().map(Booster::getName).collect(Collectors.toList());
  }

  @Override
  public Optional<Booster> getBooster(UUID player, String name) {
    if (!players.containsKey(player))
      return Optional.empty();
    for (Booster booster : players.get(player)) {
      if (booster.getName().equals(name))
        return Optional.of(booster);
    }
    return Optional.empty();
  }

  @Override
  public void add(Booster booster) {
    if (!players.containsKey(booster.getPlayer())) {
      players.put(
          booster.getPlayer(),
          new ArrayList<Booster>() {
            {
              add(booster);
            }
          });
    } else players.get(booster.getPlayer()).add(booster);
  }

  @Override
  public void remove(UUID player, String booster) {
    if (!players.containsKey(player)) return;
    players.get(player).removeIf(booster1 -> booster1.getName().equals(booster));
  }
}
