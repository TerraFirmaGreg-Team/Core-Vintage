package su.terrafirmagreg.modules.device.object.item;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.device.ConfigDevice;

public class ItemMetalFlask extends ItemFlask {

  protected static int capacity = ConfigDevice.ITEM.WATER_FLASKS.ironCap;
  protected static int drink = 100; //matches amount of water in TFC Jug

  public ItemMetalFlask() {
    super("metal", capacity, drink);

    getSettings()
            .weight(Weight.HEAVY)
            .size(Size.NORMAL);
  }

}
