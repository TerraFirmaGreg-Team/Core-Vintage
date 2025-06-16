package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.feature.soiltype.spi.types.type.SoilType;

public class BlockSoilDryGrass extends BlockSoilGrass {

  public BlockSoilDryGrass(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("dry_grass"))
      .oreDict("dry_grass");

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }
}
