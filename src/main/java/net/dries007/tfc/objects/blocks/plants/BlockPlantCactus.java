package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import net.dries007.tfc.objects.blocks.plants.property.ITallPlant;
import net.dries007.tfc.util.climate.Climate;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static su.terrafirmagreg.data.Properties.EnumProp.PLANT_PART;
import static su.terrafirmagreg.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.data.Properties.IntProp.DAYPERIOD;

public class BlockPlantCactus extends BlockPlant implements IGrowable, ITallPlant {

  private static final Map<PlantType, BlockPlantCactus> MAP = new HashMap<>();

  public BlockPlantCactus(PlantType plant) {
    super(plant);
    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }

    getSettings()
      .noReplaceable()
      .sound(SoundType.GROUND)
      .weight(Weight.MEDIUM)
      .size(Size.SMALL)
      .hardness(0.25F);
  }

  public static BlockPlantCactus get(PlantType plant) {
    return MAP.get(plant);
  }

  @Override
  public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
    IBlockState plant = plantable.getPlant(world, pos.offset(direction));

    if (plant.getBlock() == this) {
      return true;
    }
    return super.canSustainPlant(state, world, pos, direction, plantable);
  }

  @Override
  @NotNull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, AGE_4, growthStageProperty, PLANT_PART, DAYPERIOD);
  }

  @Override
  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return super.getActualState(state, worldIn, pos).withProperty(PLANT_PART, getPlantPart(worldIn, pos));
  }

  @Override
  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    entityIn.attackEntityFrom(DamageSource.CACTUS, 1.0F);
  }

  @Override
  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return EnumOffsetType.XYZ;
  }


  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, worldIn.getBlockState(pos));
  }

  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }

    if (plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) &&
        plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
      int j = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) &&
          net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
        if (j == 3 && canGrow(worldIn, pos, state, worldIn.isRemote)) {
          grow(worldIn, rand, pos, state);
        } else if (j < 3) {
          worldIn.setBlockState(pos, state.withProperty(DAYPERIOD, getDayPeriod())
                                          .withProperty(AGE_4, j + 1)
                                          .withProperty(PLANT_PART, getPlantPart(worldIn, pos)));
        }
        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    }

    checkAndDropBlock(worldIn, pos, state);
  }

  @Override
  public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
    int i;
    //noinspection StatementWithEmptyBody
    for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i)
      ;
    return i < plant.getMaxHeight() && worldIn.isAirBlock(pos.up()) && canBlockStay(worldIn, pos.up(), state);
  }

  @Override
  public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    return false;
  }

  @Override
  public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    worldIn.setBlockState(pos.up(), this.getDefaultState());
    IBlockState iblockstate = state.withProperty(DAYPERIOD, getDayPeriod())
                                   .withProperty(AGE_4, 0)
                                   .withProperty(growthStageProperty, plant.getStageForMonth())
                                   .withProperty(PLANT_PART, getPlantPart(worldIn, pos));
    worldIn.setBlockState(pos, iblockstate);
    iblockstate.neighborChanged(worldIn, pos.up(), this, pos);
  }

  @Override
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    IBlockState soil = worldIn.getBlockState(pos.down());

    if (worldIn.getBlockState(pos.down(plant.getMaxHeight())).getBlock() == this) {
      return false;
    }
    if (state.getBlock() == this) {
      boolean flag = true;
      for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
        IBlockState blockState = worldIn.getBlockState(pos.offset(enumfacing));
        Material material = blockState.getMaterial();

        if (material.isSolid() || material == Material.LAVA) {
          flag = blockState.getBlock() == this;
        }
      }

      return flag &&
             soil.getBlock()
                 .canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this) &&
             plant.isValidTemp(Climate.getActualTemp(worldIn, pos)) &&
             plant.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
    }
    return this.canSustainBush(soil);
  }

  @Override
  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return FULL_BLOCK_AABB.offset(state.getOffset(source, pos));
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!this.canBlockStay(worldIn, pos, state)) {
      worldIn.destroyBlock(pos, true);
    }
  }
}
