package me.theseems.tboosts;

import java.util.Optional;
import java.util.UUID;

public interface BoosterManager {
  /**
   * Register factory to a manager
   *
   * @param factory to register
   */
  void register(BoosterFactory factory);

  /**
   * Unregister factory by it's name from manager
   *
   * @param name of factory
   */
  void unregister(String name);

  /**
   * Produce booster
   *
   * @param name of booster to product
   * @param player to produce for
   * @return optional of produced booster
   */
  Optional<Booster> produce(UUID player, String name);
}
