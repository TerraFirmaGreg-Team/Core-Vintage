package net.dries007.tfc.objects.blocks.wood;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.feature.calendar.Month;
import su.terrafirmagreg.modules.core.feature.climate.Climate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import com.google.common.collect.ImmutableList;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.client.particle.TFCParticles;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.agriculture.SeasonalTrees;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static net.dries007.tfc.Constants.RNG;

@ParametersAreNonnullByDefault
public class BlockLeavesTFCF extends BlockLeaves {

  public static final PropertyEnum<EnumLeafState> LEAF_STATE = PropertyEnum.create("state", BlockLeavesTFCF.EnumLeafState.class);
  public static final PropertyBool HARVESTABLE = PropertyBool.create("harvestable");
  private static final Map<SeasonalTrees, BlockLeavesTFCF> MAP = new HashMap<>();
  public final Tree wood;
  public final SeasonalTrees fruitTree;

  public BlockLeavesTFCF(Tree wood, SeasonalTrees tree) {
    this.wood = wood;
    this.fruitTree = tree;
    this.setTickRandomly(true);
    if (MAP.put(tree, this) != null) {throw new IllegalStateException("There can only be one.");}
    setDefaultState(getBlockState().getBaseState().withProperty(DECAYABLE, false).withProperty(LEAF_STATE, EnumLeafState.NORMAL)
      .withProperty(HARVESTABLE, false)); // TFC leaves don't use CHECK_DECAY, so just don't use it
    leavesFancy = true; // Fast / Fancy graphics works correctly
    net.dries007.tfc.util.OreDictionaryHelper.register(this, "tree", "leaves");
    OreDictionaryHelper.register(this, "tree", "leaves", wood.getRegistryName().getPath());
    Blocks.FIRE.setFireInfo(this, 30, 60);
    this.setTickRandomly(true);
  }

  public static BlockLeavesTFCF get(SeasonalTrees wood) {
    return MAP.get(wood);
  }

  @SuppressWarnings("deprecation")
  @Override
  @Nonnull
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(HARVESTABLE, meta > 3).withProperty(LEAF_STATE, EnumLeafState.valueOf(meta & 0b11))
      .withProperty(DECAYABLE, (meta & 0b01) == 0b01);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(LEAF_STATE).ordinal() + (state.getValue(HARVESTABLE) ? 4 : 0) + (state.getValue(DECAYABLE) ? 1 : 0);
  }

  @SuppressWarnings("deprecation")
  @Override
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return NULL_AABB;
  }

  public double getGrowthRate(World world, BlockPos pos) {
    if (world.isRainingAt(pos)) {return ConfigTFC.General.MISC.plantGrowthRate * 5d;} else {return ConfigTFC.General.MISC.plantGrowthRate;}
  }

  @Override
  public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
    if (!world.isAreaLoaded(pos, 1)) {return;}

    Month currentMonth = Calendar.CALENDAR_TIME.getMonthOfYear();
    int expectedStage = fruitTree.getStageForMonth(currentMonth);
    float avgTemperature = Climate.getAvgTemp(world, pos);
    float tempGauss = (int) (12f + (random.nextGaussian() / 4));

    switch (expectedStage) {
      case 0:
        if (state.getValue(LEAF_STATE) != EnumLeafState.WINTER && avgTemperature < tempGauss) {
          world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.WINTER));
        } else if (state.getValue(LEAF_STATE) != EnumLeafState.WINTER && avgTemperature >= tempGauss) {
          world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.NORMAL));
        }
        break;
      case 1:
        if (state.getValue(LEAF_STATE) != EnumLeafState.NORMAL) {world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.NORMAL));} else if (
          state.getValue(LEAF_STATE) == EnumLeafState.FLOWERING || state.getValue(LEAF_STATE) == EnumLeafState.AUTUMN
          || state.getValue(LEAF_STATE) == EnumLeafState.WINTER) {world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.NORMAL));}
        break;
      case 2:
        if (state.getValue(LEAF_STATE) != EnumLeafState.FLOWERING) {world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.FLOWERING));}
        break;
      case 3:
        if (state.getValue(LEAF_STATE) != EnumLeafState.FRUIT) {
          TETickCounter te = Helpers.getTE(world, pos, TETickCounter.class);
          if (te != null) {
            long hours = te.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
            if (hours > (fruitTree.getGrowthTime() * ConfigTFC.General.FOOD.fruitTreeGrowthTimeModifier)) {
              world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.FRUIT));
              te.resetCounter();
            }
          }
        }
        break;
      case 4:
        if (state.getValue(LEAF_STATE) != EnumLeafState.AUTUMN && avgTemperature < tempGauss) {
          world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.AUTUMN));
        } else if (state.getValue(LEAF_STATE) != EnumLeafState.AUTUMN && avgTemperature >= tempGauss) {
          world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.NORMAL));
        }
        break;
      default:
        world.setBlockState(pos, state.withProperty(LEAF_STATE, EnumLeafState.NORMAL));
    }
    doLeafDecay(world, pos, state);
  }

  @Override
  public int tickRate(World worldIn) {
    return 10;
  }

  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(IBlockState state, World world, BlockPos pos, @Nullable Block blockIn, @Nullable BlockPos fromPos) {
    //world.scheduleUpdate(pos, this, 0);
    doLeafDecay(world, pos, state);
  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    TETickCounter tile = Helpers.getTE(worldIn, pos, TETickCounter.class);
    if (tile != null) {
      tile.resetCounter();
    }
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (worldIn.getBlockState(pos).getValue(LEAF_STATE) == EnumLeafState.FRUIT && fruitTree.getDrop() != null) {
      if (!worldIn.isRemote) {
        ItemHandlerHelper.giveItemToPlayer(playerIn, fruitTree.getFoodDrop());
        worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(LEAF_STATE, EnumLeafState.NORMAL));
        TETickCounter te = Helpers.getTE(worldIn, pos, TETickCounter.class);
        if (te != null) {
          te.resetCounter();
        }
      }
      return true;
    }
    return false;
  }

  @Override
  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    if (!(entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).isCreative())) {
      // Player will take damage when falling through leaves if fall is over 9 blocks, fall damage is then set to 0.
      entityIn.fall((entityIn.fallDistance - 6), 1.0F);
      entityIn.fallDistance = 0;
      // Entity motion is reduced by leaves.
      entityIn.motionX *= ConfigTFC.General.MISC.leafMovementModifier;
      if (entityIn.motionY < 0) {
        entityIn.motionY *= ConfigTFC.General.MISC.leafMovementModifier;
      }
      entityIn.motionZ *= ConfigTFC.General.MISC.leafMovementModifier;
    }
  }

  @Override
  @Nonnull
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, DECAYABLE, LEAF_STATE, HARVESTABLE);
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TETickCounter();
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    doLeafDecay(worldIn, pos, state);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    if (state.getValue(LEAF_STATE) != EnumLeafState.WINTER && !fruitTree.hasDeadLeaves) {
      return ConfigTFC.General.TREE.enableSaplings ? Item.getItemFromBlock(BlockSaplingTFC.get(wood)) : Items.AIR;
    }
    return null;
  }

  @Override
  protected int getSaplingDropChance(IBlockState state) {
    return 25;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @SideOnly(Side.CLIENT)
  @Override
  @Nonnull
  public BlockRenderLayer getRenderLayer() {
    /*
     * This is a way to make sure the leave settings are updated.
     * The result of this call is cached somewhere, so it's not that important, but:
     * The alternative would be to use `Minecraft.getMinecraft().gameSettings.fancyGraphics` directly in the 2 relevant methods.
     * It's better to do that than to refer to Blocks.LEAVES, for performance reasons.
     */
    leavesFancy = Minecraft.getMinecraft().gameSettings.fancyGraphics;
    return super.getRenderLayer();
  }

  @Override
  @Nonnull
  public BlockPlanks.EnumType getWoodType(int meta) {
    // Unused so return whatever
    return BlockPlanks.EnumType.OAK;
  }

  @Override
  public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
    // Don't do vanilla decay
  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    if (state.getValue(LEAF_STATE) != EnumLeafState.WINTER && !fruitTree.hasDeadLeaves) {
      int chance = this.getSaplingDropChance(state);
      if (chance > 0) {
        if (fortune > 0) {
          chance -= 2 << fortune;
          if (chance < 10) {chance = 10;}
        }

        if (RANDOM.nextInt(chance) == 0) {
          ItemStack drop = new ItemStack(getItemDropped(state, RANDOM, fortune), 1, damageDropped(state));
          if (!drop.isEmpty()) {
            drops.add(drop);
          }
        }
      }
    }
  }

  @SuppressWarnings("deprecation")
  @SideOnly(Side.CLIENT)
  @Override
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    /*
     * See comment on getRenderLayer()
     */
    leavesFancy = Minecraft.getMinecraft().gameSettings.fancyGraphics;
    return true;// super.shouldSideBeRendered(blockState, blockAccess, pos, side);
  }

  @Nonnull
  public Tree getTree() {
    return wood;
  }

  @Override
  @Nonnull
  public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    return ImmutableList.of(new ItemStack(this));
  }

  private void doLeafDecay(World world, BlockPos pos, IBlockState state) {
    // TFC Leaf Decay
    if (world.isRemote || !state.getValue(DECAYABLE)) {return;}

    Set<BlockPos> paths = new HashSet<>();
    Set<BlockPos> evaluated = new HashSet<>(); // Leaves that everything was evaluated so no need to do it again
    List<BlockPos> pathsToAdd; // New Leaves that needs evaluation
    BlockPos.MutableBlockPos pos1 = new BlockPos.MutableBlockPos(pos);
    IBlockState state1;
    paths.add(pos); // Center block

    for (int i = 0; i < wood.getMaxDecayDistance(); i++) {
      pathsToAdd = new ArrayList<>();
      for (BlockPos p1 : paths) {
        for (EnumFacing face : EnumFacing.values()) {
          pos1.setPos(p1).move(face);
          if (evaluated.contains(pos1) || !world.isBlockLoaded(pos1)) {continue;}
          state1 = world.getBlockState(pos1);
          if (state1.getBlock() == BlockLogTFC.get(wood) || state1.getBlock() == BlockLogTFCF.get(fruitTree)) {return;}
          if (state1.getBlock() == this) {pathsToAdd.add(pos1.toImmutable());}
        }
        evaluated.add(p1); // Evaluated
      }
      paths.addAll(pathsToAdd);
      paths.removeAll(evaluated);
    }

    world.setBlockToAir(pos);
    int particleScale = 10;
    double x = pos.getX();
    double y = pos.getY();
    double z = pos.getZ();
    for (int i = 1; i < RNG.nextInt(4); i++) {
      switch (RNG.nextInt(4)) {
        case 1:
          TFCParticles.LEAF1.sendToAllNear(world,
            x + RNG.nextFloat() / particleScale,
            y - RNG.nextFloat() / particleScale,
            z + RNG.nextFloat() / particleScale,
            (RNG.nextFloat() - 0.5) / particleScale,
            -0.15D + RNG.nextFloat() / particleScale, (RNG.nextFloat() - 0.5) / particleScale, 90);
          break;
        case 2:
          TFCParticles.LEAF2.sendToAllNear(world,
            x + RNG.nextFloat() / particleScale,
            y - RNG.nextFloat() / particleScale,
            z + RNG.nextFloat() / particleScale,
            (RNG.nextFloat() - 0.5) / particleScale,
            -0.15D + RNG.nextFloat() / particleScale, (RNG.nextFloat() - 0.5) / particleScale, 70);
          break;
        case 3:
          TFCParticles.LEAF3.sendToAllNear(world,
            x + RNG.nextFloat() / particleScale,
            y - RNG.nextFloat() / particleScale,
            z + RNG.nextFloat() / particleScale,
            (RNG.nextFloat() - 0.5) / particleScale,
            -0.15D + RNG.nextFloat() / particleScale, (RNG.nextFloat() - 0.5) / particleScale, 80);
          break;
      }
    }
  }

  public enum EnumLeafState implements IStringSerializable {
    NORMAL, FLOWERING, FRUIT, AUTUMN, WINTER;

    private static final EnumLeafState[] VALUES = values();

    @Nonnull
    public static EnumLeafState valueOf(int index) {
      return index < 0 || index > VALUES.length ? NORMAL : VALUES[index];
    }

    @Override
    public String getName() {
      return this.name().toLowerCase();
    }
  }
}
