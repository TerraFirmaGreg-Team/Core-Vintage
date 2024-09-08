package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

public class BlockSoilPodzol extends BlockSoilGrass {

  public BlockSoilPodzol(SoilBlockVariant variant, SoilType type) {
    super(variant, type);

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }
}
