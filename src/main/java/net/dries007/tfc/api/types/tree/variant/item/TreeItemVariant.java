package net.dries007.tfc.api.types.tree.variant.item;

import net.dries007.tfc.api.types.tree.ITreeItem;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.api.util.Pair;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.common.objects.items.TFCItems.TREE_ITEMS;

/**
 * Класс WoodItemVariant представляет вариант деревянного блока.
 */
public class TreeItemVariant {

    private static final Set<TreeItemVariant> TREE_ITEM_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<TreeItemVariant, TreeType, ITreeItem> factory;

    /**
     * Создает новый вариант деревянного блока с заданным именем и фабрикой.
     *
     * @param name    имя варианта деревянного блока
     * @param factory фабрика, создающая деревянный блок
     * @throws RuntimeException если имя варианта пустое или вариант с таким именем уже существует
     */
    public TreeItemVariant(@Nonnull String name, @Nonnull BiFunction<TreeItemVariant, TreeType, ITreeItem> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("TreeItemVariant name must contain any character: [%s]", name));
        }

        if (!TREE_ITEM_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("TreeItemVariant: [%s] already exists!", name));
        }

        for (var type : TreeType.getTreeTypes()) {
            var woodItem = this.create(type);

            if (TREE_ITEMS.put(new Pair<>(this, type), woodItem) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
    }

    /**
     * Возвращает множество всех созданных вариантов деревянных блоков.
     *
     * @return множество вариантов деревянных блоков
     */
    public static Set<TreeItemVariant> getTreeItemVariants() {
        return TREE_ITEM_VARIANTS;
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
     * @param type тип дерева
     * @return объект IWoodBlock, созданный фабрикой
     */
    @Nonnull
    public ITreeItem create(@Nonnull TreeType type) {
        return factory.apply(this, type);
    }
}
