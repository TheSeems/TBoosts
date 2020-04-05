package me.theseems.tboosts;

import java.util.UUID;

public interface TemporaryBooster extends Booster {
  void onExpire(UUID player);
  int getExpireSeconds(UUID player);
}
