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
public class BlockGroundcoverDriftwood extends BaseBlockGroundcover {

  public BlockGroundcoverDriftwood() {
    super(Settings.of(Material.WOOD));

    getSettings()
      .registryKey("core/groundcover/driftwood")
      .sound(SoundType.WOOD)
      .oreDict("driftwood")
      .oreDict("wood")
      .oreDict("wood", "driftwood");
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    if (!worldIn.isSideSolid(pos.down(), EnumFacing.UP) && !(worldIn.getBlockState(pos.down()).getBlock() instanceof BlockSoilFarmland)) {
      worldIn.setBlockToAir(pos);
    }
  }

//  @Override
//  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
//    IBlockState soil = worldIn.getBlockState(pos.down());
//
//    if (state.getBlock() == this) {
//      return BlockHelper.isGround(soil) && !(BlockHelper.isSaltWater(soil) || BlockHelper.isFreshWater(soil));
//    }
//    return this.canSustainBush(soil);
//  }


}
