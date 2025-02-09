package su.terrafirmagreg.modules.device.object.item;

import su.terrafirmagreg.modules.core.capabilities.size.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.object.item.spi.ItemFlask;

public class ItemFlaskMetal extends ItemFlask {

  protected static int capacity = ConfigDevice.ITEM.WATER_FLASKS.ironCap;
  protected static int drink = 100; //matches amount of water in TFC Jug

  public ItemFlaskMetal() {
    super(capacity, drink);

    getSettings()
      .registryKey("flask/metal")
      .capability(
        CapabilityProviderSize.of(Size.NORMAL, Weight.HEAVY, false)
      );
  }

}
