package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.feature.soiltype.spi.IDirtBlock;
import su.terrafirmagreg.modules.soil.feature.soiltype.spi.IMudBlock;
import su.terrafirmagreg.modules.soil.feature.soiltype.spi.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.object.block.spi.BlockSoil;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static su.terrafirmagreg.api.data.Properties.BoolProp.CLAY;

public class BlockSoilDirt extends BlockSoil implements IDirtBlock, IMudBlock {

  public BlockSoilDirt(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("dirt"));

    setDefaultState(blockState.getBaseState().withProperty(CLAY, Boolean.FALSE));

    //DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
  }


  @Override
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return this.getBlockState().getBaseState().getValue(CLAY)
           ? BlockRenderLayer.CUTOUT
           : BlockRenderLayer.SOLID;
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, CLAY);
  }

  @Override
  public IBlockState getGrass() {
    return BlocksSoil.GRASS.get(type).getDefaultState();
  }

  @Override
  public IBlockState getMud() {
    return BlocksSoil.MUD.get(type).getDefaultState();
  }
}
