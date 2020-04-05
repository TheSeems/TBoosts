package me.theseems.tboosts.events;

import me.theseems.tboosts.Booster;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BoosterGiveEvent extends Event implements Cancellable {
  private static final HandlerList handlers = new HandlerList();
  private Booster booster;
  private boolean isCancelled;

  public BoosterGiveEvent(Booster booster) {
    this.booster = booster;
    this.isCancelled = false;
  }

  public Booster getBooster() {
    return booster;
  }

  public void setBooster(Booster booster) {
    this.booster = booster;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  @Override
  public boolean isCancelled() {
    return isCancelled;
  }

  @Override
  public void setCancelled(boolean b) {
    isCancelled = b;
  }
}
