package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.feature.soiltype.spi.IDirtBlock;
import su.terrafirmagreg.modules.soil.feature.soiltype.spi.IMudBlock;
import su.terrafirmagreg.modules.soil.feature.soiltype.spi.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.object.block.spi.BlockSoil;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;

public class BlockSoilRootedDirt extends BlockSoil implements IDirtBlock, IMudBlock {

  public BlockSoilRootedDirt(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("rooted_dirt"))
      .renderLayer(BlockRenderLayer.CUTOUT);

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }

  @Override
  public IBlockState getGrass() {
    return BlocksSoil.DRY_GRASS.get(type).getDefaultState();
  }

  @Override
  public IBlockState getMud() {
    return BlocksSoil.MUD.get(type).getDefaultState();
  }
}
