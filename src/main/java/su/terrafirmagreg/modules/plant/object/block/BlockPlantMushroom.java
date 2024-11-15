package su.terrafirmagreg.modules.plant.object.block;

import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.api.types.variant.block.PlantBlockVariant;

import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import su.terrafirmagreg.modules.core.feature.climate.Climate;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.IntProp.AGE_4;

public class BlockPlantMushroom extends BlockPlant implements IGrowable {

  public BlockPlantMushroom(PlantBlockVariant variant, PlantType type) {
    super(variant, type);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, this.getDefaultState());
  }

  @Override
  protected boolean canSustainBush(IBlockState state) {
    return state.isFullBlock();
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }

    if (type.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) &&
        type.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
      int j = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) &&
          net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
        if (j == 3 && canGrow(worldIn, pos, state, worldIn.isRemote)) {
          grow(worldIn, rand, pos, state);
        } else if (j < 3) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, j + 1));
        }
        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    } else if (!type.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) ||
               !type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
      int j = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
        if (j == 0 && canShrink(worldIn, pos)) {
          shrink(worldIn, pos);
        } else if (j > 0) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, j - 1));
        }
        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    }

    checkAndDropBlock(worldIn, pos, state);
  }

  @Override
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    if (!worldIn.isOutsideBuildHeight(pos)) {
      IBlockState soil = worldIn.getBlockState(pos.down());
      return type.isValidSunlight(worldIn.getLight(pos)) && soil.getBlock()
                                                                .canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
    } else {
      return false;
    }
  }

  @Override
  public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
    int i = 5;

    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
      if (worldIn.getBlockState(blockpos).getBlock() == this) {
        --i;

        if (i <= 0) {
          return false;
        }
      }
    }
    return true;
  }

  @Override
  public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    return true;
  }

  @Override
  public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);

    for (int k = 0; k < 4; ++k) {
      if (worldIn.isAirBlock(blockpos1) && this.canBlockStay(worldIn, blockpos1, this.getDefaultState())) {
        pos = blockpos1;
      }

      blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
    }

    if (worldIn.isAirBlock(blockpos1) && this.canBlockStay(worldIn, blockpos1, this.getDefaultState())) {
      worldIn.setBlockState(blockpos1, this.getDefaultState(), 2);
    }
  }

  private boolean canShrink(World worldIn, BlockPos pos) {
    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
      if (worldIn.getBlockState(blockpos).getBlock() == this) {
        return true;
      }
    }
    return false;
  }

  private void shrink(World worldIn, BlockPos pos) {
    worldIn.setBlockToAir(pos);
  }
}
