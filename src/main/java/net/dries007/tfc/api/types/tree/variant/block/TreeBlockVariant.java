package net.dries007.tfc.api.types.tree.variant.block;

import net.dries007.tfc.api.types.tree.ITreeBlock;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.api.util.Pair;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.common.objects.blocks.TFCBlocks.TREE_BLOCKS;

/**
 * Класс WoodItemVariant представляет вариант деревянного блока.
 */
public class TreeBlockVariant {

    private static final Set<TreeBlockVariant> TREE_BLOCK_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<TreeBlockVariant, TreeType, ITreeBlock> factory;

    /**
     * Создает новый вариант деревянного блока с заданным именем и фабрикой.
     *
     * @param name    имя варианта деревянного блока
     * @param factory фабрика, создающая деревянный блок
     * @throws RuntimeException если имя варианта пустое или вариант с таким именем уже существует
     */
    public TreeBlockVariant(@Nonnull String name, @Nonnull BiFunction<TreeBlockVariant, TreeType, ITreeBlock> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("WoodItemVariant name must contain any character: [%s]", name));
        }

        if (!TREE_BLOCK_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("WoodItemVariant: [%s] already exists!", name));
        }

        for (var type : TreeType.getTreeTypes()) {
            var treeBlock = this.create(type);

            if (TREE_BLOCKS.put(new Pair<>(this, type), treeBlock) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
    }

    /**
     * Возвращает множество всех созданных вариантов деревянных блоков.
     *
     * @return множество вариантов деревянных блоков
     */
    public static Set<TreeBlockVariant> getTreeBlockVariants() {
        return TREE_BLOCK_VARIANTS;
    }

    /**
     * Возвращает строковое представление варианта деревянного блока (его имя).
     *
     * @return имя варианта деревянного блока
     */
    @Nonnull
    @Override
    public String toString() {
        return name;
    }

    /**
     * Применяет вариант деревянного блока к фабрике, чтобы получить соответствующий деревянный блок.
     *
     * @param treeType тип дерева
     * @return объект IWoodBlock, созданный фабрикой
     */
    @Nonnull
    public ITreeBlock create(@Nonnull TreeType treeType) {
        return factory.apply(this, treeType);
    }
}
