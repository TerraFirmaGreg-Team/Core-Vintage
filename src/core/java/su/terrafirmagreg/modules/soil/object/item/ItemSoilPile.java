package su.terrafirmagreg.modules.soil.object.item;

import su.terrafirmagreg.modules.soil.feature.soiltype.spi.type.SoilType;

public class ItemSoilPile extends ItemSoil {

  public ItemSoilPile(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("pile"))
      .oreDict("pile");
  }

}
