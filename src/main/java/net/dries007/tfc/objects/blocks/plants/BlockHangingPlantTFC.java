package net.dries007.tfc.objects.blocks.plants;

import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockHangingPlantTFC extends BlockCreepingPlantTFC implements IGrowable {

    private static final PropertyBool BOTTOM = PropertyBool.create("bottom");
    private static final Map<Plant, BlockHangingPlantTFC> MAP = new HashMap();

    public BlockHangingPlantTFC(Plant plant) {
        super(plant);
        if (MAP.put(plant, this) != null) {
            throw new IllegalStateException("There can only be one.");
        }
    }

    public static BlockHangingPlantTFC get(Plant plant) {
        return (BlockHangingPlantTFC) MAP.get(plant);
    }

    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        IBlockState iblockstate = worldIn.getBlockState(pos.down(2));
        Material material = iblockstate.getMaterial();

        int i;
        for (i = 1; worldIn.getBlockState(pos.up(i)).getBlock() == this; ++i) {
        }

        return i < this.plant.getMaxHeight() && worldIn.isAirBlock(pos.down()) && (!material.isSolid() || material == Material.LEAVES) &&
                this.canBlockStay(worldIn, pos.down(), state);
    }

    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return false;
    }

    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        worldIn.setBlockState(pos.down(), this.getDefaultState());
        IBlockState iblockstate = state.withProperty(AGE, 0)
                .withProperty(this.growthStageProperty, this.plant.getStageForMonth())
                .withProperty(BOTTOM, false);
        worldIn.setBlockState(pos, iblockstate);
        iblockstate.neighborChanged(worldIn, pos.down(), this, pos);
    }

    public void shrink(World worldIn, BlockPos pos) {
        worldIn.setBlockToAir(pos);
        worldIn.getBlockState(pos).neighborChanged(worldIn, pos.up(), this, pos);
    }

    @NotNull
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState actualState = super.getActualState(state, worldIn, pos);
        if (worldIn.getBlockState(pos.down())
                .getBlock() == this && (Boolean) actualState.getValue(UP) && !(Boolean) actualState.getValue(DOWN) &&
                !(Boolean) actualState.getValue(NORTH) && !(Boolean) actualState.getValue(SOUTH) && !(Boolean) actualState.getValue(EAST) &&
                !(Boolean) actualState.getValue(WEST)) {
            actualState = actualState.withProperty(NORTH, true)
                    .withProperty(SOUTH, true)
                    .withProperty(EAST, true)
                    .withProperty(WEST, true);
        }

        if (worldIn.getBlockState(pos.up())
                .getBlock() == this && !(Boolean) actualState.getValue(UP) && !(Boolean) actualState.getValue(NORTH) &&
                !(Boolean) actualState.getValue(SOUTH) && !(Boolean) actualState.getValue(EAST) && !(Boolean) actualState.getValue(WEST)) {
            if (!(Boolean) actualState.getValue(DOWN)) {
                actualState = actualState.getActualState(worldIn, pos.up()).withProperty(UP, false);
            } else {
                actualState = actualState.getActualState(worldIn, pos.up())
                        .withProperty(DOWN, true)
                        .withProperty(UP, false);
            }
        }

        return actualState.withProperty(BOTTOM, this.getIsBottom(worldIn, pos));
    }

    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        EnumFacing[] var4 = EnumFacing.values();
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            EnumFacing face = var4[var6];
            IBlockState blockState = worldIn.getBlockState(pos.offset(face));
            Material material = blockState.getMaterial();
            if (material == Material.LEAVES || worldIn.getBlockState(pos.up()).getBlock() == this) {
                return this.plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) &&
                        this.plant.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos));
            }
        }

        return false;
    }

    @NotNull
    protected BlockStateContainer createPlantBlockState() {
        return new BlockStateContainer(this,
                new IProperty[] { DOWN, UP, NORTH, EAST, WEST, SOUTH, this.growthStageProperty, DAYPERIOD, AGE, BOTTOM });
    }

    protected boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Material material = iblockstate.getMaterial();
        return material == Material.LEAVES;
    }

    protected boolean canPlantConnectTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return !super.canPlantConnectTo(world, pos, facing) && world.getBlockState(pos.up())
                .getBlock() == this && facing != EnumFacing.DOWN && facing != EnumFacing.UP ? this.canPlantConnectTo(world, pos.up(), facing) :
                super.canPlantConnectTo(world, pos, facing);
    }

    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (worldIn.isAreaLoaded(pos, 1)) {
            int j;
            if (this.plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) &&
                    this.plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
                j = (Integer) state.getValue(AGE);
                if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos.down(), state, true)) {
                    if (j == 3) {
                        if (this.canGrow(worldIn, pos, state, worldIn.isRemote)) {
                            this.grow(worldIn, rand, pos, state);
                        } else if (this.canGrowHorizontally(worldIn, pos, state)) {
                            this.growHorizontally(worldIn, rand, pos, state);
                        } else if (this.canGrowDiagonally(worldIn, pos, state)) {
                            this.growDiagonally(worldIn, rand, pos, state);
                        }
                    } else if (j < 3) {
                        worldIn.setBlockState(pos, state.withProperty(AGE, j + 1)
                                .withProperty(BOTTOM, this.getIsBottom(worldIn, pos)));
                    }

                    ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            } else if (!this.plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) ||
                    !this.plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
                j = (Integer) state.getValue(AGE);
                if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
                    if (j == 0) {
                        if (this.canShrink(worldIn, pos)) {
                            this.shrink(worldIn, pos);
                        } else if (this.canShrinkHorizontally(worldIn, pos)) {
                            this.shrinkHorizontally(worldIn, pos);
                        }
                    } else if (j > 0) {
                        worldIn.setBlockState(pos, state.withProperty(AGE, j - 1)
                                .withProperty(BOTTOM, this.getIsBottom(worldIn, pos)));
                    }

                    ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
                }
            }

            this.checkAndDropBlock(worldIn, pos, state);
        }
    }

    private boolean canGrowDiagonally(World worldIn, BlockPos pos, IBlockState state) {
        boolean flag = false;
        if (!(Boolean) state.getValue(BOTTOM)) {
            EnumFacing[] var5 = EnumFacing.Plane.HORIZONTAL.facings();
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                EnumFacing face = var5[var7];
                BlockPos sidePos = pos.offset(face);
                IBlockState sideState = worldIn.getBlockState(sidePos.down(2));
                Material sideMaterial = sideState.getMaterial();
                if (worldIn.isAirBlock(sidePos) && worldIn.isAirBlock(sidePos.down()) &&
                        (!sideMaterial.isSolid() || sideMaterial == Material.LEAVES) && this.canBlockStay(worldIn, sidePos.down(), state)) {
                    flag = true;
                }
            }
        }

        return flag;
    }

    private void growDiagonally(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if (!(Boolean) state.getValue(BOTTOM)) {
            EnumFacing[] var5 = EnumFacing.Plane.HORIZONTAL.facings();
            int var6 = var5.length;

            for (int var7 = 0; var7 < var6; ++var7) {
                EnumFacing face = var5[var7];
                BlockPos sidePos = pos.offset(face);
                if (rand.nextDouble() < 0.5 && worldIn.isAirBlock(sidePos) && worldIn.isAirBlock(sidePos.down())) {
                    worldIn.setBlockState(sidePos.down(), this.getDefaultState());
                    IBlockState iblockstate = state.withProperty(AGE, 0)
                            .withProperty(this.growthStageProperty, this.plant.getStageForMonth());
                    worldIn.setBlockState(pos, iblockstate);
                    iblockstate.neighborChanged(worldIn, sidePos.down(), this, pos);
                    break;
                }
            }
        }

    }

    private boolean canGrowHorizontally(World worldIn, BlockPos pos, IBlockState state) {
        boolean flag = false;
        EnumFacing[] var5 = EnumFacing.Plane.HORIZONTAL.facings();
        int var6 = var5.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            EnumFacing face = var5[var7];
            BlockPos sidePos = pos.offset(face);
            IBlockState sideState = worldIn.getBlockState(sidePos.down());
            Material sideMaterial = sideState.getMaterial();
            if (worldIn.isAirBlock(sidePos) && (!sideMaterial.isSolid() || sideMaterial == Material.LEAVES) &&
                    this.canBlockStay(worldIn, sidePos, state)) {
                flag = true;
            }
        }

        return flag;
    }

    private void growHorizontally(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        EnumFacing[] var5 = EnumFacing.Plane.HORIZONTAL.facings();
        int var6 = var5.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            EnumFacing face = var5[var7];
            BlockPos sidePos = pos.offset(face);
            if (rand.nextDouble() < 0.01 && worldIn.isAirBlock(sidePos)) {
                worldIn.setBlockState(sidePos, this.getDefaultState());
                IBlockState iblockstate = state.withProperty(AGE, 0)
                        .withProperty(this.growthStageProperty, this.plant.getStageForMonth());
                worldIn.setBlockState(pos, iblockstate);
                iblockstate.neighborChanged(worldIn, sidePos, this, pos);
                break;
            }
        }

    }

    private void shrinkHorizontally(World worldIn, BlockPos pos) {
        worldIn.setBlockToAir(pos);
        IBlockState state = worldIn.getBlockState(pos);
        state.neighborChanged(worldIn, pos.east(), this, pos);
        state.neighborChanged(worldIn, pos.west(), this, pos);
        state.neighborChanged(worldIn, pos.north(), this, pos);
        state.neighborChanged(worldIn, pos.south(), this, pos);
    }

    private boolean canShrink(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos.up()).getBlock() == this && worldIn.getBlockState(pos.down())
                .getBlock() != this;
    }

    private boolean canShrinkHorizontally(World worldIn, BlockPos pos) {
        boolean flag = false;
        EnumFacing[] var4 = EnumFacing.Plane.HORIZONTAL.facings();
        int var5 = var4.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            EnumFacing face = var4[var6];
            if (worldIn.getBlockState(pos.offset(face)).getBlock() == this) {
                flag = true;
            }
        }

        return flag;
    }

    private boolean getIsBottom(IBlockAccess world, BlockPos pos) {
        IBlockState iblockstate = world.getBlockState(pos.down());
        Material material = iblockstate.getMaterial();
        return world.getBlockState(pos.down()).getBlock() != this && !material.isSolid();
    }
}
