package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.data.enums.EnumSpeleothemSize;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.EnumProp.SPELEOTHEM_SIZE;

/**
 * Stalactites and stalagmites in one block!
 */

@SuppressWarnings("deprecation")
public class BlockRockSpeleothem extends BlockRock {


  public BlockRockSpeleothem(RockBlockVariant variant, RockType type) {
    super(variant, type);

    getSettings()
      .nonCube();

    setDefaultState(blockState.getBaseState()
                              .withProperty(SPELEOTHEM_SIZE, EnumSpeleothemSize.MEDIUM));
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state,
                                          BlockPos blockPos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @Override
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn,
                                               BlockPos pos) {
    return getBoundingBox(blockState, worldIn, pos);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(SPELEOTHEM_SIZE).ordinal();
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(SPELEOTHEM_SIZE,
                                          EnumSpeleothemSize.values()[Math.min(EnumSpeleothemSize.values().length - 1, meta)]);
  }

  @Override
  public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn,
                                    final BlockPos pos) {
    var size = EnumSpeleothemSize.values()[Math.max(0, getBearing(worldIn, pos) - 1)];
    if (isCenter(worldIn, pos)) {
      size = EnumSpeleothemSize.MEDIUM;
    }
    return state.withProperty(SPELEOTHEM_SIZE, size);
  }

  @Override
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return false;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return state.getValue(SPELEOTHEM_SIZE).aabb;
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
                              BlockPos fromPos) {
    int size = state.getValue(SPELEOTHEM_SIZE).strength;
    if (getBearing(worldIn, pos) < size + 1) {
      worldIn.playEvent(2001, pos, Block.getStateId(worldIn.getBlockState(pos)));
      dropBlockAsItem(worldIn, pos, state, 0);
      worldIn.setBlockToAir(pos);
    }
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsRock.LOOSE.get(type);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return getBearing(worldIn, pos) > 0;
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
                              EntityLivingBase placer, ItemStack stack) {
    var size = EnumSpeleothemSize.values()[Math.max(0, getBearing(worldIn, pos) - 1)];
    worldIn.setBlockState(pos, state.withProperty(SPELEOTHEM_SIZE, size));
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, SPELEOTHEM_SIZE);
  }

  @Override
  public int quantityDropped(IBlockState state, int fortune, Random random) {
    return 1 + random.nextInt(3);
  }

  @Override
  public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    return true;
  }

  @Override
  public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
    return true;
  }

  private int getBearing(IBlockAccess world, BlockPos pos) {
    return Math.max(getStrength(world, pos.down()), getStrength(world, pos.up()));
  }

  private boolean isCenter(IBlockAccess world, BlockPos pos) {
    return isThis(world, pos.down()) && isThis(world, pos.up());
  }

  private int getStrength(IBlockAccess world, BlockPos pos) {
    var state = world.getBlockState(pos);
    if (state.isFullBlock()) {
      return 3;
    }

    if (state.getPropertyKeys().contains(SPELEOTHEM_SIZE)) {
      return state.getValue(SPELEOTHEM_SIZE).strength;
    }

    return 0;
  }

  private boolean isThis(IBlockAccess world, BlockPos pos) {
    return world.getBlockState(pos).getBlock() instanceof BlockRockSpeleothem;
  }

}
