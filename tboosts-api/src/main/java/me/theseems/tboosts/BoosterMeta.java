package me.theseems.tboosts;

import java.util.Collection;
import java.util.Optional;

public interface BoosterMeta {
  void set(String key, Object value);

  Optional<Object> get(String key);
  Optional<Integer> getInteger(String key);
  Optional<Double> getDouble(String key);
  Optional<String> getString(String key);

  Collection<String> getKeys();

  void remove(String key);
}
