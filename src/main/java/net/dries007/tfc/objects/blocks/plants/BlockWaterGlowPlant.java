package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.objects.blocks.BlockFluidTFC;
import net.dries007.tfc.objects.blocks.groundcover.BlockCoralBlock;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.util.climate.Climate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Properties.BoolProp.ALL_FACES;
import static su.terrafirmagreg.data.Properties.BoolProp.DOWN;
import static su.terrafirmagreg.data.Properties.BoolProp.EAST;
import static su.terrafirmagreg.data.Properties.BoolProp.NORTH;
import static su.terrafirmagreg.data.Properties.BoolProp.SOUTH;
import static su.terrafirmagreg.data.Properties.BoolProp.UP;
import static su.terrafirmagreg.data.Properties.BoolProp.WEST;

public class BlockWaterGlowPlant extends BlockFluidTFC implements ICapabilitySize, IPlantable {


  private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.1D, 0.2D, 0.1D, 0.9D, 1.0D, 0.9D);
  private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);
  private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.1D, 0.1D, 0.0D, 0.9D, 0.9D, 0.8D);
  private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.1D, 0.1D, 0.2D, 0.9D, 0.9D, 1.0D);
  private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.2D, 0.1D, 0.1D, 1.0D, 0.9D, 0.9D);
  private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.1D, 0.1D, 0.8D, 0.9D, 0.9D);

  public BlockWaterGlowPlant(Fluid fluid) {
    this(fluid, Material.WATER);
  }

  public BlockWaterGlowPlant(Fluid fluid, Material materialIn) {
    super(fluid, Material.WATER, false);
    this.setSoundType(SoundType.PLANT);
    this.setHardness(0.0F);
    this.setLightOpacity(0);
    this.setLightLevel(1F);
    BlockUtils.setFireInfo(this, 5, 20);
    this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0));
    this.canCreateSources = false;
  }

  @Override
  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.VERY_LIGHT; // Stacksize = 64
  }

  @Override
  public @NotNull Size getSize(ItemStack stack) {
    return Size.TINY; // Store anywhere
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return super.getActualState(state, worldIn, pos)
                .withProperty(DOWN, canPlantConnectTo(worldIn, pos, EnumFacing.DOWN))
                .withProperty(UP, canPlantConnectTo(worldIn, pos, EnumFacing.UP))
                .withProperty(NORTH, canPlantConnectTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(EAST, canPlantConnectTo(worldIn, pos, EnumFacing.EAST))
                .withProperty(SOUTH, canPlantConnectTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST, canPlantConnectTo(worldIn, pos, EnumFacing.WEST));
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
  public boolean isBlockNormalCube(IBlockState blockState) {
    return false;
  }

  @NotNull
  @Override
  public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
    return true;
  }

  @SuppressWarnings("deprecation")
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

  @SuppressWarnings("deprecation")
  @Override
  @SideOnly(Side.CLIENT)
  public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
    if (state.getBlock() instanceof BlockWaterGlowPlant) {
      return state.getBoundingBox(worldIn, pos).offset(pos);
    }
    return null;
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    for (EnumFacing face : EnumFacing.values()) {
      IBlockState up = worldIn.getBlockState(pos.up());
      IBlockState blockState = worldIn.getBlockState(pos.offset(face));
      if ((blockState.getBlockFaceShape(worldIn, pos.offset(face), face.getOpposite()) == BlockFaceShape.SOLID ||
           BlockUtils.isGround(blockState) || blockState.getBlock() instanceof BlockCoralBlock) &&
          (BlockUtils.isSaltWater(worldIn.getBlockState(pos.up())) || up.getBlock() instanceof BlockCoralBlock ||
           up.getBlock() instanceof BlockWaterGlowPlant)) {
        return Climate.getAvgTemp(worldIn, pos) >= 10f && ProviderChunkData.getRainfall(worldIn, pos) >= 100f;
      }
    }
    return false;
  }

  @Override
  public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
    if (!worldIn.isRemote) {
      spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
    }
  }

  @NotNull
  @Override
  @SideOnly(Side.CLIENT)
  public Block.EnumOffsetType getOffsetType() {
    return Block.EnumOffsetType.NONE;
  }

  @NotNull
  @Override
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    this.onBlockHarvested(world, pos, state, player);
    return world.setBlockState(pos, FluidsTFC.SALT_WATER.get()
                                                        .getBlock()
                                                        .getDefaultState(), world.isRemote ? 11 : 3);
  }

  @NotNull
  @Override
  @SideOnly(Side.CLIENT)
  public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
    return layer == BlockRenderLayer.TRANSLUCENT || layer == BlockRenderLayer.CUTOUT;
  }

  @NotNull
  @Override
  public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
    return canConnectTo(world, pos.offset(facing), facing.getOpposite()) && !(world.getBlockState(pos.offset(facing))
                                                                                   .getBlock() instanceof BlockFence);
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
    return blockfaceshape == BlockFaceShape.SOLID || block instanceof BlockFence;
  }

  protected boolean canSustainBush(IBlockState state) {
    return true;
  }

  @NotNull
  @Override
  protected BlockStateContainer createBlockState() {
    //return new BlockStateContainer(this, LEVEL, DOWN, UP, NORTH, EAST, WEST, SOUTH);
    return new BlockStateContainer.Builder(this)
      .add(LEVEL)
      .add(FLUID_RENDER_PROPS.toArray(new IUnlistedProperty<?>[0]))
      .add(DOWN)
      .add(UP)
      .add(NORTH)
      .add(EAST)
      .add(WEST)
      .add(SOUTH)
      .build();
  }

  @SuppressWarnings("deprecation")
  @NotNull
  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!worldIn.isRemote) {
      if (!canBlockStay(worldIn, pos, state)) {
        worldIn.destroyBlock(pos, true);
      }
    }
  }

  @NotNull
  @Override
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  @SuppressWarnings("deprecation")
  @NotNull
  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @SuppressWarnings("deprecation")
  @NotNull
  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  @NotNull
  @Override
  @SideOnly(Side.CLIENT)
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.TRANSLUCENT;
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @SuppressWarnings("deprecation")
  @Override
  @Nullable
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return NULL_AABB;
  }

  @NotNull
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    for (EnumFacing face : EnumFacing.values()) {
      IBlockState up = worldIn.getBlockState(pos.up());
      IBlockState blockState = worldIn.getBlockState(pos.offset(face));
      if ((blockState.getBlockFaceShape(worldIn, pos.offset(face), face.getOpposite()) == BlockFaceShape.SOLID ||
           BlockUtils.isGround(blockState) || blockState.getBlock() instanceof BlockCoralBlock) &&
          (BlockUtils.isSaltWater(worldIn.getBlockState(pos.up())) || up.getBlock() instanceof BlockCoralBlock ||
           up.getBlock() instanceof BlockWaterGlowPlant)) {
        return Climate.getAvgTemp(worldIn, pos) >= 10f && ProviderChunkData.getRainfall(worldIn, pos) >= 100f;
      }
    }
    return false;
  }

  protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (!this.canBlockStay(worldIn, pos, state)) {
      this.dropBlockAsItem(worldIn, pos, state, 0);
      worldIn.setBlockState(pos, FluidsTFC.SALT_WATER.get().getBlock().getDefaultState());
    }
  }

  public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
    return EnumPlantType.Plains;
  }

  public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
    return getDefaultState();
  }

  @Override
  public boolean canCollideCheck(IBlockState state, boolean fullHit) {
    return state.getBlock() instanceof BlockWaterGlowPlant && super.canCollideCheck(state, fullHit);
  }
}
