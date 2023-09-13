package net.dries007.tfc.api.types.metal.variant.Item;

import gregtech.api.unification.material.Material;
import net.dries007.tfc.api.util.IHasModel;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IMetalItem extends IHasModel {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
     */
    @Nonnull
    MetalItemVariant getItemVariant();

    /**
     * Возвращает тип породы.
     *
     * @return Тип породы.
     */
    @Nullable
    Material getMaterial();
}
