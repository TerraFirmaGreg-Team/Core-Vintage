package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;

import java.util.Random;

import static su.terrafirmagreg.data.Properties.MOSSY;

public class BlockSoilMudBricks extends BlockSoilMud {

  public BlockSoilMudBricks(SoilBlockVariant variant, SoilType type) {
    super(variant, type);
    getSettings()
      .renderLayer(BlockRenderLayer.CUTOUT)
      .sound(SoundType.STONE)
      .harvestLevel(ToolClasses.PICKAXE, 0);

    setDefaultState(blockState.getBaseState()
                              .withProperty(MOSSY, false));
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsSoil.MUD_BRICK.get(type);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, MOSSY);
  }
}
