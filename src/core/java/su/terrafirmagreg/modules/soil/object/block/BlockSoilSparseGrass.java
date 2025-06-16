package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.feature.soiltype.spi.type.SoilType;

public class BlockSoilSparseGrass extends BlockSoilGrass {

  public BlockSoilSparseGrass(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("sparse_grass"))
      .oreDict("sparse_grass");

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }
}
