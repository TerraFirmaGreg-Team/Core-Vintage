package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

public class BlockSoilCoarseDirt extends BlockSoilDirt {

  public BlockSoilCoarseDirt(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("coarse_dirt"))
      .oreDict("coarse_dirt");

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }
}
