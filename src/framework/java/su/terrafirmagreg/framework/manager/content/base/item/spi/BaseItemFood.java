package su.terrafirmagreg.framework.manager.content.base.item.spi;

import net.minecraft.item.ItemFood;

public abstract class BaseItemFood extends ItemFood {

  public BaseItemFood(int amount, float saturation, boolean isWolfFood) {
    super(amount, saturation, isWolfFood);
  }

  public BaseItemFood(int amount, boolean isWolfFood) {
    super(amount, isWolfFood);
  }
}
