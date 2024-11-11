package su.terrafirmagreg.modules.plant.object.block;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.api.types.variant.block.PlantBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.objects.blocks.wood.BlockLogTFC;
import net.dries007.tfc.util.climate.ClimateTFC;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.api.data.Properties.DirectionProp.DIRECTIONAL;
import static su.terrafirmagreg.api.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.api.data.Properties.IntProp.DAYPERIOD;

public class BlockPlantEpiphyte extends BlockPlant {

  private static final AxisAlignedBB PLANT_UP_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.75D, 0.75D);
  private static final AxisAlignedBB PLANT_DOWN_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.25D, 0.75D, 1.0D, 0.75D);
  private static final AxisAlignedBB PLANT_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
  private static final AxisAlignedBB PLANT_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
  private static final AxisAlignedBB PLANT_WEST_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
  private static final AxisAlignedBB PLANT_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);

  public BlockPlantEpiphyte(PlantBlockVariant variant, PlantType type) {
    super(variant, type);
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    this.onNeighborChangeInternal(worldIn, pos, state);
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
      } else if (axis.isVertical() && !this.canPlaceOn(worldIn, blockpos)) {
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

  private boolean canPlaceOn(World worldIn, BlockPos pos) {
    IBlockState state = worldIn.getBlockState(pos);
    return state.getBlock() instanceof BlockLogTFC;
  }

  private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
    BlockPos blockpos = pos.offset(facing.getOpposite());
    IBlockState iblockstate = worldIn.getBlockState(blockpos);
    BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

    return this.canPlaceOn(worldIn, blockpos) && blockfaceshape == BlockFaceShape.SOLID;
  }

  @NotNull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, DIRECTIONAL, STAGE, DAYPERIOD, AGE_4);
  }

  @NotNull
  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(DIRECTIONAL, EnumFacing.byIndex(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(DIRECTIONAL).getIndex();
  }

  @Override
  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return EnumOffsetType.NONE;
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
  protected boolean canSustainBush(IBlockState state) {
    return true;
  }

  @Override
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    for (EnumFacing enumfacing : DIRECTIONAL.getAllowedValues()) {
      if (this.canPlaceAt(worldIn, pos, enumfacing)) {
        return type.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) && type.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
      }
    }

    return false;
  }

  @Override
  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    switch (state.getValue(DIRECTIONAL)) {
      case EAST:
        return PLANT_EAST_AABB;
      case WEST:
        return PLANT_WEST_AABB;
      case SOUTH:
        return PLANT_SOUTH_AABB;
      case NORTH:
        return PLANT_NORTH_AABB;
      case DOWN:
        return PLANT_DOWN_AABB;
      default:
        return PLANT_UP_AABB;
    }
  }


  @Override
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    return state.withProperty(DIRECTIONAL, rot.rotate(state.getValue(DIRECTIONAL)));
  }


  @Override
  public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
    return state.withRotation(mirrorIn.toRotation(state.getValue(DIRECTIONAL)));
  }


  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
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
}
