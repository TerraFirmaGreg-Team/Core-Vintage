package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.device.ConfigDevice;

public class ItemLeatherFlask extends ItemFlask {

  protected static int capacity = ConfigDevice.ITEMS.WATER_FLASKS.leatherCap;
  protected static int drink = 100; //matches amount of water in TFC Jug

  public ItemLeatherFlask() {
    super("leather", capacity, drink);

    getSettings()
            .size(Size.SMALL)
            .weight(Weight.MEDIUM);
  }

}
