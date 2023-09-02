package net.dries007.tfc.api.types.tree;

import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodSapling;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

/**
 * Интерфейс ITreeGenerator представляет генератор деревьев.
 */
public interface ITreeGenerator {

    /**
     * Вызывается для генерации дерева. Каждое дерево должно иметь этот метод.
     * Используется для генерации деревьев в мире и роста саженцев.
     *
     * @param manager    экземпляр менеджера шаблонов мира. Используется для получения структур.
     * @param world      мир
     * @param pos        позиция, где находился или должен был находиться саженец
     * @param woodType   тип дерева для генерации
     * @param rand       случайное число для использования в генерации
     * @param isWorldGen флаг, указывающий, является ли генерация частью генерации мира
     */
    void generateTree(TemplateManager manager, World world, BlockPos pos, WoodType woodType, Random rand, boolean isWorldGen);

    /**
     * Проверяет, может ли быть сгенерировано дерево. Эта реализация проверяет высоту, радиус и уровень освещения.
     *
     * @param world    мир
     * @param pos      позиция дерева
     * @param woodType тип дерева (для проверки возможности генерации)
     * @return true, если дерево может быть сгенерировано
     */
    default boolean canGenerateTree(World world, BlockPos pos, WoodType woodType) {
        // Проверяем, достаточно ли ровный грунт
        final int radius = woodType.getMaxGrowthRadius();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if ((x == 0 && z == 0) ||
                        world.getBlockState(pos.add(x, 0, z)).getMaterial().isReplaceable() ||
                        ((x > 1 || z > 1) && world.getBlockState(pos.add(x, 1, z)).getMaterial().isReplaceable()))
                    continue;
                return false;
            }
        }
        // Проверяем, есть ли пространство прямо вверх
        final int height = woodType.getMaxHeight();
        for (int y = 1; y <= height; y++) {
            var state = world.getBlockState(pos.up(y));
            if (!state.getMaterial().isReplaceable() && state.getMaterial() != Material.LEAVES)
                return false;
        }

        // Проверяем, есть ли твердый блок снизу
        if (!TFCBlocks.isGrowableSoil(world.getBlockState(pos.down())))
            return false;


        // Проверьте, достаточен ли уровень освещенности
        // world.getLightFromNeighbors(pos) >= 7;

        // Проверяем позицию на наличие жидкостей и т.д.
        var stateAt = world.getBlockState(pos);
        return !stateAt.getMaterial().isLiquid() && (stateAt.getMaterial().isReplaceable() || stateAt.getBlock() instanceof BlockWoodSapling);
    }
}
