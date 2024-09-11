package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.init.ItemsCore;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
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


import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.blocks.plants.property.ITallPlant;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.Climate;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static su.terrafirmagreg.data.MathConstants.RNG;

public class BlockTallGrassTFC extends BlockShortGrassTFC implements IGrowable, ITallPlant {

  private static final PropertyEnum<ITallPlant.EnumBlockPart> PART = PropertyEnum.create("part", ITallPlant.EnumBlockPart.class);
  private static final Map<Plant, BlockTallGrassTFC> MAP = new HashMap<>();

  public BlockTallGrassTFC(Plant plant) {
    super(plant);
    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
  }

  public static BlockTallGrassTFC get(Plant plant) {
    return MAP.get(plant);
  }

  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return super.getActualState(state, worldIn, pos).withProperty(PART, this.getPlantPart(worldIn, pos));
  }

  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, worldIn.getBlockState(pos));
  }

  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    if (worldIn.getBlockState(pos.down(this.plant.getMaxHeight())).getBlock() == this) {
      return false;
    } else if (state.getBlock() != this) {
      return this.canSustainBush(soil);
    } else {
      return soil.getBlock()
              .canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this) &&
              this.plant.isValidTemp(Climate.getActualTemp(worldIn, pos)) &&
              this.plant.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
    }
  }

  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (this.getPlantPart(worldIn, pos) == EnumBlockPart.LOWER) {
      worldIn.setBlockState(pos, state.withProperty(AGE, worldIn.getBlockState(pos.up()).getValue(AGE)));
    }

    if (!this.canBlockStay(worldIn, pos, state)) {
      worldIn.destroyBlock(pos, true);
    }

  }

  protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (!this.canBlockStay(worldIn, pos, state)) {
      if (this.getPlantPart(worldIn, pos) != EnumBlockPart.UPPER) {
        this.dropBlockAsItem(worldIn, pos, state, 0);
      }

      worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    }

  }

  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return EnumOffsetType.XZ;
  }

  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (worldIn.isAreaLoaded(pos, 1)) {
      int j;
      if (this.plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) &&
              this.plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
        j = state.getValue(AGE);
        if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
          if (j == 3 && this.canGrow(worldIn, pos, state, worldIn.isRemote)) {
            this.grow(worldIn, rand, pos, state);
          } else if (j < 3) {
            worldIn.setBlockState(pos, state.withProperty(AGE, j + 1)
                    .withProperty(PART, this.getPlantPart(worldIn, pos)));
          }

          ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
        }
      } else if (!this.plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) ||
              !this.plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
        j = state.getValue(AGE);
        if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
          if (j == 0 && this.canShrink(worldIn, pos)) {
            this.shrink(worldIn, pos);
          } else if (j > 0) {
            worldIn.setBlockState(pos, state.withProperty(AGE, j - 1)
                    .withProperty(PART, this.getPlantPart(worldIn, pos)));
          }

          ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
        }
      }

      this.checkAndDropBlock(worldIn, pos, state);
    }
  }

  public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
    int i;
    for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i) {
    }

    return i < this.plant.getMaxHeight() && worldIn.isAirBlock(pos.up()) && this.canBlockStay(worldIn, pos.up(), state);
  }

  public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    return false;
  }

  public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    worldIn.setBlockState(pos.up(), this.getDefaultState());
    IBlockState iblockstate = state.withProperty(AGE, 0)
            .withProperty(this.growthStageProperty, this.plant.getStageForMonth())
            .withProperty(PART, this.getPlantPart(worldIn, pos));
    worldIn.setBlockState(pos, iblockstate);
    iblockstate.neighborChanged(worldIn, pos.up(), this, pos);
  }  public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
    Month currentMonth = Calendar.CALENDAR_TIME.getMonthOfYear();
    int currentStage = state.getValue(this.growthStageProperty);
    this.plant.getStageForMonth(currentMonth);
    int age = state.getValue(AGE);
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

  private boolean canShrink(World worldIn, BlockPos pos) {
    return worldIn.getBlockState(pos.down()).getBlock() == this && worldIn.getBlockState(pos.up())
            .getBlock() != this;
  }  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    this.onBlockHarvested(world, pos, state, player);
    return world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
  }

  public void shrink(World worldIn, BlockPos pos) {
    worldIn.setBlockToAir(pos);
    worldIn.getBlockState(pos).neighborChanged(worldIn, pos.down(), this, pos);
  }  public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
    IBlockState plant = plantable.getPlant(world, pos.offset(direction));
    return plant.getBlock() == this || super.canSustainPlant(state, world, pos, direction, plantable);
  }

  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return this.getTallBoundingBax(state.getValue(AGE), state, source, pos);
  }

  @NotNull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, AGE, this.growthStageProperty, DAYPERIOD, PART);
  }

  public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
    return true;
  }

  @NotNull
  public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    return NonNullList.withSize(1, new ItemStack(this, 1));
  }






}
