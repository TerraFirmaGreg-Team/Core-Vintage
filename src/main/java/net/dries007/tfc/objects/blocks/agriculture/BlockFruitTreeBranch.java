package net.dries007.tfc.objects.blocks.agriculture;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.util.climate.ClimateTFC;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.EnumProp.FACING;
import static su.terrafirmagreg.api.data.Properties.IntProp.EAST_INT;
import static su.terrafirmagreg.api.data.Properties.IntProp.NORTH_INT;
import static su.terrafirmagreg.api.data.Properties.IntProp.SOUTH_INT;
import static su.terrafirmagreg.api.data.Properties.IntProp.UP_INT;
import static su.terrafirmagreg.api.data.Properties.IntProp.WEST_INT;

public class BlockFruitTreeBranch extends Block implements IGrowingPlant {

  /* Connection sides
   * 0 = no connection
   * 1 = connected, use vertical model
   * 2 = connected=, use horizontal model */

  private static final AxisAlignedBB TRUNK_N_AABB = new AxisAlignedBB(0.375D, 0.375D, 0.375D, 0.625D, 0.625D, 1.0D);
  private static final AxisAlignedBB TRUNK_E_AABB = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 0.625D, 0.625D, 0.625D);
  private static final AxisAlignedBB TRUNK_S_AABB = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 0.625D);
  private static final AxisAlignedBB TRUNK_W_AABB = new AxisAlignedBB(0.375D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);

  private static final AxisAlignedBB TRUNK_U_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 1.0D, 0.6875D);

  private static final AxisAlignedBB CONNECTION_N_AABB = new AxisAlignedBB(0.3125D, 0.375D, 0.0D, 0.0D, 0.625D, 0.3125D);
  private static final AxisAlignedBB CONNECTION_S_AABB = new AxisAlignedBB(0.3125D, 0.375D, 0.6875D, 0.0D, 0.625D, 1.0D);
  private static final AxisAlignedBB CONNECTION_W_AABB = new AxisAlignedBB(0.0D, 0.375D, 0.3125D, 0.3125D, 0.625D, 0.6875D);
  private static final AxisAlignedBB CONNECTION_E_AABB = new AxisAlignedBB(0.6875D, 0.375D, 0.3125D, 1.0D, 0.625D, 0.6875D);

  private static final Map<IFruitTree, BlockFruitTreeBranch> MAP = new HashMap<>();
  private final IFruitTree tree;

  public BlockFruitTreeBranch(IFruitTree tree) {
    super(Material.WOOD, Material.WOOD.getMaterialMapColor());
    if (MAP.put(tree, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
    setHardness(2.0F);
    setHarvestLevel("axe", 0);
    setSoundType(SoundType.WOOD);
    this.tree = tree;
    BlockUtils.setFireInfo(this, 5, 20);
    setDefaultState(blockState.getBaseState()
                              .withProperty(FACING, EnumFacing.UP)
                              .withProperty(NORTH_INT, 0)
                              .withProperty(EAST_INT, 0)
                              .withProperty(SOUTH_INT, 0)
                              .withProperty(WEST_INT, 0)
                              .withProperty(UP_INT, 0));
  }

  public static BlockFruitTreeBranch get(IFruitTree tree) {
    return MAP.get(tree);
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isTopSolid(IBlockState state) {
    return false;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isFullBlock(IBlockState state) {
    return false;
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return 0;
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState getActualState(@NotNull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    int connectedValue;
    EnumFacing face = getFacing(worldIn, pos);
    if (face == null || face == EnumFacing.UP || face == EnumFacing.DOWN) {
      // Vertical branch
      state = state.withProperty(FACING, EnumFacing.UP);
      connectedValue = 1;
    } else {
      // Horizontal branch
      state = state.withProperty(FACING, face);
      connectedValue = 2;
    }
    for (EnumFacing facing : EnumFacing.VALUES) {
      if (worldIn.getBlockState(pos.offset(facing)).getBlock() instanceof BlockFruitTreeLeaves) {
        if (facing == EnumFacing.NORTH) {
          state = state.withProperty(NORTH_INT, connectedValue);
        } else if (facing == EnumFacing.SOUTH) {
          state = state.withProperty(SOUTH_INT, connectedValue);
        } else if (facing == EnumFacing.EAST) {
          state = state.withProperty(EAST_INT, connectedValue);
        } else if (facing == EnumFacing.WEST) {
          state = state.withProperty(WEST_INT, connectedValue);
        } else if (facing == EnumFacing.UP) {
          state = state.withProperty(UP_INT, connectedValue);
        }
      }
    }
    return state;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isBlockNormalCube(IBlockState state) {
    return false;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isNormalCube(IBlockState state) {
    return false;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    state = getActualState(state, source, pos);
    AxisAlignedBB finalAABB;
    switch (state.getValue(FACING)) {
      case NORTH:
        finalAABB = TRUNK_N_AABB;
        break;
      case EAST:
        finalAABB = TRUNK_E_AABB;
        break;
      case SOUTH:
        finalAABB = TRUNK_S_AABB;
        break;
      case WEST:
        finalAABB = TRUNK_W_AABB;
        break;
      default:
        finalAABB = TRUNK_U_AABB;
    }
    if (state.getValue(NORTH_INT) > 0) {
      finalAABB = finalAABB.union(CONNECTION_N_AABB);
    }
    if (state.getValue(EAST_INT) > 0) {
      finalAABB = finalAABB.union(CONNECTION_E_AABB);
    }
    if (state.getValue(SOUTH_INT) > 0) {
      finalAABB = finalAABB.union(CONNECTION_S_AABB);
    }
    if (state.getValue(WEST_INT) > 0) {
      finalAABB = finalAABB.union(CONNECTION_W_AABB);
    }
    return finalAABB;
  }

  @Override
  @NotNull
  @SuppressWarnings("deprecation")
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @SuppressWarnings("deprecation")
  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    if (getFacing(worldIn, pos) == null) {
      worldIn.setBlockToAir(pos);
    }
  }

  @Override
  @NotNull
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return Items.AIR;
  }

  @Override
  public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
    ItemStack stack = player.getHeldItemMainhand();
    if (stack.getItem().getToolClasses(stack).contains("axe") || stack.getItem()
                                                                      .getToolClasses(stack)
                                                                      .contains("saw")) {
      if (!worldIn.isRemote && RANDOM.nextBoolean()) {
        ItemStack dropStack = new ItemStack(BlockFruitTreeSapling.get(tree));
        StackUtils.spawnItemStack(worldIn, pos, dropStack);
      }
    }
    super.onBlockHarvested(worldIn, pos, state, player);
  }

  @Override
  @NotNull
  public BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FACING, NORTH_INT, EAST_INT, SOUTH_INT, WEST_INT, UP_INT);
  }

  @Override
  public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
    return false;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return false;
  }

  @Override
  @NotNull
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(BlockFruitTreeSapling.get(tree));
  }

  private EnumFacing getFacing(IBlockAccess worldIn, BlockPos pos) {
    for (EnumFacing facing : EnumFacing.VALUES) {
      if (worldIn.getBlockState(pos.offset(facing)).getBlock() == BlockFruitTreeTrunk.get(tree)) {
        return facing.getOpposite();
      }
    }
    return null;
  }

  @NotNull
  public IFruitTree getTree() {
    return tree;
  }

  @Override
  public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
    float temp = ClimateTFC.getActualTemp(world, pos);
    float rainfall = ProviderChunkData.getRainfall(world, pos);
    boolean canGrow = tree.isValidForGrowth(temp, rainfall);
    if (canGrow) {
      return GrowthStatus.GROWING;
    }
    return GrowthStatus.NOT_GROWING;
  }
}
