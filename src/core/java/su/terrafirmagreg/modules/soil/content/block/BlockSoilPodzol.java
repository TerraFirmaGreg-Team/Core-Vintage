package su.terrafirmagreg.modules.soil.content.block;

import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;

public class BlockSoilPodzol extends BlockSoilGrass {

  public BlockSoilPodzol(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("podzol"))
      .addOreDict("podzol");

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }
}
