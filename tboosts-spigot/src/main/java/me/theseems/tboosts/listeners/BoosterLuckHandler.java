package me.theseems.tboosts.listeners;

import me.theseems.tboosts.Booster;
import me.theseems.tboosts.Main;
import me.theseems.tboosts.TBoostsAPI;
import me.theseems.tboosts.boosters.LuckBooster;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class BoosterLuckHandler implements Listener {
    public void init() {
        Main.getPlugin().getServer().getPluginManager().registerEvents(this, Main.getPlugin());
    }

    private float getMaxAmount(UUID player) {
        final double[] maxAmount = {-1};
        for (Booster booster : TBoostsAPI.getBoosterStorage().getBoosters(player)) {
            if (booster instanceof LuckBooster) {
                booster
                        .getMeta()
                        .getDouble("amount")
                        .ifPresent(aDouble -> maxAmount[0] = Math.max(maxAmount[0], aDouble));
            }
        }
        return (float) maxAmount[0];
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBreak(BlockBreakEvent e) {
        float percentage;
        if (e.isCancelled()
                || !e.isDropItems()
                || (percentage = getMaxAmount(e.getPlayer().getUniqueId())) == -1) return;

        e.setDropItems(false);
        e.setExpToDrop((int) Math.floor(e.getExpToDrop() * percentage));

        World world = Objects.requireNonNull(e.getPlayer().getLocation().getWorld());
        e.getBlock()
                .getDrops(e.getPlayer().getInventory().getItemInMainHand())
                .forEach(
                        stack -> {
                            int amount = (int) Math.round(Math.pow(percentage + 0.5, percentage) * (new Random().nextDouble() * percentage));
                            stack.setAmount(Math.max(amount, 1));
                            Location toDrop = e.getBlock().getLocation().clone();
                            Item item = world.dropItemNaturally(toDrop, stack);
                            world.spawnParticle(
                                    Particle.VILLAGER_HAPPY, item.getLocation().clone().add(0, 0.5, 0), 1, 0, 0, 0);
                        });
    }
}
