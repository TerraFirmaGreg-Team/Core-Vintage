package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.Month;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.core.init.ItemsCore;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IShearable;

import net.dries007.tfc.Constants;
import net.dries007.tfc.api.types.Plant;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@ParametersAreNonnullByDefault
public class BlockPlantShortGrass extends BlockPlantTFC implements IShearable {

  private static final AxisAlignedBB GRASS_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
  private static final AxisAlignedBB SHORTER_GRASS_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.5, 0.875);
  private static final AxisAlignedBB SHORT_GRASS_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.75, 0.875);
  private static final AxisAlignedBB SHORTEST_GRASS_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.25, 0.875);
  private static final Map<Plant, BlockPlantShortGrass> MAP = new HashMap();

  public BlockPlantShortGrass(Plant plant) {
    super(plant);
    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
  }

  public static BlockPlantShortGrass get(Plant plant) {
    return MAP.get(plant);
  }

  public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
    Month currentMonth = Calendar.CALENDAR_TIME.getMonthOfYear();
    int currentStage = state.getValue(this.growthStageProperty);
    this.plant.getStageForMonth(currentMonth);
    int age = state.getValue(AGE);
    if (!worldIn.isRemote) {
      if (stack.getItem().getHarvestLevel(stack, "knife", player, state) == -1 && stack.getItem().getHarvestLevel(stack, "scythe", player, state) == -1) {
        if (stack.getItem() == Items.SHEARS) {
          spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
        }
      } else if (Constants.RNG.nextDouble() <= (double) (age + 1) / 4.0) {
        spawnAsEntity(worldIn, pos, new ItemStack(ItemsCore.STRAW.get()));
      }
    }

  }

  @Nonnull
  public Block.EnumOffsetType getOffsetType() {
    return EnumOffsetType.XZ;
  }

  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (worldIn.isAreaLoaded(pos, 1)) {
      int j;
      if (this.plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos))
          && this.plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
        j = state.getValue(AGE);
        if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
          if (j < 3) {
            worldIn.setBlockState(pos, state.withProperty(AGE, j + 1));
          }

          ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
        }
      } else if (!this.plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos))
                 || !this.plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
        j = state.getValue(AGE);
        if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
          if (j > 0) {
            worldIn.setBlockState(pos, state.withProperty(AGE, j - 1));
          } else {
            worldIn.setBlockToAir(pos);
          }

          ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
        }
      }

      this.checkAndDropBlock(worldIn, pos, state);
    }
  }

  @Nonnull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    switch (state.getValue(AGE)) {
      case 0:
        return SHORTEST_GRASS_AABB.offset(state.getOffset(source, pos));
      case 1:
        return SHORTER_GRASS_AABB.offset(state.getOffset(source, pos));
      case 2:
        return SHORT_GRASS_AABB.offset(state.getOffset(source, pos));
      default:
        return GRASS_AABB.offset(state.getOffset(source, pos));
    }
  }

  @Nonnull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, AGE, this.growthStageProperty, DAYPERIOD);
  }

  public int quantityDroppedWithBonus(int fortune, Random random) {
    return 1 + random.nextInt(fortune * 2 + 1);
  }

  @Nonnull
  public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
    return new ItemStack(this, 1);
  }

  @Nonnull
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(this, 1);
  }

  public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
    return true;
  }

  @Nonnull
  public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    return NonNullList.withSize(1, new ItemStack(this, 1));
  }
}
