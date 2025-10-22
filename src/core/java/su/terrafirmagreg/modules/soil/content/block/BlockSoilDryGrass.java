package su.terrafirmagreg.modules.soil.content.block;

import su.terrafirmagreg.api.data.Tags;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;

public class BlockSoilDryGrass extends BlockSoilGrass {

  public BlockSoilDryGrass(SoilType type) {
    super(type);

    getSettings()
      .tag(Tags.DRY_GRASS)
      .addOreDict("dry_grass");

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }
}
