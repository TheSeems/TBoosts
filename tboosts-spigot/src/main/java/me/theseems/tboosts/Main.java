package me.theseems.tboosts;

import me.theseems.tboosts.boosters.DamageBooster;
import me.theseems.tboosts.boosters.FlashBooster;
import me.theseems.tboosts.boosters.ImmortalBooster;
import me.theseems.tboosts.boosters.LuckBooster;
import me.theseems.tboosts.commands.BoostsCommand;
import me.theseems.tboosts.features.BoosterExpansion;
import me.theseems.tboosts.listeners.*;
import me.theseems.tboosts.storage.MetaBoosterStorage;
import me.theseems.tboosts.storage.SpigotBoosterMeta;
import me.theseems.tboosts.support.BoosterReward;
import me.theseems.tcrates.TCratesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class Main extends JavaPlugin {
    private static Plugin plugin;

    private String getRoman(int number) {
        // declare arrays for Roman numbers
        String[] thousands = {"", "M", "MM", "MMM"};
        String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String[] units = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        // get thousands in the decimal number
        int numberOfThousands = number / 1000;
        // get hundreds in the decimal number
        int numberOfHundreds = (number / 100) % 10;
        // get tens in the decimal number
        int numberOfTens = (number / 10) % 10;
        // get units in the decimal number
        int numberOfUnits = number % 10;
        // get the corresponding Roman digits and merge them
        return thousands[numberOfThousands]
                + hundreds[numberOfHundreds]
                + tens[numberOfTens]
                + units[numberOfUnits];
    }

    private BoosterFactory hulk(double amount, double expire, int level) {
        return new BoosterFactory() {
            @Override
            public String getName() {
                return "hulk-" + level;
            }

            @Override
            public Booster produce(UUID player) {
                DamageBooster booster =
                        new DamageBooster(player) {
                            @Override
                            public String getName() {
                                return "hulk-" + level;
                            }
                        };
                booster.setMeta(new SpigotBoosterMeta(Bukkit.getPlayer(player), "hulk-" + level));
                if (!booster.getMeta().getKeys().contains("init")) {
                    booster.getMeta().set("init", true);
                    booster.getMeta().set("display_name", "§c§lУдар Халка §8" + getRoman(level));
                    booster.getMeta().set("expire", expire);
                    booster.getMeta().set("amount", amount);
                    booster.getMeta().set("date", System.currentTimeMillis());
                }
                return booster;
            }
        };
    }

    private BoosterFactory flash(double amount, double expire, int level) {
        return new BoosterFactory() {
            @Override
            public String getName() {
                return "flash-" + level;
            }

            @Override
            public Booster produce(UUID player) {
                FlashBooster booster =
                        new FlashBooster(player) {
                            @Override
                            public String getName() {
                                return "flash-" + level;
                            }
                        };
                booster.setMeta(new SpigotBoosterMeta(Bukkit.getPlayer(player), "flash-" + level));
                if (!booster.getMeta().getKeys().contains("init")) {
                    booster.getMeta().set("init", true);
                    booster.getMeta().set("display_name", "§b§lФлеш §8" + getRoman(level));
                    booster.getMeta().set("expire", expire);
                    booster.getMeta().set("amount", amount);
                    booster.getMeta().set("date", System.currentTimeMillis());
                }
                return booster;
            }
        };
    }

    private BoosterFactory immortal(int amount, double expire, int level) {
        return new BoosterFactory() {
            @Override
            public String getName() {
                return "immo-" + level;
            }

            @Override
            public Booster produce(UUID player) {
                ImmortalBooster booster =
                        new ImmortalBooster(player) {
                            @Override
                            public String getName() {
                                return "immo-" + level;
                            }
                        };

                booster.setMeta(new SpigotBoosterMeta(Bukkit.getPlayer(player), "immo-" + level));
                if (!booster.getMeta().getKeys().contains("init")) {
                    booster.getMeta().set("init", true);
                    booster.getMeta().set("display_name", "§6§lБессмертный §8" + getRoman(level));
                    booster.getMeta().set("expire", expire);
                    booster.getMeta().set("amount", amount);
                    booster.getMeta().set("hits", 0);
                    booster.getMeta().set("date", System.currentTimeMillis());
                }
                return booster;
            }
        };
    }

    private BoosterFactory luck(double amount, double expire, int level) {
        return new BoosterFactory() {
            @Override
            public String getName() {
                return "luck-" + level;
            }

            @Override
            public Booster produce(UUID player) {
                LuckBooster booster =
                        new LuckBooster(player) {
                            @Override
                            public String getName() {
                                return "luck-" + level;
                            }
                        };

                booster.setMeta(new SpigotBoosterMeta(Bukkit.getPlayer(player), "luck-" + level));
                if (!booster.getMeta().getKeys().contains("init")) {
                    booster.getMeta().set("init", true);
                    booster.getMeta().set("display_name", "§a§lВезунчик §8" + getRoman(level));
                    booster.getMeta().set("expire", expire);
                    booster.getMeta().set("amount", amount);
                    booster.getMeta().set("date", System.currentTimeMillis());
                }
                return booster;
            }
        };
    }

    private void initHulkBooster() {
        // Hulk 1
        TBoostsAPI.getBoosterManager().register(hulk(1.05, 60 * 10, 1));
        // Hulk 2
        TBoostsAPI.getBoosterManager().register(hulk(1.1, 60 * 7, 2));
        // Hulk 3
        TBoostsAPI.getBoosterManager().register(hulk(1.15, 60 * 5, 3));
        // Hulk 4
        TBoostsAPI.getBoosterManager().register(hulk(1.2, 60 * 3, 4));
    }

    private void initFlashBooster() {
        // Flash 1
        TBoostsAPI.getBoosterManager().register(flash(1.25, 5 * 60, 1));
        // Flash 2
        TBoostsAPI.getBoosterManager().register(flash(1.6, 7 * 60, 2));
        // Flash 3
        TBoostsAPI.getBoosterManager().register(flash(1.85, 10 * 60, 3));
    }

    private void initImmortalBooster() {
        // Immortal 1
        TBoostsAPI.getBoosterManager().register(immortal(2, 5 * 60, 1));
        // Immortal 2
        TBoostsAPI.getBoosterManager().register(immortal(4, 3 * 60, 2));
        // Immortal 3
        TBoostsAPI.getBoosterManager().register(immortal(6, 2 * 60, 3));
    }

    private void initLuckBooster() {
        // Luck 1
        TBoostsAPI.getBoosterManager().register(luck(1.15, 5 * 60, 1));
        // Luck 2
        TBoostsAPI.getBoosterManager().register(luck(1.25, 10 * 60, 2));
        // Luck 3
        TBoostsAPI.getBoosterManager().register(luck(1.35, 15 * 60, 3));
    }

    public void loadT() {
        // Small check to make sure that TCrates is installed
        if (Bukkit.getPluginManager().getPlugin("TCrates") != null) {
            TCratesPlugin.getManager()
                    .register(
                            "booster",
                            crateRewardConfig -> {
                                BoosterReward reward = new BoosterReward();
                                reward.setMeta(crateRewardConfig.getMeta());
                                reward.setStack(crateRewardConfig.getIcon().getStack());
                                return reward;
                            });
        }
    }

    @Override
    public void onLoad() {
        plugin = this;
        TBoostsAPI.setBoosterManager(new SimpleBoosterManager());
        TBoostsAPI.setBoosterStorage(new MetaBoosterStorage());

        initHulkBooster();
        initFlashBooster();
        initImmortalBooster();
        initLuckBooster();
        loadT();
    }

    @Override
    public void onEnable() {
        new BoosterTimeHandler().init();
        new BoosterGiveHandler().init();
        new BoosterAnnounceHandler().init();
        new BoosterDamageHandler().init();
        new BoosterImmortalHandler().init();
        new BoosterLuckHandler().init();

        // Small check to make sure that PlaceholderAPI is installed
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new BoosterExpansion().register();
        }

        getCommand("boosts").setExecutor(new BoostsCommand());
    }

    public static Plugin getPlugin() {
    return plugin;
  }
}
