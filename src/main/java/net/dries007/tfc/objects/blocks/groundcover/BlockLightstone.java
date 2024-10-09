package net.dries007.tfc.objects.blocks.groundcover;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Properties.DirectionProp.DIRECTIONAL;

public class BlockLightstone extends BlockBush implements ICapabilitySize {

  private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);
  private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.1D, 0.2D, 0.1D, 0.9D, 1.0D, 0.9D);
  private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.1D, 0.1D, 0.2D, 0.9D, 0.9D, 1.0D);
  private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.1D, 0.1D, 0.0D, 0.9D, 0.9D, 0.8D);
  private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.1D, 0.1D, 0.8D, 0.9D, 0.9D);
  private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.2D, 0.1D, 0.1D, 1.0D, 0.9D, 0.9D);

  protected final BlockStateContainer blockState;

  public BlockLightstone(float lightLevel) {
    super(Material.ROCK);
    setSoundType(SoundType.GLASS);
    setHardness(0.5f).setResistance(5.0F);
    this.setLightLevel(lightLevel);
    blockState = this.createBlockState();
    this.setDefaultState(this.blockState.getBaseState());
    OreDictionaryHelper.register(this, "ore", "yooperlite");
    OreDictionaryHelper.register(this, "gem", "yooperlite");
  }

  @NotNull
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, DIRECTIONAL);
  }

  @Override
  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.LIGHT;
  }

  @Override
  public @NotNull Size getSize(ItemStack stack) {
    return Size.TINY; // Store anywhere
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    for (EnumFacing enumfacing : DIRECTIONAL.getAllowedValues()) {
      if (this.canPlaceAt(worldIn, pos, enumfacing)) {
        return worldIn.getBlockState(pos).getBlock() != this;
      }
    }

    return false;
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    this.onNeighborChangeInternal(worldIn, pos, state);
  }

  @Override
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    for (EnumFacing enumfacing : DIRECTIONAL.getAllowedValues()) {
      if (this.canPlaceAt(worldIn, pos, enumfacing)) {
        return true;
      }
    }

    return false;
  }

  @Override
  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    switch (state.getValue(DIRECTIONAL)) {
      case EAST:
        return EAST_AABB;
      case WEST:
        return WEST_AABB;
      case SOUTH:
        return SOUTH_AABB;
      case NORTH:
        return NORTH_AABB;
      case DOWN:
        return DOWN_AABB;
      default:
        return UP_AABB;
    }
  }

  @Nullable
  @Override
  @SuppressWarnings("deprecation")
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return NULL_AABB;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  @Override
  @NotNull
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.CUTOUT;
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  private void onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
    if (this.checkForDrop(worldIn, pos, state)) {
      EnumFacing facing = state.getValue(DIRECTIONAL);
      EnumFacing.Axis axis = facing.getAxis();
      BlockPos blockpos = pos.offset(facing.getOpposite());
      boolean flag = false;

      if (axis.isHorizontal() && worldIn.getBlockState(blockpos)
                                        .getBlockFaceShape(worldIn, blockpos, facing) != BlockFaceShape.SOLID) {
        flag = true;
      } else if (axis.isVertical() && !this.canPlaceAt(worldIn, pos, state.getValue(DIRECTIONAL))) {
        flag = true;
      }

      if (flag) {
        worldIn.destroyBlock(pos, true);
      }
    }
  }

  private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
    if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, state.getValue(DIRECTIONAL))) {
      return true;
    } else {
      if (worldIn.getBlockState(pos).getBlock() == this) {
        checkAndDropBlock(worldIn, pos, state);
      }

      return false;
    }
  }

  private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
    BlockPos blockpos = pos.offset(facing.getOpposite());
    IBlockState iblockstate = worldIn.getBlockState(blockpos);
    BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

    return blockfaceshape == BlockFaceShape.SOLID || BlockUtils.isGround(iblockstate) ||
           worldIn.getBlockState(blockpos)
                  .isFullBlock();
  }

  public IBlockState getStateForWorldGen(World worldIn, BlockPos pos) {
    for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
      if (this.canPlaceAt(worldIn, pos, enumfacing)) {
        return this.getDefaultState().withProperty(DIRECTIONAL, enumfacing);
      }
    }
    for (EnumFacing enumfacing : EnumFacing.Plane.VERTICAL) {
      if (this.canPlaceAt(worldIn, pos, enumfacing)) {
        return this.getDefaultState().withProperty(DIRECTIONAL, enumfacing);
      }
    }

    return this.getDefaultState();
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isTopSolid(IBlockState state) {
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isFullBlock(IBlockState state) {
    return false;
  }

    /*@NotNull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return ItemOreTFC.get(TFCRegistries.ORES.getValue(OreTypesTFCF.YOOPERLITE));
    }*/

  @NotNull
  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(DIRECTIONAL, EnumFacing.byIndex(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(DIRECTIONAL).getIndex();
  }

  @SuppressWarnings("deprecation")
  @NotNull
  @Override
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    return state.withProperty(DIRECTIONAL, rot.rotate(state.getValue(DIRECTIONAL)));
  }

  @SuppressWarnings("deprecation")
  @NotNull
  @Override
  public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
    return state.withRotation(mirrorIn.toRotation(state.getValue(DIRECTIONAL)));
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isBlockNormalCube(IBlockState state) {
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isNormalCube(IBlockState state) {
    return false;
  }

  @Override
  public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    return false;
  }

  @SuppressWarnings("deprecation")
  @NotNull
  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
                                          EntityLivingBase placer) {
    if (this.canPlaceAt(worldIn, pos, facing)) {
      return this.getDefaultState().withProperty(DIRECTIONAL, facing);
    } else {
      for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
        if (this.canPlaceAt(worldIn, pos, enumfacing)) {
          return this.getDefaultState().withProperty(DIRECTIONAL, enumfacing);
        }
      }

      return this.getDefaultState();
    }
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
  public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
    return false;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return false;
  }

  @Override
  @NotNull
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(state.getBlock());
  }
}
