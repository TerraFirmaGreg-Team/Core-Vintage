package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.block.state.IBlockState;

public class BlockSoilCoarseDirt extends BlockSoilDirt {

  public BlockSoilCoarseDirt(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("coarse_dirt"))
      .addOreDict("coarse_dirt");

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }

  @Override
  public IBlockState getGrass() {
    return BlocksSoil.SPARSE_GRASS.get(type).getDefaultState();
  }
}
