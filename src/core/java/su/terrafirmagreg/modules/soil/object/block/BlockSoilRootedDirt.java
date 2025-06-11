package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.object.block.spi.BlockSoil;

import net.minecraft.util.BlockRenderLayer;

public class BlockSoilRootedDirt extends BlockSoil {

  public BlockSoilRootedDirt(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("rooted_dirt"))
      .oreDict("rooted_dirt")
      .renderLayer(BlockRenderLayer.CUTOUT);

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }
}
