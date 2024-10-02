package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static su.terrafirmagreg.data.Properties.BoolProp.CLAY;

public class BlockSoilDirt extends BlockSoil {

  public BlockSoilDirt(SoilBlockVariant variant, SoilType type) {
    super(variant, type);

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
}
