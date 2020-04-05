package me.theseems.tboosts;

public class TBoostsAPI {
  private static BoosterStorage boosterStorage;
  private static BoosterManager boosterManager;

  public static BoosterManager getBoosterManager() {
    return boosterManager;
  }

  public static void setBoosterManager(BoosterManager boosterManager) {
    TBoostsAPI.boosterManager = boosterManager;
  }

  public static BoosterStorage getBoosterStorage() {
    return boosterStorage;
  }

  public static void setBoosterStorage(BoosterStorage boosterStorage) {
    TBoostsAPI.boosterStorage = boosterStorage;
  }
}
