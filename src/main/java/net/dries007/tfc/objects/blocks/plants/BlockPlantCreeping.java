package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.objects.blocks.wood.BlockLeavesTFC;
import net.dries007.tfc.util.climate.Climate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

import static su.terrafirmagreg.data.Properties.BoolProp.ALL_FACES;
import static su.terrafirmagreg.data.Properties.BoolProp.DOWN;
import static su.terrafirmagreg.data.Properties.BoolProp.EAST;
import static su.terrafirmagreg.data.Properties.BoolProp.NORTH;
import static su.terrafirmagreg.data.Properties.BoolProp.SOUTH;
import static su.terrafirmagreg.data.Properties.BoolProp.UP;
import static su.terrafirmagreg.data.Properties.BoolProp.WEST;
import static su.terrafirmagreg.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.data.Properties.IntProp.DAYPERIOD;

public class BlockPlantCreeping extends BlockPlant {


  private static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
  private static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.0D, 0.875D, 0.0D, 1.0D, 1.0D, 1.0D);
  private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
  private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
  private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
  private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);

  private static final Map<PlantType, BlockPlantCreeping> MAP = new HashMap<>();

  public BlockPlantCreeping(PlantType plant) {
    super(plant);
    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
  }

  public static BlockPlantCreeping get(PlantType plant) {
    return BlockPlantCreeping.MAP.get(plant);
  }

  @Override
  @NotNull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, DOWN, UP, NORTH, EAST, WEST, SOUTH, growthStageProperty, DAYPERIOD, AGE_4);
  }

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

  @Override
  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return EnumOffsetType.NONE;
  }

  @Override
  protected boolean canSustainBush(IBlockState state) {
    return true;
  }

  @Override
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    for (EnumFacing face : EnumFacing.values()) {
      IBlockState blockState = worldIn.getBlockState(pos.offset(face));
      if (!(blockState.getBlock() instanceof BlockLeavesTFC) &&
          (blockState.getBlockFaceShape(worldIn, pos.offset(face), face.getOpposite()) == BlockFaceShape.SOLID ||
           blockState.getBlock() instanceof BlockFence)) {
        return plant.isValidTemp(Climate.getActualTemp(worldIn, pos)) && plant.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
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

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    switch (rot) {
      case CLOCKWISE_180:
        return state.withProperty(growthStageProperty, plant.getStageForMonth())
                    .withProperty(NORTH, state.getValue(SOUTH))
                    .withProperty(EAST, state.getValue(WEST))
                    .withProperty(SOUTH, state.getValue(NORTH))
                    .withProperty(WEST, state.getValue(EAST));
      case COUNTERCLOCKWISE_90:
        return state.withProperty(growthStageProperty, plant.getStageForMonth())
                    .withProperty(NORTH, state.getValue(EAST))
                    .withProperty(EAST, state.getValue(SOUTH))
                    .withProperty(SOUTH, state.getValue(WEST))
                    .withProperty(WEST, state.getValue(NORTH));
      case CLOCKWISE_90:
        return state.withProperty(growthStageProperty, plant.getStageForMonth())
                    .withProperty(NORTH, state.getValue(WEST))
                    .withProperty(EAST, state.getValue(NORTH))
                    .withProperty(SOUTH, state.getValue(EAST))
                    .withProperty(WEST, state.getValue(SOUTH));
      default:
        return state.withProperty(growthStageProperty, plant.getStageForMonth());
    }
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
    switch (mirrorIn) {
      case LEFT_RIGHT:
        return state.withProperty(growthStageProperty, plant.getStageForMonth())
                    .withProperty(NORTH, state.getValue(SOUTH))
                    .withProperty(SOUTH, state.getValue(NORTH));
      case FRONT_BACK:
        return state.withProperty(growthStageProperty, plant.getStageForMonth())
                    .withProperty(EAST, state.getValue(WEST))
                    .withProperty(WEST, state.getValue(EAST));
      default:
        return super.withMirror(state, mirrorIn);
    }
  }

  @Override
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    return true;
  }

  @Override
  public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
    return canConnectTo(world, pos.offset(facing), facing.getOpposite()) && !(world.getBlockState(pos.offset(facing))
                                                                                   .getBlock() instanceof BlockFence);
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
  @NotNull
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }
}
