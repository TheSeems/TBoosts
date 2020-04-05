package me.theseems.tboosts;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface BoosterStorage {
  /**
   * Get boosters for player
   *
   * @param player to get for
   * @return boosters
   */
  Collection<Booster> getBoosters(UUID player);

  /**
   * Check if player has a booster (by name)
   *
   * @param player to check
   * @param name to check for
   * @return player has a booster
   */
  boolean hasBooster(UUID player, String name);

  /**
   * Get booster names for player
   *
   * @param player to get boosters of
   * @return boosters names
   */
  Collection<String> getBoosterNames(UUID player);

  /**
   * Get booster by name
   *
   * @param player to get booster of
   * @param name of booster to get
   * @return optional of found booster
   */
  Optional<Booster> getBooster(UUID player, String name);

  /**
   * Store booster for player
   *
   * @param booster to store
   */
  void add(Booster booster);

  /**
   * Remove booster from player
   *
   * @param player to remove from
   * @param name to remove
   */
  void remove(UUID player, String name);
}
