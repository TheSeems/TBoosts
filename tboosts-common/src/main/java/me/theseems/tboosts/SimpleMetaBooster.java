package me.theseems.tboosts;

public abstract class SimpleMetaBooster implements Booster {
  private BoosterMeta meta;

  public SimpleMetaBooster() {
    meta = new SimpleBoosterMeta();
  }

  @Override
  public BoosterMeta getMeta() {
    return meta;
  }

  @Override
  public void setMeta(BoosterMeta meta) {
    this.meta = meta;
  }
}
