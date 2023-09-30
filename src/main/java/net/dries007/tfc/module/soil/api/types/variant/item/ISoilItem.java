package net.dries007.tfc.module.soil.api.types.variant.item;

import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.soil.api.types.type.SoilType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Интерфейс ICropItem представляет деревянный предмет.
 */
public interface ISoilItem extends IHasModel {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
     */
    @Nonnull
    SoilItemVariant getItemVariant();

    /**
     * Возвращает тип дерева предмета.
     *
     * @return тип дерева
     */
    @Nonnull
    SoilType getType();

    /**
     * Возвращает расположение в реестре для данного подтипа деревянного предмета.
     *
     * @return расположение в реестре
     */
    @Nonnull
    default String getName() {
        return String.format("soil.%s.%s", getItemVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного подтипа деревянного предмета.
     *
     * @return расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return Helpers.getID(String.format("soil/%s/%s", getItemVariant(), getType()));
    }
}
