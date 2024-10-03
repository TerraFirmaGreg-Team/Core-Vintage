package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.objects.blocks.plants.property.ITallPlant;

import java.util.HashMap;
import java.util.Map;

public class BlockPlantEmergentTallWater extends BlockPlantTallWater implements ITallPlant {

  private static final Map<PlantType, BlockPlantEmergentTallWater> MAP = new HashMap<>();

  public BlockPlantEmergentTallWater(PlantType plant) {
    super(plant);
    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
  }

  public static BlockPlantEmergentTallWater get(PlantType plant) {
    return MAP.get(plant);
  }

  @Override
  public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
    IBlockState water = plant.getWaterType();
    int i;
    //noinspection StatementWithEmptyBody
    for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i)
      ;
    if (water == ChunkGenClassic.SALT_WATER) {
      return i < plant.getMaxHeight() && (worldIn.isAirBlock(pos.up()) || BlockUtils.isSaltWater(worldIn.getBlockState(pos.up()))) &&
             canBlockStay(worldIn, pos.up(), state);
    } else {
      return i < plant.getMaxHeight() && (worldIn.isAirBlock(pos.up()) || BlockUtils.isFreshWater(worldIn.getBlockState(pos.up()))) &&
             canBlockStay(worldIn, pos.up(), state);
    }
  }

  public void shrink(World worldIn, BlockPos pos) {
    boolean flag = false;
    for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
      if (BlockUtils.isWater(worldIn.getBlockState(pos.offset(enumfacing)))) {
        flag = true;
      }
    }

    if (flag) {
      worldIn.setBlockState(pos, plant.getWaterType());
    } else {
      worldIn.setBlockToAir(pos);
    }
    worldIn.getBlockState(pos).neighborChanged(worldIn, pos.down(), this, pos);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    if (plant.getWaterType() == ChunkGenClassic.SALT_WATER) {
      return (soil.getBlock() == this || BlockUtils.isSaltWater(worldIn.getBlockState(pos))) && this.canSustainBush(soil);
    }
    return (soil.getBlock() == this || BlockUtils.isFreshWater(worldIn.getBlockState(pos))) && this.canSustainBush(soil);
  }

  @Override
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    this.onBlockHarvested(world, pos, state, player);

    boolean flag = false;
    for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
      if (BlockUtils.isWater(world.getBlockState(pos.offset(enumfacing)))) {
        flag = true;
      }
    }

    if (flag) {
      return world.setBlockState(pos, plant.getWaterType(), world.isRemote ? 11 : 3);
    } else {
      return world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
    }
  }

  @Override
  protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (!this.canBlockStay(worldIn, pos, state)) {
      boolean flag = false;
      for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
        if (BlockUtils.isWater(worldIn.getBlockState(pos.offset(enumfacing)))) {
          flag = true;
        }
      }

      this.dropBlockAsItem(worldIn, pos, state, 0);
      if (flag) {
        worldIn.setBlockState(pos, plant.getWaterType());
      } else {
        worldIn.setBlockToAir(pos);
      }
    }
  }
}
