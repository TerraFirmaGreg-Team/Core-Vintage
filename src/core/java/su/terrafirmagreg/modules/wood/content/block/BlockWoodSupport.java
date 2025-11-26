package su.terrafirmagreg.modules.wood.content.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlock;
import su.terrafirmagreg.modules.wood.api.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.type.WoodType;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

import static su.terrafirmagreg.api.data.Properties.BoolProp.EAST;
import static su.terrafirmagreg.api.data.Properties.BoolProp.NORTH;
import static su.terrafirmagreg.api.data.Properties.BoolProp.SOUTH;
import static su.terrafirmagreg.api.data.Properties.BoolProp.WEST;
import static su.terrafirmagreg.api.data.Properties.EnumProp.AXIS;

@SuppressWarnings("deprecation")
@Getter
public class BlockWoodSupport extends BaseBlock implements IWoodEntry {

  private static final AxisAlignedBB VERTICAL_SUPPORT_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 1.0D, 0.6875D);
  private static final AxisAlignedBB HORIZONTAL_SUPPORT_AABB = new AxisAlignedBB(0.375D, 0.625D, 0.375D, 0.625D, 1.0D, 0.625D);
  private static final AxisAlignedBB CONNECTION_N_AABB = new AxisAlignedBB(0.3125D, 0.625D, 0.0D, 0.6875D, 1.0D, 0.3125D);
  private static final AxisAlignedBB CONNECTION_S_AABB = new AxisAlignedBB(0.3125D, 0.625D, 0.6875D, 0.6875D, 1.0D, 1.0);
  private static final AxisAlignedBB CONNECTION_E_AABB = new AxisAlignedBB(0.6875D, 0.625D, 0.3125D, 1.0D, 1.0D, 0.6875D);
  private static final AxisAlignedBB CONNECTION_W_AABB = new AxisAlignedBB(0.0D, 0.625D, 0.3125D, 0.3125D, 1.0D, 0.6875D);

  protected final WoodType type;

  public BlockWoodSupport(WoodType type) {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .customResource(type.getResource("support"))
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .addOreDict("support")
      .hardness(2.0F)
      .nonFullCube()
      .nonOpaque()
    );

    this.type = type;
    setDefaultState(getBlockState().getBaseState()
      .withProperty(AXIS, EnumFacing.Axis.Y)
      .withProperty(NORTH, Boolean.FALSE)
      .withProperty(SOUTH, Boolean.FALSE)
      .withProperty(EAST, Boolean.FALSE)
      .withProperty(WEST, Boolean.FALSE));
  }

  /**
   * Checks if this support block can support collapsable/fallable blocks Returns true only if this is horizontally placed and can stay in place.
   *
   * @param world the worldObj this support block is in
   * @param pos   the BlockPos this support block is in
   * @return true if this can support blocks
   */
  public boolean canSupportBlocks(IBlockAccess world, BlockPos pos) {
    return canBlockStay(world, pos) && world.getBlockState(pos).getValue(AXIS) != EnumFacing.Axis.Y;
  }

  /**
   * Checks if this support can stay in pos
   *
   * @param world the world obj
   * @param pos   the pos of this support
   * @return true if this support can stay in this position
   */
  private boolean canBlockStay(IBlockAccess world, BlockPos pos) {
    IBlockState state = world.getBlockState(pos);
    if (!(state.getBlock() instanceof BlockWoodSupport)) {
      return false;
    }
    EnumFacing.Axis axis = state.getValue(AXIS);
    if (axis == EnumFacing.Axis.Y) {
      return world.getBlockState(pos.down()).isNormalCube() || isConnectable(world, pos,
        EnumFacing.DOWN);
    } else {
      return (isConnectable(world, pos, EnumFacing.WEST) && isConnectable(world, pos, EnumFacing.EAST))
             || (isConnectable(world, pos, EnumFacing.NORTH) && isConnectable(world, pos, EnumFacing.SOUTH));
    }
  }

  /**
   * Check if the facing can connect
   *
   * @param world  the worldObj to check
   * @param pos    the BlockPos the current block is in
   * @param facing the facing to check for connection
   * @return true if the facing has another support block and it's Axis is Y or facing this connection
   */
  private boolean isConnectable(IBlockAccess world, BlockPos pos, EnumFacing facing) {
    IBlockState state = world.getBlockState(pos.offset(facing));
    return state.getBlock() instanceof BlockWoodSupport;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(AXIS, EnumFacing.Axis.values()[meta]);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(AXIS).ordinal();
  }

  @Override
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return state.withProperty(NORTH, isConnectable(worldIn, pos, EnumFacing.NORTH))
      .withProperty(SOUTH, isConnectable(worldIn, pos, EnumFacing.SOUTH))
      .withProperty(EAST, isConnectable(worldIn, pos, EnumFacing.EAST))
      .withProperty(WEST, isConnectable(worldIn, pos, EnumFacing.WEST));
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    AxisAlignedBB value =
      state.getValue(AXIS) == EnumFacing.Axis.Y ? VERTICAL_SUPPORT_AABB : HORIZONTAL_SUPPORT_AABB;
    if (isConnectable(source, pos, EnumFacing.NORTH)) {
      value = value.union(CONNECTION_N_AABB);
    }
    if (isConnectable(source, pos, EnumFacing.SOUTH)) {
      value = value.union(CONNECTION_S_AABB);
    }
    if (isConnectable(source, pos, EnumFacing.EAST)) {
      value = value.union(CONNECTION_E_AABB);
    }
    if (isConnectable(source, pos, EnumFacing.WEST)) {
      value = value.union(CONNECTION_W_AABB);
    }
    return value;
  }

  @SuppressWarnings("deprecation")
  @Override
  public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos,
                                    AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes,
                                    @Nullable Entity entityIn,
                                    boolean isActualState) {
    EnumFacing.Axis axis = state.getValue(AXIS);
    if (axis == EnumFacing.Axis.Y) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, VERTICAL_SUPPORT_AABB);
    } else {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, HORIZONTAL_SUPPORT_AABB);
    }
    if (isConnectable(worldIn, pos, EnumFacing.NORTH)) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, CONNECTION_N_AABB);
    }
    if (isConnectable(worldIn, pos, EnumFacing.SOUTH)) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, CONNECTION_S_AABB);
    }
    if (isConnectable(worldIn, pos, EnumFacing.EAST)) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, CONNECTION_E_AABB);
    }
    if (isConnectable(worldIn, pos, EnumFacing.WEST)) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, CONNECTION_W_AABB);
    }
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
                              BlockPos fromPos) {
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    if (!this.canBlockStay(worldIn, pos)) {
      worldIn.destroyBlock(pos, true);
    }
  }

  @Override
  public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
    if (side.getAxis() == EnumFacing.Axis.Y) {
      return world.getBlockState(pos.down()).isNormalCube() || isConnectable(world, pos,
        EnumFacing.DOWN);
    } else {
      if (!isConnectable(world, pos, side.getOpposite())) {
        return false;
      }
      int distance = getHorizontalDistance(side, world, pos);
      return distance > 0;
    }
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                  EnumHand hand, EnumFacing facing, float hitX,
                                  float hitY, float hitZ) {
    ItemStack heldStack = player.getHeldItem(hand);
    if (player.isSneaking() && heldStack.getItem() instanceof ItemBlock itemBlock) {
      Block block = itemBlock.getBlock();
      if (block instanceof BlockWoodSupport) {
        for (BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(pos);
             mutablePos.getY() <= pos.getY() + 5; mutablePos.setY(mutablePos.getY() + 1)) {
          if (world.getBlockState(mutablePos).getMaterial().isReplaceable()) {
            if (!world.isRemote) {
              world.setBlockState(mutablePos, block.getDefaultState()
                .withProperty(AXIS, EnumFacing.Axis.Y), 2);
            }
            if (!player.isCreative()) {
              heldStack.shrink(1);
            }
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing,
                                          float hitX, float hitY, float hitZ, int meta,
                                          EntityLivingBase placer) {
    return this.getDefaultState().withProperty(AXIS, facing.getAxis());
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
                              EntityLivingBase placer, ItemStack stack) {
    if (worldIn.isRemote) {
      return;
    }
    EnumFacing.Axis axis = state.getValue(AXIS);
    if (axis == EnumFacing.Axis.Y) {
      //Try placing a 3 blocks high column in one click
      if (!isConnectable(worldIn, pos, EnumFacing.DOWN)
          && !placer.isSneaking() && stack.getCount() > 2
          //Need 3 or more because at this point itemstack didn't shrink for the first block
          && worldIn.isAirBlock(pos.up()) && worldIn.isAirBlock(pos.up(2))) {
        //Place two more support blocks to make a 3 column in one click
        if (worldIn.checkNoEntityCollision(new AxisAlignedBB(pos.up()))) {
          worldIn.setBlockState(pos.up(),
            this.getDefaultState().withProperty(AXIS, EnumFacing.Axis.Y), 2);
          if (worldIn.checkNoEntityCollision(new AxisAlignedBB(pos.up(2)))) {
            worldIn.setBlockState(pos.up(2), this.getDefaultState()
              .withProperty(AXIS, EnumFacing.Axis.Y), 2);
            stack.shrink(2);
          } else {
            stack.shrink(1);
          }
        }
      }
    } else {
      //Try placing all horizontally placed blocks in one go
      EnumFacing face = EnumFacing.getFacingFromAxis(EnumFacing.AxisDirection.NEGATIVE, axis);
      if (isConnectable(worldIn, pos, face)) {
        face = face.getOpposite();
      }
      int distance = getHorizontalDistance(face, worldIn, pos);
      if (distance == 0 || stack.getCount() < distance) {
        //Another vertical support to connect not found or player don't have enough items to place.
        worldIn.destroyBlock(pos, true);
      } else if (distance > 0) {
        stack.shrink(distance - 1); //-1 because the first one is already placed by onBlockPlace
        for (int i = 1; i < distance; i++) {
          if (worldIn.getBlockState(pos.offset(face, i)).getMaterial().isReplaceable()) {
            worldIn.setBlockState(pos.offset(face, i),
              this.getDefaultState().withProperty(AXIS, axis), 2);
            worldIn.scheduleBlockUpdate(pos.offset(face, i)
              .down(), worldIn.getBlockState(pos.offset(face, i).down())
              .getBlock(), 3, 2);
          }
        }
      }
    }
  }

  @Override
  public BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, AXIS, NORTH, SOUTH, EAST, WEST);
  }

  /**
   * Checks the distance to a vertical support, in blocks
   *
   * @param face    the EnumFacing to check, please use N-S-W-E
   * @param worldIn the worldObj to check blocks
   * @param pos     the BlockPos to start
   * @return 0 if not found, 1-5 block distance between this BlockPos and the found vertical support
   */
  private int getHorizontalDistance(EnumFacing face, IBlockAccess worldIn, BlockPos pos) {
    // if the placement block on the clicked side is not three tall don't bother checking for length
    if (!isThreeTall(worldIn, pos.offset(face.getOpposite()))) {
      return 0;
    }
    // look across the gap for valid distance
    int distance = -1;
    for (int i = 0; i < 5; i++) {
      BlockPos offsetPos = pos.offset(face, i);
      if (!(worldIn.getBlockState(offsetPos)
              .getBlock() instanceof BlockWoodSupport) && !worldIn.isAirBlock(offsetPos)) {
        return 0;
      }
      IBlockState state = worldIn.getBlockState(pos.offset(face, i + 1));
      if (state.getBlock() instanceof BlockWoodSupport
          && state.getValue(AXIS) == EnumFacing.Axis.Y) {
        distance = i;
        break;
      }
    }

    // if another side wasn't found, fail
    if (distance == -1) {
      return 0;
    }

    // if the other side isn't three tall, fail
    if (!isThreeTall(worldIn, pos.offset(face, distance + 1))) {
      return 0;
    }

    // return the distance + 1 because the distance checked is off by one for the loop
    return distance + 1;
  }

  /**
   * Checks if this block is a vertical support beam of height 3 or higher
   *
   * @param world the world this block is in
   * @param pos   the position of the block
   * @return true if this is a vertical support beam three blocks or higher, false otherwise
   */
  private boolean isThreeTall(IBlockAccess world, BlockPos pos) {
    // if the block is invalid it definitely can't support a vertical beam
    if (!canBlockStay(world, pos)) {
      return false;
    }
    IBlockState state = world.getBlockState(pos);
    EnumFacing.Axis axis = state.getValue(AXIS);
    // sideways supports are never three tall
    if (axis != EnumFacing.Axis.Y) {
      return false;
    }
    // if either of the two block beneath this block are not block supports, then this isn't three tall
    if (!(world.getBlockState(pos.down()).getBlock() instanceof BlockWoodSupport)) {
      return false;
    }
    return world.getBlockState(pos.down().down()).getBlock() instanceof BlockWoodSupport;
  }
}
