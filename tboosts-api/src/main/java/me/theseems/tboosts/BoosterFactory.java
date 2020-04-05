package me.theseems.tboosts;

import java.util.UUID;

public interface BoosterFactory {
  /**
   * Get factory name
   * @return name
   */
  String getName();

  /**
   * Produce booster
   * @return booster
   */
  Booster produce(UUID player);
}
