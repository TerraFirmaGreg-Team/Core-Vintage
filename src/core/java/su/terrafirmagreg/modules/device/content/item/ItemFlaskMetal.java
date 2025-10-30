package su.terrafirmagreg.modules.device.content.item;

import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.content.item.spi.ItemFlask;

public class ItemFlaskMetal extends ItemFlask {

  protected static int capacity = ConfigDevice.ITEM.WATER_FLASKS.ironCap;
  protected static int drink = 100; //matches amount of water in TFC Jug

  public ItemFlaskMetal() {
    super(capacity, drink);

    getSettings()
      .capability(
        CapabilityProviderSize.of(Size.NORMAL, Weight.HEAVY, false)
      );
  }

}
