package su.terrafirmagreg.modules.soil.content.block;

import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;

public class BlockSoilSparseGrass extends BlockSoilGrass {

  public BlockSoilSparseGrass(SoilType type) {
    super(type);

    getSettings()
      .addOreDict("sparse_grass");

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }
}
