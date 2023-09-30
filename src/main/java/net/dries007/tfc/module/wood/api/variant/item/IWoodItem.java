package net.dries007.tfc.module.wood.api.variant.item;

import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.wood.api.type.WoodType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Интерфейс ICropItem представляет деревянный предмет.
 */
public interface IWoodItem extends IHasModel {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
     */
    @Nonnull
    WoodItemVariant getItemVariant();

    /**
     * Возвращает тип дерева предмета.
     *
     * @return тип дерева
     */
    @Nonnull
    WoodType getType();

    /**
     * Возвращает расположение в реестре для данного подтипа предмета.
     *
     * @return Расположение в реестре
     */
    @Nonnull
    default String getName() {
        return String.format("wood.%s.%s", getItemVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного подтипа предмета.
     *
     * @return Расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return Helpers.getID(String.format("wood/%s", getItemVariant()));
    }
}
