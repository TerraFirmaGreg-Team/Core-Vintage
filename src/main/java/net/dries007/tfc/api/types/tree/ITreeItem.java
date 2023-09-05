package net.dries007.tfc.api.types.tree;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.api.types.tree.variant.item.TreeItemVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Интерфейс IWoodItem представляет деревянный предмет.
 */
public interface ITreeItem extends IHasModel {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
     */
    @Nonnull
    TreeItemVariant getItemVariant();

    /**
     * Возвращает тип дерева предмета.
     *
     * @return тип дерева
     */
    @Nonnull
    TreeType getType();

    /**
     * Возвращает расположение в реестре для данного подтипа деревянного предмета.
     *
     * @return расположение в реестре
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.identifier(String.format("tree/%s/%s", getItemVariant(), getType()));
    }

    /**
     * Возвращает расположение ресурса для данного подтипа деревянного предмета.
     *
     * @return расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.identifier(String.format("tree/%s", getItemVariant()));
    }

    /**
     * Возвращает локализованное имя для данного подтипа деревянного предмета.
     *
     * @return локализованное имя
     */
    @Nonnull
    default String getTranslationName() {
        return getRegistryLocation().toString().toLowerCase().replace(":", ".").replace("/", ".");
    }
}
