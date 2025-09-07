package su.terrafirmagreg.modules.soil.content.block;

import su.terrafirmagreg.api.data.Tags;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.block.state.IBlockState;

public class BlockSoilCoarseDirt extends BlockSoilDirt {

  public BlockSoilCoarseDirt(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("coarse_dirt"))
      .tag(Tags.COARSE_DIRT)
      .addOreDict("coarse_dirt");

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }

  @Override
  public IBlockState getGrass() {
    return BlocksSoil.SPARSE_GRASS.get(type).getDefaultState();
  }
}
