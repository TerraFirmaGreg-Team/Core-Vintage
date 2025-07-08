package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.data.Tags;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

public class BlockSoilDryGrass extends BlockSoilGrass {

  public BlockSoilDryGrass(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("dry_grass"))
      .tag(Tags.DRY_GRASS)
      .oreDict("dry_grass");

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }
}
