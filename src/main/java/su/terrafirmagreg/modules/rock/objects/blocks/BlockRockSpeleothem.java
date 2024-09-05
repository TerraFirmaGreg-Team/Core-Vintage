package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import java.util.Random;

/**
 * Stalactites and stalagmites in one block!
 */

@SuppressWarnings("deprecation")
public class BlockRockSpeleothem extends BlockRock {

  public static final PropertyEnum<EnumSize> SIZE = PropertyEnum.create("size", EnumSize.class);

  public BlockRockSpeleothem(RockBlockVariant variant, RockType type) {
    super(variant, type);

    getSettings()
        .nonCube();

    setDefaultState(getBlockState().getBaseState()
        .withProperty(SIZE, EnumSize.MEDIUM));
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return getBearing(worldIn, pos) > 0;
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
      EntityLivingBase placer, ItemStack stack) {
    var size = EnumSize.values()[Math.max(0, getBearing(worldIn, pos) - 1)];
    worldIn.setBlockState(pos, state.withProperty(SIZE, size));
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
      BlockPos fromPos) {
    int size = state.getValue(SIZE).strength;
    if (getBearing(worldIn, pos) < size + 1) {
      worldIn.playEvent(2001, pos, Block.getStateId(worldIn.getBlockState(pos)));
      dropBlockAsItem(worldIn, pos, state, 0);
      worldIn.setBlockToAir(pos);
    }
  }

  @Override
  public int quantityDropped(IBlockState state, int fortune, Random random) {
    return 1 + random.nextInt(3);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsRock.LOOSE.get(getType());
  }

  @Override
  public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
    return true;
  }

  @Override
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return false;
  }

  private int getBearing(IBlockAccess world, BlockPos pos) {
    return Math.max(getStrength(world, pos.down()), getStrength(world, pos.up()));
  }

  private int getStrength(IBlockAccess world, BlockPos pos) {
    var state = world.getBlockState(pos);
    if (state.isFullBlock()) {
      return 3;
    }

    if (state.getPropertyKeys().contains(SIZE)) {
      return state.getValue(SIZE).strength;
    }

    return 0;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return state.getValue(SIZE).aabb;
  }

  @Override
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn,
      BlockPos pos) {
    return getBoundingBox(blockState, worldIn, pos);
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state,
      BlockPos blockPos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @Override
  public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
    return true;
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, SIZE);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(SIZE).ordinal();
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(SIZE,
        EnumSize.values()[Math.min(EnumSize.values().length - 1, meta)]);
  }

  @Override
  public IBlockState getActualState(final IBlockState state, final IBlockAccess worldIn,
      final BlockPos pos) {
    var size = EnumSize.values()[Math.max(0, getBearing(worldIn, pos) - 1)];
    if (isCenter(worldIn, pos)) {
      size = EnumSize.MEDIUM;
    }
    return state.withProperty(SIZE, size);
  }

  private boolean isCenter(IBlockAccess world, BlockPos pos) {
    return isThis(world, pos.down()) && isThis(world, pos.up());
  }

  private boolean isThis(IBlockAccess world, BlockPos pos) {
    return world.getBlockState(pos).getBlock() instanceof BlockRockSpeleothem;
  }

  public enum EnumSize implements IStringSerializable {

    SMALL(0, 2),
    MEDIUM(1, 4),
    BIG(2, 8);

    public final int strength;
    public final AxisAlignedBB aabb;

    EnumSize(int strength, int width) {
      this.strength = strength;

      float pad = (((16 - width) / 2f) / 16F);
      aabb = new AxisAlignedBB(pad, 0F, pad, 1F - pad, 1F, 1F - pad);
    }

    @Override
    public String getName() {
      return name().toLowerCase();
    }
  }
}
