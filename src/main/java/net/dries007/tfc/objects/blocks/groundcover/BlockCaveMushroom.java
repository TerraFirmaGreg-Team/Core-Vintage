package net.dries007.tfc.objects.blocks.groundcover;

import su.terrafirmagreg.api.base.block.BaseBlockBush;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.data.MathConstants;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.food.FoodHeatHandler;
import net.dries007.tfc.api.capability.food.IItemFoodTFC;
import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.dries007.tfc.objects.items.food.PotionEffectToHave;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.Climate;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Random;

import static su.terrafirmagreg.data.Properties.BoolProp.ALL_FACES;
import static su.terrafirmagreg.data.Properties.BoolProp.DOWN;
import static su.terrafirmagreg.data.Properties.BoolProp.EAST;
import static su.terrafirmagreg.data.Properties.BoolProp.NORTH;
import static su.terrafirmagreg.data.Properties.BoolProp.SOUTH;
import static su.terrafirmagreg.data.Properties.BoolProp.UP;
import static su.terrafirmagreg.data.Properties.BoolProp.WEST;
import static su.terrafirmagreg.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.data.Properties.IntProp.DAYPERIOD;

public class BlockCaveMushroom extends BaseBlockBush implements IGrowable, ICapabilitySize, IItemFoodTFC {

  private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.1D, 0.2D, 0.1D, 0.9D, 1.0D, 0.9D);
  private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);
  private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.1D, 0.1D, 0.0D, 0.9D, 0.9D, 0.8D);
  private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.1D, 0.1D, 0.2D, 0.9D, 0.9D, 1.0D);
  private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.2D, 0.1D, 0.1D, 1.0D, 0.9D, 0.9D);
  private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.1D, 0.1D, 0.8D, 0.9D, 0.9D);
  protected final BlockStateContainer blockState;
  public FoodData data;
  ArrayList<PotionEffectToHave> PotionEffects = new ArrayList<PotionEffectToHave>();

  public BlockCaveMushroom(float lightLevel, FoodData data, Object... oreNameParts) {
    super(Settings.of(Material.PLANTS));

    this.setTickRandomly(true);
    setSoundType(SoundType.PLANT);
    setHardness(0.0F);
    BlockUtils.setFireInfo(this, 5, 20);
    blockState = this.createBlockState();
    this.setDefaultState(this.blockState.getBaseState());
    this.setLightLevel(lightLevel);
    this.data = data;

    for (Object obj : oreNameParts) {
      if (obj instanceof PotionEffectToHave Effect) {
        PotionEffects.add(Effect);
      } else if (obj instanceof Object[]) {
        OreDictionaryHelper.register(this, (Object[]) obj);
      } else {
        OreDictionaryHelper.register(this, obj);
      }
    }
  }

  @NotNull
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, DOWN, UP, NORTH, EAST, WEST, SOUTH, DAYPERIOD, AGE_4);
  }

  @Override
  public ICapabilityProvider getCustomFoodHandler() {
    return new FoodHeatHandler(null, data, 1.0F, 200.0F);
  }

  protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
    if (!PotionEffects.isEmpty()) {
      for (PotionEffectToHave Effect : PotionEffects) {
        if (MathConstants.RNG.nextInt(Effect.chance) == 0) {
          player.addPotionEffect(new PotionEffect(Effect.PotionEffect, Effect.Duration, Effect.Power));
        }
      }
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(AGE_4, meta);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(AGE_4);
  }

  @Override
  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return state
      .withProperty(DAYPERIOD, getDayPeriod())
      .withProperty(DOWN, canPlantConnectTo(worldIn, pos, EnumFacing.DOWN))
      .withProperty(UP, canPlantConnectTo(worldIn, pos, EnumFacing.UP))
      .withProperty(NORTH, canPlantConnectTo(worldIn, pos, EnumFacing.NORTH))
      .withProperty(EAST, canPlantConnectTo(worldIn, pos, EnumFacing.EAST))
      .withProperty(SOUTH, canPlantConnectTo(worldIn, pos, EnumFacing.SOUTH))
      .withProperty(WEST, canPlantConnectTo(worldIn, pos, EnumFacing.WEST));
  }

  int getDayPeriod() {
    return Calendar.CALENDAR_TIME.getHourOfDay() / (ICalendar.HOURS_IN_DAY / 4);
  }

  protected boolean canPlantConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
    BlockPos other = pos.offset(facing);
    Block block = world.getBlockState(other).getBlock();
    return block.canBeConnectedTo(world, other, facing.getOpposite()) || canConnectTo(world, other, facing.getOpposite());
  }

  protected boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
    IBlockState iblockstate = worldIn.getBlockState(pos);
    BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos, facing);
    Block block = iblockstate.getBlock();
    return blockfaceshape == BlockFaceShape.SOLID;
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    switch (rot) {
      case CLOCKWISE_180:
        return state.withProperty(NORTH, state.getValue(SOUTH))
                    .withProperty(EAST, state.getValue(WEST))
                    .withProperty(SOUTH, state.getValue(NORTH))
                    .withProperty(WEST, state.getValue(EAST));
      case COUNTERCLOCKWISE_90:
        return state.withProperty(NORTH, state.getValue(EAST))
                    .withProperty(EAST, state.getValue(SOUTH))
                    .withProperty(SOUTH, state.getValue(WEST))
                    .withProperty(WEST, state.getValue(NORTH));
      case CLOCKWISE_90:
        return state.withProperty(NORTH, state.getValue(WEST))
                    .withProperty(EAST, state.getValue(NORTH))
                    .withProperty(SOUTH, state.getValue(EAST))
                    .withProperty(WEST, state.getValue(SOUTH));
      default:
        return state;
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
    switch (mirrorIn) {
      case LEFT_RIGHT:
        return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
      case FRONT_BACK:
        return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
      default:
        return super.withMirror(state, mirrorIn);
    }
  }

  @Override
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  @Override
  public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  @SuppressWarnings("deprecation")
  @Override
  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    return true;
  }

  @NotNull
  @Override
  public BlockStateContainer getBlockState() {
    return this.blockState;
  }

  @Override
  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return EnumOffsetType.NONE;
  }

  @Override
  public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
    return canConnectTo(world, pos.offset(facing), facing.getOpposite()) && !(world.getBlockState(pos.offset(facing))
                                                                                   .getBlock() instanceof BlockFence);
  }

  @Override
  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.VERY_LIGHT; // Stacksize = 64
  }

  @Override
  public @NotNull Size getSize(ItemStack stack) {
    return Size.TINY; // Store anywhere
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return Climate.getAvgTemp(worldIn, pos) >= -13f && Climate.getAvgTemp(worldIn, pos) <= 50f &&
           ProviderChunkData.getRainfall(worldIn, pos) >= 250f && ProviderChunkData.getRainfall(worldIn, pos) <= 500;
  }

  @Override
  protected boolean canSustainBush(IBlockState state) {
    return true;
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!worldIn.isRemote) {
      if (!canBlockStay(worldIn, pos, state)) {
        worldIn.destroyBlock(pos, true);
      }
    }
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }

    if (Climate.getActualTemp(worldIn, pos) >= -11f && Climate.getActualTemp(worldIn, pos) <= 48f &&
        Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()) <= 5f) {
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
    } else if (!(Climate.getActualTemp(worldIn, pos) >= -11f && Climate.getActualTemp(worldIn, pos) <= 48f) ||
               (Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()) > 5f)) {
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

  public double getGrowthRate(World world, BlockPos pos) {
    if (world.isRainingAt(pos)) {
      return ConfigTFC.General.MISC.plantGrowthRate * 5d;
    } else {
      return ConfigTFC.General.MISC.plantGrowthRate;
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
    return false;
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

  @Override
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    for (EnumFacing face : EnumFacing.values()) {
      IBlockState blockState = worldIn.getBlockState(pos.offset(face));
      if (!(blockState.getBlock() instanceof BlockLeavesTFC) &&
          (blockState.getBlockFaceShape(worldIn, pos.offset(face), face.getOpposite()) == BlockFaceShape.SOLID)) {
        return Climate.getAvgTemp(worldIn, pos) >= -13f && Climate.getAvgTemp(worldIn, pos) <= 50f &&
               ProviderChunkData.getRainfall(worldIn, pos) >= 250f && ProviderChunkData.getRainfall(worldIn, pos) <= 500;
      }
    }
    return false;
  }

  @Override
  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    state = state.getActualState(source, pos);

    int i = 0;
    AxisAlignedBB axisalignedbb = FULL_BLOCK_AABB;

    for (PropertyBool propertybool : ALL_FACES) {
      if ((state.getValue(propertybool))) {
        switch (propertybool.getName()) {
          case "down":
            axisalignedbb = DOWN_AABB;
            ++i;
            break;
          case "up":
            axisalignedbb = UP_AABB;
            ++i;
            break;
          case "north":
            axisalignedbb = NORTH_AABB;
            ++i;
            break;
          case "south":
            axisalignedbb = SOUTH_AABB;
            ++i;
            break;
          case "west":
            axisalignedbb = WEST_AABB;
            ++i;
            break;
          case "east":
            axisalignedbb = EAST_AABB;
            ++i;
            break;
          default:
            axisalignedbb = FULL_BLOCK_AABB;
        }
      }
    }

    return i == 1 ? axisalignedbb : FULL_BLOCK_AABB;
  }

  @Override
  @Nullable
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return NULL_AABB;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }
}
