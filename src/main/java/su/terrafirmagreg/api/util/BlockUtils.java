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

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.api.data.Blockstates.CLAY;
import static su.terrafirmagreg.modules.rock.init.BlocksRock.*;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.*;

@UtilityClass
@SuppressWarnings("unused")
public final class BlockUtils {

    /**
     * Уведомляет мир о том, что блок был обновлен.
     *
     * @param world мир, в котором находится блок
     * @param pos   позиция блока
     */
    public static void notifyBlockUpdate(World world, BlockPos pos) {

        IBlockState blockState = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, blockState, blockState, 3);
    }

    /**
     * Проверяет, окружен ли блок только воздухом по горизонтали.
     *
     * @param world мир, в котором находится блок
     * @param pos   позиция блока
     * @return true, если блок окружен только воздухом по горизонтали, иначе false
     */
    public static boolean isBlockSurroundedByAirHorizontal(World world, BlockPos pos) {

        return world.isAirBlock(pos.offset(EnumFacing.NORTH))
                && world.isAirBlock(pos.offset(EnumFacing.SOUTH))
                && world.isAirBlock(pos.offset(EnumFacing.EAST))
                && world.isAirBlock(pos.offset(EnumFacing.WEST));
    }

    public static boolean isBlockSurroundedByAir(World world, BlockPos blockPos) {
        return (world.isAirBlock(blockPos.up()) ||
                world.isAirBlock(blockPos.down()) ||
                world.isAirBlock(blockPos.north()) ||
                world.isAirBlock(blockPos.south()) ||
                world.isAirBlock(blockPos.east()) ||
                world.isAirBlock(blockPos.west()));
    }

    /**
     * Выполняет действие для каждого блока в заданном радиусе вокруг заданной позиции.
     *
     * @param world  мир, в котором выполняется действие
     * @param pos    начальная позиция
     * @param range  радиус действия
     * @param action действие, которое выполняется для каждого блока
     */
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

    /**
     * Выполняет действие для каждого блока в заданном радиусе вокруг заданной позиции в случайном порядке.
     *
     * @param world  мир, в котором выполняется действие
     * @param pos    начальная позиция
     * @param range  радиус действия
     * @param action действие, которое выполняется для каждого блока
     */
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

    /**
     * Выполняет действие для каждого блока в заданном кубе вокруг заданной позиции.
     *
     * @param world  мир, в котором выполняется действие
     * @param pos    начальная позиция
     * @param rangeX радиус действия по оси X
     * @param rangeY радиус действия по оси Y
     * @param rangeZ радиус действия по оси Z
     * @param action действие, которое выполняется для каждого блока
     */
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

    /**
     * Выполняет действие для каждого блока в заданном кубе вокруг заданной позиции в случайном порядке.
     *
     * @param world  мир, в котором выполняется действие
     * @param pos    начальная позиция
     * @param rangeX радиус действия по оси X
     * @param rangeY радиус действия по оси Y
     * @param rangeZ радиус действия по оси Z
     * @param action действие, которое выполняется для каждого блока
     */
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

    /**
     * Находит все блоки в заданном кубе и добавляет их в заданный список.
     *
     * @param world  мир, в котором выполняется поиск блоков
     * @param pos    начальная позиция
     * @param rangeX радиус действия по оси X
     * @param rangeY радиус действия по оси Y
     * @param rangeZ радиус действия по оси Z
     * @param filter фильтр, который определяет, какие блоки добавляются в список
     * @param result список, в который добавляются найденные блоки
     * @return список найденных блоков
     */
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

    /**
     * Находит все блоки в заданном радиусе вокруг заданной позиции и добавляет их в заданный список.
     *
     * @param world  мир, в котором выполняется поиск блоков
     * @param pos    начальная позиция
     * @param range  радиус действия
     * @param filter фильтр, который определяет, какие блоки добавляются в список
     * @param result список, в который добавляются найденные блоки
     * @return список найденных блоков
     */
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
     * Проверяет, содержит ли ItemStack элемент руды. Это делается путем проверки элемента, расширяющего BlockOre, или наличия в словаре руд записей, начинающихся с «ore». Это
     * также будет проверять отображаемое имя стака, чтобы увидеть, есть ли в ней слово Ore.
     *
     * @param stack     Стек ItemStack для проверки.
     * @param checkName Следует ли проверять имя ItemStack.
     * @return Является ли ItemStack рудой.
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
     * Проверяет, является ли блок жидкостью или нет.
     *
     * @param block Экземпляр проверяемого блока.
     * @return Если блок представляет собой жидкость, будет возвращено значение true. Если нет, будет возвращено false.
     */
    public static boolean isFluid(Block block) {

        return block == Blocks.LAVA || block == Blocks.WATER || block instanceof IFluidBlock;
    }

    /**
     * Проверяет, полон ли уровень жидкости.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Независимо от того, заполнена ли жидкость.
     */
    public static boolean isFluidFull(World world, BlockPos pos) {

        return isFluidFull(getActualState(world, pos));
    }

    /**
     * Проверяет, полон ли уровень жидкости.
     *
     * @param state Состояние блокировки.
     * @return Независимо от того, заполнена ли жидкость.
     */
    public static boolean isFluidFull(IBlockState state) {

        return getFluidLevel(state) == 0;
    }

    /**
     * Проверьте уровень жидкости.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Уровень жидкости. 0 заполнен.
     */
    public static int getFluidLevel(World world, BlockPos pos) {

        return getFluidLevel(getActualState(world, pos));
    }

    /**
     * Проверьте уровень жидкости.
     *
     * @param state Состояние блокировки.
     * @return Уровень жидкости. 0 заполнен.
     */
    public static int getFluidLevel(IBlockState state) {

        return state.getValue(BlockLiquid.LEVEL);
    }

    /**
     * Попытки получить стек жидкости из поз. блока.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Жидкий стек.
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
     * Метод подвешивания блоков, чтобы проверить, могут ли они висеть. Описание 11/10. ПРИМЕЧАНИЕ. Там, где это применимо, не забудьте проверить, допускает ли состояние блока
     * указанное направление!
     *
     * @param worldIn мир
     * @param pos     позиция блока, который выполняет проверку
     * @param facing  в том направлении, в котором смотрит блок. Это направление, в котором должен быть указан блок, и сторона, НА которой он висит, а не сторона, С которой он
     *                прилипает. Например: знак, обращенный к северу также висит на северной стороне опорного блока
     * @return true, если сторона сплошная, в противном случае — false.
     */
    public static boolean canHangAt(World worldIn, BlockPos pos, EnumFacing facing) {

        return worldIn.isSideSolid(pos.offset(facing.getOpposite()), facing);
    }

    /**
     * В первую очередь для использования при размещении чеков. Определяет твердую сторону, к которой прикрепляется блок.
     *
     * @param worldIn         мир
     * @param pos             позиция проверяемого блока/пространства.
     * @param preferredFacing это выравнивание проверяется в первую очередь. Оно может быть недействительным или нулевым.
     * @param possibleSides   — список/массив всех сторон, к которым может прикрепиться блок.
     * @return Обнаружено столкновение или значение null означает, что ничего не найдено. Это направление, в котором должен быть указан блок, и сторона, К которой он прилипает, а
     * не сторона, С которой он прилипает.
     */
    public static EnumFacing getASolidFacing(World worldIn, BlockPos pos, @Nullable EnumFacing preferredFacing, EnumFacing... possibleSides) {

        return getASolidFacing(worldIn, pos, preferredFacing, Arrays.asList(possibleSides));
    }

    public static EnumFacing getASolidFacing(World worldIn, BlockPos pos, @Nullable EnumFacing preferredFacing, Collection<EnumFacing> possibleSides) {
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
     * Получает фактическое состояние блока.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Фактическое состояние блока.
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
