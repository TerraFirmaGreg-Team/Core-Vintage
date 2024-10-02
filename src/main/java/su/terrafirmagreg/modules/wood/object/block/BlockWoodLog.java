package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.player.CapabilityPlayer;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.wood.ConfigWood;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static su.terrafirmagreg.data.MathConstants.RNG;
import static su.terrafirmagreg.data.Properties.BoolProp.PLACED;
import static su.terrafirmagreg.data.Properties.BoolProp.SMALL;


@Getter
@SuppressWarnings("deprecation")
public class BlockWoodLog extends BlockLog implements IWoodBlock {

  public static final AxisAlignedBB SMALL_AABB_Y = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 1, 0.75);
  public static final AxisAlignedBB SMALL_AABB_X = new AxisAlignedBB(0, 0.25, 0.25, 1, 0.75, 0.75);
  public static final AxisAlignedBB SMALL_AABB_Z = new AxisAlignedBB(0.25, 0.25, 0, 0.75, 0.75, 1);

  protected final Settings settings;
  protected final WoodBlockVariant variant;
  protected final WoodType type;

  public BlockWoodLog(WoodBlockVariant variant, WoodType type) {
    this.variant = variant;
    this.type = type;
    this.settings = Settings.of(Material.WOOD);

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .randomTicks()
      .size(Size.VERY_LARGE)
      .weight(Weight.MEDIUM)
      .ignoresProperties(PLACED)
      .harvestLevel(ToolClasses.AXE, 0)
      .resistance(5.0F)
      .hardness(20.0F)//TODO 2.0 в тфк
      .lightValue(this.getDefaultState().getValue(SMALL) ? 0 : 255)
      .oreDict(variant)
      .oreDict(variant, type)
      .oreDict("logWood")
      .oreDict(type.isCanMakeTannin() ? "tannin" : null);

    setDefaultState(blockState.getBaseState()
                              .withProperty(LOG_AXIS, EnumAxis.Y)
                              .withProperty(PLACED, true)
                              .withProperty(SMALL, false));

    BlockUtils.setFireInfo(this, 5, 5);

  }


  @Override
  public boolean isFullBlock(IBlockState state) {
    return !state.getValue(SMALL);
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return !state.getValue(SMALL);
  }

  //TODO в этом не вижу смысла после добавления dt, так как все бревна, что имели бы свойство PLACED,
  // генерируются dt, а у них свой подсчет Hardness
  @Override
  public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
    return (blockState.getValue(PLACED) ? 1.0f : 2.5f) * super.getBlockHardness(blockState, worldIn, pos);
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    if (!state.getValue(SMALL)) {
      return FULL_BLOCK_AABB;
    }
    return switch (state.getValue(LOG_AXIS)) {
      case X -> SMALL_AABB_X;
      case Y -> SMALL_AABB_Y;
      case Z -> SMALL_AABB_Z;
      default -> FULL_BLOCK_AABB;
    };
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return !state.getValue(SMALL);
  }

  @Override
  public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
    // For floating tree things, just make them gently disappear over time
    if (state.getValue(PLACED)) {
      return;
    }
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        for (int z = -1; z <= 1; z++) {
          if (world.getBlockState(pos.add(x, y, z)).getBlock() == this && (z != 0 || y != 0 || x != 0)) {
            return;
          }
        }
      }
    }
    world.setBlockToAir(pos);
  }

  @Override
  public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
    if (!worldIn.isRemote) {
      removeTree(worldIn, pos, null, ItemStack.EMPTY, false);
    }
  }

  @Override //TODO DT
  public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tile, ItemStack stack) {
    // Do this check again, so we can drop items now
    final Set<String> toolClasses = stack.getItem().getToolClasses(stack);
    if (toolClasses.contains("axe") || toolClasses.contains("saw")) {
      // Harvest the block normally, saws and axes are valid tools regardless
      super.harvestBlock(worldIn, player, pos, state, tile, stack);
    } else if (toolClasses.contains("hammer") && ConfigWood.MISC.enableHammerSticks) {
      // Hammers drop sticks here - we duplicate the original method
      //noinspection ConstantConditions
      player.addStat(StatList.getBlockStats(this));
      player.addExhaustion(0.005F);

      if (!worldIn.isRemote) {
        StackUtils.spawnItemStack(worldIn, pos.add(0.5D, 0.5D, 0.5D), new ItemStack(Items.STICK, 1 + (int) (Math.random() * 3)));
      }
    } else if (ConfigWood.MISC.requiresAxe) {
      // Here, there was no valid tool used. Deny spawning any drops since logs require axes
      //noinspection ConstantConditions
      player.addStat(StatList.getBlockStats(this));
      player.addExhaustion(0.005F);
    } else {
      // No tool, but handle normally
      super.harvestBlock(worldIn, player, pos, state, tile, stack);
    }
  }

  @Override // TODO DT
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    ItemStack stack = ItemStack.EMPTY;
    var cap = CapabilityPlayer.get(player);
    if (cap != null) {
      stack = cap.getHarvestingTool();
    }
    if (stack.isEmpty()) {
      stack = player.getHeldItemMainhand();
    }
    final Set<String> toolClasses = stack.getItem().getToolClasses(stack);
    if (toolClasses.contains("axe") && !toolClasses.contains("saw")) {
      // Axes, not saws, cause tree felling
      if (!state.getValue(PLACED) && ConfigWood.MISC.enableFelling) {
        player.setHeldItem(EnumHand.MAIN_HAND,
                           stack); // Reset so we can damage however we want before vanilla
        if (!removeTree(world, pos, player, stack,
                        OreDictUtils.contains(stack, "axeStone") ||
                        OreDictUtils.contains(stack, "hammerStone"))) {
          // Don't remove the block, the rest of the tree broke instead
          return false;
        }
        return world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
      }
    } else if (toolClasses.contains("hammer") && ConfigWood.MISC.enableHammerSticks) {
      // Hammers drop sticks instead
      return world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
    } else if (!toolClasses.contains("saw") && ConfigWood.MISC.requiresAxe) {
      // Don't drop anything if broken by hand
      return world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
    }
    return super.removedByPlayer(state, world, pos, player, willHarvest);
  }

  @Override
  public boolean isToolEffective(String type, IBlockState state) {
    return ("hammer".equals(type) && ConfigWood.MISC.enableHammerSticks) || super.isToolEffective(type, state);
  }

  // TODO это нужно?
  private boolean removeTree(World world, BlockPos pos, @Nullable EntityPlayer player, ItemStack stack, boolean stoneTool) {
    final boolean explosion = stack.isEmpty() || player == null;
    final int maxLogs =
      explosion ? Integer.MAX_VALUE : 1 + stack.getMaxDamage() - stack.getItemDamage();

    // Find all logs and add them to a list
    List<BlockPos> logs = new ArrayList<>(50);
    Set<BlockPos> checked = new HashSet<>(50 * 3 * 3);
    logs.add(pos);
    for (int i = 0; i < logs.size(); i++) {
      final BlockPos pos1 = logs.get(i);
      // check for nearby logs
      for (int x = -1; x <= 1; x++) {
        for (int y = -1; y <= 1; y++) {
          for (int z = -1; z <= 1; z++) {
            final BlockPos pos2 = pos1.add(x, y, z);
            if (!checked.contains(pos2)) {
              checked.add(pos2);
              IBlockState state = world.getBlockState(pos2);
              if (state.getBlock() == this && !state.getValue(PLACED)) {
                logs.add(pos2);
              }
            }
          }
        }
      }
    }
    // Sort the list in terms of max distance to the original tree
    logs.sort(Comparator.comparing(x -> -x.distanceSq(pos)));

    // Start removing logs
    for (final BlockPos pos1 : logs.subList(0, Math.min(logs.size(), maxLogs))) {
      if (explosion) {
        // Explosions are 30% Efficient: no TNT powered tree farms.
        if (RNG.nextFloat() < 0.3) {
          if (!world.isRemote) {
            StackUtils.spawnItemStack(world, pos.add(0.5d, 0.5d, 0.5d),
                                      new ItemStack(Item.getItemFromBlock(this)));
          }
        }
      } else {
        // Stone tools are 60% efficient (default config)
        if (!stoneTool
            || RNG.nextFloat() < ConfigWood.MISC.stoneAxeReturnRate && !world.isRemote) {
          harvestBlock(world, player, pos1, world.getBlockState(pos1), null, stack);
        }
        stack.damageItem(1, player);
      }
      if (!world.isRemote) {
        world.setBlockToAir(pos1);
      }
    }
    return maxLogs >= logs.size();
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState()
      .withProperty(LOG_AXIS, EnumAxis.values()[meta & 0b11])
      .withProperty(PLACED, (meta & 0b100) == 0b100)
      .withProperty(SMALL, (meta & 0b1000) == 0b1000);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(LOG_AXIS).ordinal() |
           (state.getValue(PLACED) ? 0b100 : 0) |
           (state.getValue(SMALL) ? 0b1000 : 0);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, LOG_AXIS, PLACED, SMALL);
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    // Don't do vanilla leaf decay
  }

  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    // Small logs are a weird feature, for now they shall be disabled via shift placement since it interferes with log pile placement
    return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(PLACED, true);
  }


}
