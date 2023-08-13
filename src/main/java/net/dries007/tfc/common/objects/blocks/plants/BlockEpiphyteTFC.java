package net.dries007.tfc.common.objects.blocks.plants;

import net.dries007.tfc.api.types.plant.type.PlantType;
import net.dries007.tfc.api.types.plant.variant.PlantBlockVariant;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodLog;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
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

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class BlockEpiphyteTFC extends BlockPlantTFC {
    private static final PropertyDirection FACING = PropertyDirection.create("facing");
    private static final AxisAlignedBB PLANT_UP_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.75D, 0.75D);
    private static final AxisAlignedBB PLANT_DOWN_AABB = new AxisAlignedBB(0.25D, 0.25D, 0.25D, 0.75D, 1.0D, 0.75D);
    private static final AxisAlignedBB PLANT_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB PLANT_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
    private static final AxisAlignedBB PLANT_WEST_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    private static final AxisAlignedBB PLANT_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);

    private final PlantType plant;

    public BlockEpiphyteTFC(PlantBlockVariant plantBlockVariant, PlantType plant) {
        super(plantBlockVariant, plant);

        this.plant = plant;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        this.onNeighborChangeInternal(worldIn, pos, state);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        world.setBlockState(pos, state.withProperty(DAYPERIOD, getDayPeriod()).withProperty(growthStageProperty, plant.getStageForMonth()));
        checkAndDropBlock(world, pos, state);
    }

    @Override
    @Nonnull
    public Block.EnumOffsetType getOffsetType() {
        return EnumOffsetType.NONE;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        for (EnumFacing enumfacing : FACING.getAllowedValues()) {
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
        for (EnumFacing enumfacing : FACING.getAllowedValues()) {
            if (this.canPlaceAt(worldIn, pos, enumfacing)) {
                return plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plant.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos));
            }
        }

        return false;
    }

    @Override
    @Nonnull
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return switch (state.getValue(FACING)) {
            case EAST -> PLANT_EAST_AABB;
            case WEST -> PLANT_WEST_AABB;
            case SOUTH -> PLANT_SOUTH_AABB;
            case NORTH -> PLANT_NORTH_AABB;
            case DOWN -> PLANT_DOWN_AABB;
            default -> PLANT_UP_AABB;
        };
    }

    @Nonnull
    protected BlockStateContainer createPlantBlockState() {
        return new BlockStateContainer(this, FACING, growthStageProperty, DAYPERIOD, AGE);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (this.canPlaceAt(worldIn, pos, facing)) {
            return this.getDefaultState().withProperty(FACING, facing);
        } else {
            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
                if (this.canPlaceAt(worldIn, pos, enumfacing)) {
                    return this.getDefaultState().withProperty(FACING, enumfacing);
                }
            }

            return this.getDefaultState();
        }
    }

    public IBlockState getStateForWorldGen(World worldIn, BlockPos pos) {
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
            if (this.canPlaceAt(worldIn, pos, enumfacing)) {
                return this.getDefaultState().withProperty(FACING, enumfacing);
            }
        }
        for (EnumFacing enumfacing : EnumFacing.Plane.VERTICAL) {
            if (this.canPlaceAt(worldIn, pos, enumfacing)) {
                return this.getDefaultState().withProperty(FACING, enumfacing);
            }
        }

        return this.getDefaultState();
    }

    private void onNeighborChangeInternal(World worldIn, BlockPos pos, IBlockState state) {
        if (this.checkForDrop(worldIn, pos, state)) {
            EnumFacing facing = state.getValue(FACING);
            EnumFacing.Axis axis = facing.getAxis();
            BlockPos blockpos = pos.offset(facing.getOpposite());
            boolean flag = false;

            if (axis.isHorizontal() && worldIn.getBlockState(blockpos).getBlockFaceShape(worldIn, blockpos, facing) != BlockFaceShape.SOLID) {
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
        if (state.getBlock() == this && this.canPlaceAt(worldIn, pos, state.getValue(FACING))) {
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
        return state.getBlock() instanceof BlockWoodLog;
    }

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

        return this.canPlaceOn(worldIn, blockpos) && blockfaceshape == BlockFaceShape.SOLID;
    }
}
