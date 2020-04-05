package me.theseems.tboosts;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SimpleBoosterManager implements BoosterManager {
  private Map<String, BoosterFactory> factoryMap;

  public SimpleBoosterManager() {
    factoryMap = new HashMap<>();
  }

  @Override
  public void register(BoosterFactory factory) {
    if (factoryMap.containsKey(factory.getName()))
      throw new IllegalStateException("Attempt to register existing factory '" + factory.getName() + "'");
    factoryMap.put(factory.getName(), factory);
  }

  @Override
  public void unregister(String name) {
    factoryMap.remove(name);
  }

  @Override
  public Optional<Booster> produce(UUID player, String name) {
    if (!factoryMap.containsKey(name))
      return Optional.empty();
    return Optional.ofNullable(factoryMap.get(name).produce(player));
  }
}
