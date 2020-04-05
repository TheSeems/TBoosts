package me.theseems.tboosts;

import java.util.UUID;

public interface Booster {
  /**
   * Get booster name
   *
   * @return name
   */
  String getName();

  /**
   * Apply booster for player
   */
  void apply();

  /**
   * Take booster from player
   */
  void take();

  /**
   * Get booster meta
   *
   * @return meta
   */
  BoosterMeta getMeta();

  /**
   * Set booster meta
   *
   * @param meta to set
   */
  void setMeta(BoosterMeta meta);

  /**
   * Set player, owner of this booster
   *
   * @param player to set
   */
  void setPlayer(UUID player);

  /**
   * Get player, owner of this booster
   *
   * @return owner
   */
  UUID getPlayer();
}
