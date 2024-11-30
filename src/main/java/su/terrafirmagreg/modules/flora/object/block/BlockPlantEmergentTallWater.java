package su.terrafirmagreg.modules.flora.object.block;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.modules.flora.api.types.type.FloraType;
import su.terrafirmagreg.modules.flora.api.types.variant.block.FloraBlockVariant;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static su.terrafirmagreg.modules.worldgen.classic.ChunkGenClassic.SALT_WATER;

public class BlockPlantEmergentTallWater extends BlockPlantTallWater {

  public BlockPlantEmergentTallWater(FloraBlockVariant variant, FloraType type) {
    super(variant, type);
  }

  @Override
  public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
    IBlockState water = type.getWaterType();
    int i;
    //noinspection StatementWithEmptyBody
    for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i) {}

    if (water == SALT_WATER) {
      return i < type.getMaxHeight() && (worldIn.isAirBlock(pos.up()) || BlockHelper.isSaltWater(worldIn.getBlockState(pos.up())))
             && canBlockStay(worldIn, pos.up(), state);
    } else {
      return i < type.getMaxHeight() && (worldIn.isAirBlock(pos.up()) || BlockHelper.isFreshWater(worldIn.getBlockState(pos.up())))
             && canBlockStay(worldIn, pos.up(), state);
    }
  }

  public void shrink(World worldIn, BlockPos pos) {
    boolean flag = false;
    for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
      if (BlockHelper.isWater(worldIn.getBlockState(pos.offset(enumfacing)))) {
        flag = true;
      }
    }

    if (flag) {
      worldIn.setBlockState(pos, type.getWaterType());
    } else {
      worldIn.setBlockToAir(pos);
    }
    worldIn.getBlockState(pos).neighborChanged(worldIn, pos.down(), this, pos);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    if (type.getWaterType() == SALT_WATER) {
      return (soil.getBlock() == this || BlockHelper.isSaltWater(worldIn.getBlockState(pos))) && this.canSustainBush(soil);
    }
    return (soil.getBlock() == this || BlockHelper.isFreshWater(worldIn.getBlockState(pos))) && this.canSustainBush(soil);
  }

  @Override
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    this.onBlockHarvested(world, pos, state, player);

    boolean flag = false;
    for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
      if (BlockHelper.isWater(world.getBlockState(pos.offset(enumfacing)))) {
        flag = true;
      }
    }

    if (flag) {
      return world.setBlockState(pos, type.getWaterType(), world.isRemote ? 11 : 3);
    } else {
      return world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
    }
  }

  @Override
  protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (!this.canBlockStay(worldIn, pos, state)) {
      boolean flag = false;
      for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
        if (BlockHelper.isWater(worldIn.getBlockState(pos.offset(enumfacing)))) {
          flag = true;
        }
      }

      this.dropBlockAsItem(worldIn, pos, state, 0);
      if (flag) {
        worldIn.setBlockState(pos, type.getWaterType());
      } else {
        worldIn.setBlockToAir(pos);
      }
    }
  }
}
