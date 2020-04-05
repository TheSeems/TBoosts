package me.theseems.tboosts;

import me.theseems.tboosts.boosters.DamageBooster;
import me.theseems.tboosts.boosters.HealthBooster;
import me.theseems.tboosts.commands.BoostsCommand;
import me.theseems.tboosts.features.BoosterExpansion;
import me.theseems.tboosts.listeners.BoosterAnnounceHandler;
import me.theseems.tboosts.listeners.BoosterGiveHandler;
import me.theseems.tboosts.listeners.BoosterTimeHandler;
import me.theseems.tboosts.listeners.BoosterDamageHandler;
import me.theseems.tboosts.storage.MetaBoosterStorage;
import me.theseems.tboosts.storage.SpigotBoosterMeta;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Main extends JavaPlugin {
  private static Plugin plugin;

  private void initHulkBooster() {
    // Hulk 1
    TBoostsAPI.getBoosterManager()
        .register(
            new BoosterFactory() {
              @Override
              public String getName() {
                return "hulk-1";
              }

              @Override
              public Booster produce(UUID player) {
                DamageBooster booster =
                    new DamageBooster(player) {
                      @Override
                      public String getName() {
                        return "hulk-1";
                      }
                    };
                booster.setMeta(new SpigotBoosterMeta(Bukkit.getPlayer(player), "hulk-1"));
                if (!booster.getMeta().getKeys().contains("init")) {
                  booster.getMeta().set("init", true);
                  booster.getMeta().set("display_name", "§cУДАР ХАЛКА §8I");
                  booster.getMeta().set("expire", 60 * 10);
                  booster.getMeta().set("amount", 1.05D);
                  booster.getMeta().set("date", System.currentTimeMillis());
                }
                return booster;
              }
            });

    // Hulk 2
    TBoostsAPI.getBoosterManager()
        .register(
            new BoosterFactory() {
              @Override
              public String getName() {
                return "hulk-2";
              }

              @Override
              public Booster produce(UUID player) {
                DamageBooster booster =
                    new DamageBooster(player) {
                      @Override
                      public String getName() {
                        return "hulk-2";
                      }
                    };
                booster.setMeta(new SpigotBoosterMeta(Bukkit.getPlayer(player), "hulk-2"));
                if (!booster.getMeta().getKeys().contains("init")) {
                  booster.getMeta().set("init", true);
                  booster.getMeta().set("display_name", "§cУДАР ХАЛКА §8II");
                  booster.getMeta().set("expire", 60 * 7);
                  booster.getMeta().set("amount", 1.1D);
                  booster.getMeta().set("date", System.currentTimeMillis());
                }
                return booster;
              }
            });

    // Hulk 3
    TBoostsAPI.getBoosterManager()
        .register(
            new BoosterFactory() {
              @Override
              public String getName() {
                return "hulk-3";
              }

              @Override
              public Booster produce(UUID player) {
                DamageBooster booster =
                    new DamageBooster(player) {
                      @Override
                      public String getName() {
                        return "hulk-3";
                      }
                    };
                booster.setMeta(new SpigotBoosterMeta(Bukkit.getPlayer(player), "hulk-3"));
                if (!booster.getMeta().getKeys().contains("init")) {
                  booster.getMeta().set("init", true);
                  booster.getMeta().set("display_name", "§cУДАР ХАЛКА §8III");
                  booster.getMeta().set("expire", 60 * 5);
                  booster.getMeta().set("amount", 1.15D);
                  booster.getMeta().set("date", System.currentTimeMillis());
                }
                return booster;
              }
            });

    // Hulk 4
    TBoostsAPI.getBoosterManager()
        .register(
            new BoosterFactory() {
              @Override
              public String getName() {
                return "hulk-4";
              }

              @Override
              public Booster produce(UUID player) {
                DamageBooster booster =
                    new DamageBooster(player) {
                      @Override
                      public String getName() {
                        return "hulk-4";
                      }
                    };
                booster.setMeta(new SpigotBoosterMeta(Bukkit.getPlayer(player), "hulk-4"));
                if (!booster.getMeta().getKeys().contains("init")) {
                  booster.getMeta().set("init", true);
                  booster.getMeta().set("display_name", "§cУДАР ХАЛКА §8IV");
                  booster.getMeta().set("expire", 60 * 3);
                  booster.getMeta().set("amount", 1.2D);
                  booster.getMeta().set("date", System.currentTimeMillis());
                }
                return booster;
              }
            });

    TBoostsAPI.getBoosterManager()
      .register(new BoosterFactory() {
        @Override
        public String getName() {
          return "heart-1";
        }

        @Override
        public Booster produce(UUID player) {
          HealthBooster booster =
            new HealthBooster(player) {
              @Override
              public String getName() {
                return "heart-1";
              }
            };
          booster.setMeta(new SpigotBoosterMeta(Bukkit.getPlayer(player), "heart-1"));
          if (!booster.getMeta().getKeys().contains("init")) {
            booster.getMeta().set("init", true);
            booster.getMeta().set("display_name", "§4Сердечник §8I");
            booster.getMeta().set("expire", 60 * 3);
            booster.getMeta().set("amount", 25D);
            booster.getMeta().set("date", System.currentTimeMillis());
          }
          return booster;
        }
      });
  }

  @Override
  public void onEnable() {
    plugin = this;
    TBoostsAPI.setBoosterManager(new SimpleBoosterManager());
    TBoostsAPI.setBoosterStorage(new MetaBoosterStorage());

    initHulkBooster();

    getCommand("boosts").setExecutor(new BoostsCommand());
    new BoosterTimeHandler().init();
    new BoosterGiveHandler().init();
    new BoosterAnnounceHandler().init();
    new BoosterDamageHandler().init();;

    // Small check to make sure that PlaceholderAPI is installed
    if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
      new BoosterExpansion().register();
    }
  }

  public static Plugin getPlugin() {
    return plugin;
  }
}
