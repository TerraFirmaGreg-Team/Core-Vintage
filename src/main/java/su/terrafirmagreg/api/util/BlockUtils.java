package su.terrafirmagreg.api.util;

import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.soil.api.spi.IGrass;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.objects.blocks.BlockSoilPeat;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.oredict.OreDictionary;


import net.dries007.tfc.objects.fluids.FluidsTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.api.data.Blockstates.CLAY;
import static su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariants.*;
import static su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariants.*;

@SuppressWarnings("unused")
public final class BlockUtils {

    private BlockUtils() {
        throw new IllegalAccessError("Utility class");
    }

    public static void notifyBlockUpdate(World world, BlockPos pos) {

        IBlockState blockState = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, blockState, blockState, 3);
    }

    public static boolean isBlockSurroundedByAirHorizontal(World world, BlockPos pos) {

        return world.isAirBlock(pos.offset(EnumFacing.NORTH))
                && world.isAirBlock(pos.offset(EnumFacing.SOUTH))
                && world.isAirBlock(pos.offset(EnumFacing.EAST))
                && world.isAirBlock(pos.offset(EnumFacing.WEST));
    }

    public static void forBlocksInRange(World world, BlockPos pos, int range, IBlockAction action) {

        int rangeSq = range * range;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        complete:
        for (int x = pos.getX() - range; x <= pos.getX() + range; x++) {
            for (int y = pos.getY() - range; y <= pos.getY() + range; y++) {
                for (int z = pos.getZ() - range; z <= pos.getZ() + range; z++) {
                    double distanceSq = pos.distanceSq(x, y, z);

                    if (distanceSq <= rangeSq) {
                        mutableBlockPos.setPos(x, y, z);
                        IBlockState blockState = world.getBlockState(mutableBlockPos);

                        if (!action.execute(world, mutableBlockPos, blockState)) {
                            break complete;
                        }
                    }
                }
            }
        }
    }

    public static void forBlocksInRangeShuffled(World world, BlockPos pos, int range, IBlockAction action) {

        ArrayList<BlockPos> blockList = new ArrayList<>();
        BlockUtils.findBlocksInCube(world, pos, range, range, range, IBlockFilter.TRUE, blockList);
        Collections.shuffle(blockList);
        int rangeSq = range * range;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

        for (BlockPos blockPos : blockList) {
            double distanceSq = pos.distanceSq(blockPos.getX(), blockPos.getY(), blockPos.getZ());

            if (distanceSq <= rangeSq) {
                mutableBlockPos.setPos(blockPos);
                IBlockState blockState = world.getBlockState(mutableBlockPos);

                if (!action.execute(world, mutableBlockPos, blockState)) {
                    break;
                }
            }
        }
    }

    public static void forBlocksInCube(World world, BlockPos pos, int rangeX, int rangeY, int rangeZ, IBlockAction action) {

        complete:
        for (int x = pos.getX() - rangeX; x <= pos.getX() + rangeX; x++) {
            for (int y = pos.getY() - rangeY; y <= pos.getY() + rangeY; y++) {
                for (int z = pos.getZ() - rangeZ; z <= pos.getZ() + rangeZ; z++) {

                    BlockPos candidatePos = new BlockPos(x, y, z);
                    IBlockState blockState = world.getBlockState(candidatePos);

                    if (!action.execute(world, candidatePos, blockState)) {
                        break complete;
                    }
                }
            }
        }
    }

    public static void forBlocksInCubeShuffled(World world, BlockPos pos, int rangeX, int rangeY, int rangeZ, IBlockAction action) {

        ArrayList<BlockPos> blockList = new ArrayList<>();
        BlockUtils.findBlocksInCube(world, pos, rangeX, rangeY, rangeZ, IBlockFilter.TRUE, blockList);
        Collections.shuffle(blockList);

        for (BlockPos blockPos : blockList) {
            IBlockState blockState = world.getBlockState(blockPos);

            if (!action.execute(world, blockPos, blockState)) {
                break;
            }
        }
    }

    public static List<BlockPos> findBlocksInCube(World world, BlockPos pos, int rangeX, int rangeY, int rangeZ, IBlockFilter filter, List<BlockPos> result) {

        for (int x = pos.getX() - rangeX; x <= pos.getX() + rangeX; x++) {
            for (int y = pos.getY() - rangeY; y <= pos.getY() + rangeY; y++) {
                for (int z = pos.getZ() - rangeZ; z <= pos.getZ() + rangeZ; z++) {

                    BlockPos candidatePos = new BlockPos(x, y, z);
                    IBlockState blockState = world.getBlockState(candidatePos);

                    if (filter.allow(world, candidatePos, blockState)) {
                        result.add(candidatePos);
                    }
                }
            }
        }

        return result;
    }

    public static List<BlockPos> findBlocksInRange(World world, BlockPos pos, int range, IBlockFilter filter, List<BlockPos> result) {

        int rangeSq = range * range;

        for (int x = pos.getX() - range; x <= pos.getX() + range; x++) {
            for (int y = pos.getY() - range; y <= pos.getY() + range; y++) {
                for (int z = pos.getZ() - range; z <= pos.getZ() + range; z++) {
                    double distanceSq = pos.distanceSq(x, y, z);

                    if (distanceSq <= rangeSq) {
                        BlockPos candidatePos = new BlockPos(x, y, z);
                        IBlockState blockState = world.getBlockState(candidatePos);

                        if (filter.allow(world, candidatePos, blockState)) {
                            result.add(candidatePos);
                        }
                    }
                }
            }
        }

        return result;
    }

    public static void setFireInfo(Block blockIn, int encouragement, int flammability) {
        Blocks.FIRE.setFireInfo(blockIn, encouragement, flammability);
    }

    /**
     * Checks if an ItemStack contains an ore item. This is done by checking the item extends BlockOre, or if the ore dictionary for entries that start with 'ore'. It will also
     * check the display name of the stack to see if it has the word Ore in it.
     *
     * @param stack     The ItemStack to check.
     * @param checkName Whether or not the name of the ItemStack should be checked.
     * @return Whether or not the ItemStack is an ore.
     */
    public static boolean isOre(@NotNull ItemStack stack, boolean checkName) {

        if (Block.getBlockFromItem(stack.getItem()) instanceof BlockOre) {
            return true;
        }

        for (final int oreID : OreDictionary.getOreIDs(stack)) {
            if (OreDictionary.getOreName(oreID).startsWith("ore")) {
                return true;
            }
        }

        return checkName && stack.getItem().getItemStackDisplayName(stack).matches(".*(^|\\s)([oO]re)($|\\s).");
    }

    /**
     * Checks if a block is a fluid or not.
     *
     * @param block An instance of the block being checked.
     * @return If the block is a fluid, true will be returned. If not, false will be returned.
     */
    public static boolean isFluid(Block block) {

        return block == Blocks.LAVA || block == Blocks.WATER || block instanceof IFluidBlock;
    }

    /**
     * Checks if the fluid level is full.
     *
     * @param world The world.
     * @param pos   The position.
     * @return Whether or not the fluid is full.
     */
    public static boolean isFluidFull(World world, BlockPos pos) {

        return isFluidFull(getActualState(world, pos));
    }

    /**
     * Checks if the fluid level is full.
     *
     * @param state The block state.
     * @return Whether or not the fluid is full.
     */
    public static boolean isFluidFull(IBlockState state) {

        return getFluidLevel(state) == 0;
    }

    /**
     * Get the fluid level.
     *
     * @param world The world.
     * @param pos   The position.
     * @return The level of the fluid. 0 is full.
     */
    public static int getFluidLevel(World world, BlockPos pos) {

        return getFluidLevel(getActualState(world, pos));
    }

    /**
     * Get the fluid level.
     *
     * @param state The block state.
     * @return The level of the fluid. 0 is full.
     */
    public static int getFluidLevel(IBlockState state) {

        return state.getValue(BlockLiquid.LEVEL);
    }

    /**
     * Attempts to get a fluid stack from a block pos.
     *
     * @param world The world.
     * @param pos   The position.
     * @return The fluid stack.
     */
    public static FluidStack getFluid(World world, BlockPos pos) {

        final IBlockState state = getActualState(world, pos);
        final Block block = state.getBlock();

        if (block instanceof IFluidBlock fluidBlock && fluidBlock.canDrain(world, pos)) {

            return fluidBlock.drain(world, pos, true);
        } else if (block instanceof BlockStaticLiquid && isFluidFull(state)) {

            final Fluid fluid = block == Blocks.WATER ? FluidRegistry.WATER : block == Blocks.LAVA ? FluidRegistry.LAVA : null;

            if (fluid != null) {

                return new FluidStack(fluid, 1000);
            }
        }

        return null;
    }

    /**
     * Method for hanging blocks to check if they can hang. 11/10 description. NOTE: where applicable, remember to still check if the blockstate allows for the specified
     * direction!
     *
     * @param pos    position of the block that makes the check
     * @param facing the direction the block is facing. This is the direction the block should be pointing and the side it hangs ON, not the side it sticks WITH. e.g: a sign facing
     *               north also hangs on the north side of the support block
     * @return true if the side is solid, false otherwise.
     */
    public static boolean canHangAt(World worldIn, BlockPos pos, EnumFacing facing) {
        return worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing);
    }

    /**
     * Primarily for use in placing checks. Determines a solid side for the block to attach to.
     *
     * @param pos             position of the block/space to be checked.
     * @param possibleSides   a list/array of all sides the block can attach to.
     * @param preferredFacing this facing is checked first. It can be invalid or null.
     * @return Found facing or null is none is found. This is the direction the block should be pointing and the side it stick TO, not the side it sticks WITH.
     */
    public static EnumFacing getASolidFacing(World worldIn, BlockPos pos, @Nullable EnumFacing preferredFacing, EnumFacing... possibleSides) {
        return getASolidFacing(worldIn, pos, preferredFacing, Arrays.asList(possibleSides));
    }

    public static EnumFacing getASolidFacing(World worldIn, BlockPos pos, @Nullable EnumFacing preferredFacing,
                                             Collection<EnumFacing> possibleSides) {
        if (preferredFacing != null && possibleSides.contains(preferredFacing) && canHangAt(worldIn, pos, preferredFacing)) {
            return preferredFacing;
        }
        for (EnumFacing side : possibleSides) {
            if (side != null && canHangAt(worldIn, pos, side)) {
                return side;
            }
        }
        return null;
    }

    /**
     * Gets the actual state of the block.
     *
     * @param world The world.
     * @param pos   The position.
     * @return The actual block state.
     */
    public static IBlockState getActualState(IBlockAccess world, BlockPos pos) {

        return world.getBlockState(pos).getActualState(world, pos);
    }

    /**
     * Проверяет наличие определенного свойства у блока.
     *
     * @param blockState Состояние блока для проверки.
     * @param property   Свойство для проверки.
     * @return true, если свойство присутствует у блока, иначе false.
     */
    public static boolean hasProperty(IBlockState blockState, IProperty<?> property) {
        return blockState.getPropertyKeys().contains(property);
    }

    public static boolean isSoilBlockType(ISoilBlock soilBlock, SoilBlockVariant... variants) {
        var blockVariant = soilBlock.getVariant();
        for (var variant : variants) {
            if (blockVariant == variant) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRockBlockType(IRockBlock rockBlock, RockBlockVariant... variants) {
        var blockVariant = rockBlock.getVariant();
        for (var variant : variants) {
            if (blockVariant == variant) {
                return true;
            }
        }
        return false;
    }

    public static boolean isGrass(IBlockState current) {
        return current.getBlock() instanceof IGrass;
    }

    public static boolean isDirt(IBlockState current) {
        var block = current.getBlock();
        if (block instanceof ISoilBlock soil) {
            return isSoilBlockType(soil, DIRT, COARSE_DIRT, ROOTED_DIRT);
        }
        return false;
    }

    public static boolean isSoil(IBlockState current) {
        var block = current.getBlock();
        if (block instanceof BlockSoilPeat) return true;
        if (block instanceof ISoilBlock soil) {
            return isSoilBlockType(soil,
                    GRASS, DRY_GRASS, PODZOL,
                    MYCELIUM, DIRT, COARSE_DIRT,
                    SPARSE_GRASS, ROOTED_DIRT);
        }
        return false;
    }

    public static boolean isDryGrass(IBlockState current) {
        var block = current.getBlock();
        if (block instanceof ISoilBlock soil) {
            return isSoilBlockType(soil, DRY_GRASS);
        }
        return false;
    }

    public static boolean isSparseGrass(IBlockState current) {
        var block = current.getBlock();
        if (block instanceof ISoilBlock soil) {
            return isSoilBlockType(soil, SPARSE_GRASS);
        }
        return false;
    }

    public static boolean isClay(IBlockState current) {
        return hasProperty(current, CLAY);
    }

    public static boolean isGround(IBlockState current) {
        var block = current.getBlock();
        if (block instanceof IRockBlock rock) {
            return isRockBlockType(rock, GRAVEL, SAND, RAW);
        }
        if (block instanceof ISoilBlock soil) {
            return isSoilBlockType(soil,
                    GRASS, DRY_GRASS, COARSE_DIRT,
                    SPARSE_GRASS, ROOTED_DIRT, DIRT,
                    MUD, PODZOL, MYCELIUM);
        }
        return false;
    }

    public static boolean isGrowableSoil(IBlockState current) {
        var block = current.getBlock();
        if (block instanceof ISoilBlock soil) {
            return isSoilBlockType(soil,
                    GRASS, DRY_GRASS, SPARSE_GRASS,
                    DIRT, PODZOL, MYCELIUM);
        }
        return false;
    }

    public static boolean isSoilOrGravel(IBlockState current) {
        var block = current.getBlock();
        if (block instanceof IRockBlock rock) {
            return isRockBlockType(rock, GRAVEL);
        }
        if (block instanceof ISoilBlock soil) {
            return isSoilBlockType(soil,
                    GRASS, DRY_GRASS, COARSE_DIRT,
                    SPARSE_GRASS, ROOTED_DIRT, DIRT,
                    MUD, PODZOL, MYCELIUM);
        }
        return false;
    }

    public static boolean isRawStone(IBlockState current) {
        var block = current.getBlock();
        if (block instanceof IRockBlock rock) {
            return isRockBlockType(rock, RAW, MOSSY_RAW);
        }
        return false;
    }

    public static boolean isSand(IBlockState current) {
        var block = current.getBlock();
        if (block instanceof IRockBlock rock) {
            return isRockBlockType(rock, SAND);
        }
        return false;
    }

    public static boolean isPodzol(IBlockState current) {
        var block = current.getBlock();
        if (block instanceof ISoilBlock soil) {
            return isSoilBlockType(soil, PODZOL);
        }
        return false;
    }

    public static boolean isWater(IBlockState current) {
        return current.getMaterial() == Material.WATER;
    }

    public static boolean isFreshWater(IBlockState current) {
        return current == FluidsTFC.FRESH_WATER.get().getBlock().getDefaultState();
    }

    public static boolean isSaltWater(IBlockState current) {
        return current == FluidsTFC.SALT_WATER.get().getBlock().getDefaultState();
    }

    public static boolean isFreshWaterOrIce(IBlockState current) {
        return current.getBlock() == Blocks.ICE || BlockUtils.isFreshWater(current);
    }

    public interface IBlockAction {

        /**
         * Return false to stop processing, true to keep processing.
         */
        boolean execute(World w, BlockPos p, IBlockState bs);
    }

    public interface IBlockFilter {

        IBlockFilter TRUE = (world, pos, blockState) -> true;

        boolean allow(World world, BlockPos pos, IBlockState blockState);
    }

}
