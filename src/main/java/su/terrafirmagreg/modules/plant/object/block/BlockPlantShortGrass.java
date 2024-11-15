package su.terrafirmagreg.modules.plant.object.block;

import su.terrafirmagreg.api.library.MCDate.Month;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.api.types.variant.block.PlantBlockVariant;

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

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.climate.Climate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.api.util.MathUtils.RNG;

public class BlockPlantShortGrass extends BlockPlant implements IShearable {

  private static final AxisAlignedBB GRASS_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
  private static final AxisAlignedBB SHORTER_GRASS_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.5, 0.875);
  private static final AxisAlignedBB SHORT_GRASS_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.75, 0.875);
  private static final AxisAlignedBB SHORTEST_GRASS_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.25, 0.875);

  public BlockPlantShortGrass(PlantBlockVariant variant, PlantType type) {
    super(variant, type);
  }


  public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tile, ItemStack stack) {
    Month currentMonth = Calendar.CALENDAR_TIME.getMonthOfYear();
    int currentStage = state.getValue(this.STAGE);
    int expectedStage = type.getStageForMonth(currentMonth);
    int age = state.getValue(AGE_4);
    if (!worldIn.isRemote) {
      if (stack.getItem().getHarvestLevel(stack, "knife", player, state) == -1 && stack.getItem().getHarvestLevel(stack, "scythe", player, state) == -1) {
        if (stack.getItem() == Items.SHEARS) {
          spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
        }
      } else if (RNG.nextDouble() <= (double) (age + 1) / 4.0) {
        spawnAsEntity(worldIn, pos, new ItemStack(ItemsCore.STRAW, 1));
      }
    }

  }

  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }
    int age;
    if (type.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) &&
        type.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
      age = state.getValue(AGE_4);
      if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
        if (age < 3) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, age + 1));
        }

        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    } else if (!type.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) ||
               !type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
      age = state.getValue(AGE_4);
      if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
        if (age > 0) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, age - 1));
        } else {
          worldIn.setBlockToAir(pos);
        }

        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    }

    checkAndDropBlock(worldIn, pos, state);

  }

  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    switch (state.getValue(AGE_4)) {
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

  public int quantityDroppedWithBonus(int fortune, Random random) {
    return 1 + random.nextInt(fortune * 2 + 1);
  }

  @NotNull
  public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
    return new ItemStack(this, 1);
  }

  @NotNull
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(this, 1);
  }

  public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
    return true;
  }

  @NotNull
  public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    return NonNullList.withSize(1, new ItemStack(this, 1));
  }
}
