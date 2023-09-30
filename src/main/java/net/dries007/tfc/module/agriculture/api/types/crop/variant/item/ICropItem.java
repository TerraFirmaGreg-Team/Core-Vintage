package net.dries007.tfc.module.agriculture.api.types.crop.variant.item;

import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.agriculture.api.types.crop.type.CropType;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Интерфейс ICropItem представляет предмет урожая.
 */
public interface ICropItem extends IHasModel {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
     */
    @Nonnull
    CropItemVariant getItemVariant();

    /**
     * Возвращает тип предмета.
     *
     * @return тип
     */
    @Nonnull
    CropType getType();

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @Nonnull
    default String getName() {
        return String.format("crop.%s.%s", getItemVariant(), getType());
    }

    /**
     * Возвращает расположение в реестре для данного подтипа предмета.
     *
     * @return Расположение в реестре
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return Helpers.getID(String.format("crop.%s.%s", getItemVariant(), getType()));
    }

    /**
     * Возвращает расположение ресурса для данного подтипа предмета.
     *
     * @return Расположение ресурса.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return Helpers.getID(String.format("crop/%s/%s", getItemVariant(), getType()));
    }
}
