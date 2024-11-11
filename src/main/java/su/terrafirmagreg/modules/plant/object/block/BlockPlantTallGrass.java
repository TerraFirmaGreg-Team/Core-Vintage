package su.terrafirmagreg.modules.plant.object.block;

import su.terrafirmagreg.api.data.enums.EnumPlantPart;
import su.terrafirmagreg.api.library.MCDate.Month;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.api.types.variant.block.PlantBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

import net.dries007.tfc.objects.blocks.plants.property.ITallPlant;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.climate.ClimateTFC;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static su.terrafirmagreg.api.util.MathUtils.RNG;
import static su.terrafirmagreg.api.data.Properties.EnumProp.PLANT_PART;
import static su.terrafirmagreg.api.data.Properties.IntProp.AGE_4;

public class BlockPlantTallGrass extends BlockPlantShortGrass implements IGrowable, ITallPlant {


  public BlockPlantTallGrass(PlantBlockVariant variant, PlantType type) {
    super(variant, type);
  }

  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return super.getActualState(state, worldIn, pos).withProperty(PLANT_PART, getPlantPart(worldIn, pos));
  }

  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return super.canPlaceBlockAt(worldIn, pos) && canBlockStay(worldIn, pos, worldIn.getBlockState(pos));
  }

  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    if (worldIn.getBlockState(pos.down(type.getMaxHeight())).getBlock() == this) {
      return false;
    } else if (state.getBlock() != this) {
      return canSustainBush(soil);
    } else {
      return soil.getBlock()
                 .canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this) &&
             type.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) &&
             type.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
    }
  }

  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (getPlantPart(worldIn, pos) == EnumPlantPart.LOWER) {
      worldIn.setBlockState(pos, state.withProperty(AGE_4, worldIn.getBlockState(pos.up()).getValue(AGE_4)));
    }

    if (!canBlockStay(worldIn, pos, state)) {
      worldIn.destroyBlock(pos, true);
    }

  }

  protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (!canBlockStay(worldIn, pos, state)) {
      if (getPlantPart(worldIn, pos) != EnumPlantPart.UPPER) {
        dropBlockAsItem(worldIn, pos, state, 0);
      }

      worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

  }


  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }

    int age;
    if (type.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) &&
        type.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
      age = state.getValue(AGE_4);
      if (rand.nextDouble() < getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
        if (age == 3 && canGrow(worldIn, pos, state, worldIn.isRemote)) {
          grow(worldIn, rand, pos, state);
        } else if (age < 3) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, age + 1)
                                          .withProperty(PLANT_PART, getPlantPart(worldIn, pos)));
        }

        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    } else if (!type.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) ||
               !type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
      age = state.getValue(AGE_4);
      if (rand.nextDouble() < getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
        if (age == 0 && canShrink(worldIn, pos)) {
          shrink(worldIn, pos);
        } else if (age > 0) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, age - 1)
                                          .withProperty(PLANT_PART, getPlantPart(worldIn, pos)));
        }

        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    }

    checkAndDropBlock(worldIn, pos, state);

  }

  public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
    int i;

    //noinspection StatementWithEmptyBody
    for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i) {
    }

    return i < type.getMaxHeight() && worldIn.isAirBlock(pos.up()) && canBlockStay(worldIn, pos.up(), state);
  }

  public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    return false;
  }

  public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    worldIn.setBlockState(pos.up(), getDefaultState());
    IBlockState iblockstate = state.withProperty(AGE_4, 0)
                                   .withProperty(STAGE, type.getStageForMonth())
                                   .withProperty(PLANT_PART, getPlantPart(worldIn, pos));
    worldIn.setBlockState(pos, iblockstate);
    iblockstate.neighborChanged(worldIn, pos.up(), this, pos);
  }

  private boolean canShrink(World worldIn, BlockPos pos) {
    return worldIn.getBlockState(pos.down()).getBlock() == this && worldIn.getBlockState(pos.up()).getBlock() != this;
  }

  public void shrink(World worldIn, BlockPos pos) {
    worldIn.setBlockToAir(pos);
    worldIn.getBlockState(pos).neighborChanged(worldIn, pos.down(), this, pos);
  }

  public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
    Month currentMonth = Calendar.CALENDAR_TIME.getMonthOfYear();
    int currentStage = state.getValue(STAGE);
    int expectedStage = type.getStageForMonth(currentMonth);
    int age = state.getValue(AGE_4);

    if (!worldIn.isRemote) {
      ItemStack stack = player.getHeldItemMainhand();
      int i;
      if (stack.getItem().getHarvestLevel(stack, "knife", player, state) == -1 && stack.getItem()
                                                                                       .getHarvestLevel(stack, "scythe", player, state) == -1) {
        if (stack.getItem() == Items.SHEARS) {
          for (i = 1; worldIn.getBlockState(pos.up(i)).getBlock() == this; ++i) {
            spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
          }
        }
      } else {
        for (i = 1; worldIn.getBlockState(pos.up(i)).getBlock() == this; ++i) {
          if (RNG.nextDouble() <= (double) (age + 1) / 4.0) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsCore.STRAW, 1));
          }
        }
      }
    }

  }

  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return getTallBoundingBax(state.getValue(AGE_4), state, source, pos);
  }

  public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
    return true;
  }

  @NotNull
  public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    return NonNullList.withSize(1, new ItemStack(this, 1));
  }

  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    onBlockHarvested(world, pos, state, player);
    return world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
  }


  public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
    IBlockState plant = plantable.getPlant(world, pos.offset(direction));
    return plant.getBlock() == this || super.canSustainPlant(state, world, pos, direction, plantable);
  }


}
