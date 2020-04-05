package me.theseems.tboosts.events;

import me.theseems.tboosts.Booster;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BoosterTakeEvent extends Event {
  private static final HandlerList handlers = new HandlerList();
  private Booster booster;

  public BoosterTakeEvent(Booster booster) {
    this.booster = booster;
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
}
