package su.terrafirmagreg.modules.core.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockGroundcover;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilFarmland;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class BlockGroundcoverTwig extends BaseBlockGroundcover {

  public BlockGroundcoverTwig() {
    super(Settings.of(Material.WOOD));

    getSettings()
      .registryKey("core/groundcover/twig")
      .sound(SoundType.WOOD)
      .oreDict("twig")
      .oreDict("wood", "twig")
      .oreDict("wood")
      .oreDict("stick")
      .oreDict("wood", "stick");
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    if (!worldIn.isSideSolid(pos.down(), EnumFacing.UP) && !(worldIn.getBlockState(pos.down()).getBlock() instanceof BlockSoilFarmland)) {
      worldIn.setBlockToAir(pos);
    }
  }
}
