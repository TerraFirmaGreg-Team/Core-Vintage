package su.terrafirmagreg.modules.soil.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.soil.feature.soiltype.spi.type.SoilType;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.MOSSY;

public class BlockSoilMudBricks extends BlockSoilMud {

  public BlockSoilMudBricks(SoilType type) {
    super(type);

    getSettings()
      .registryKey(type.getRegistryKey("mud_bricks"))
      .renderLayer(BlockRenderLayer.CUTOUT)
      .sound(SoundType.STONE)
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .oreDict("mud_bricks");

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
