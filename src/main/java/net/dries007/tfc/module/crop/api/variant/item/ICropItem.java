package net.dries007.tfc.module.crop.api.variant.item;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.crop.api.type.CropType;
import net.dries007.tfc.api.util.IHasModel;
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
     * Возвращает расположение в реестре для данного подтипа предмета.
     *
     * @return расположение в реестре
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.identifier(String.format("crop/%s/%s", getItemVariant(), getType()));
    }

    /**
     * Возвращает расположение ресурса для данного подтипа предмета.
     *
     * @return расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.identifier(String.format("crop/%s", getItemVariant()));
    }

    /**
     * Возвращает локализованное имя для данного подтипа предмета.
     *
     * @return локализованное имя
     */
    @Nonnull
    default String getTranslationName() {
        return getRegistryLocation().toString().toLowerCase().replace(":", ".").replace("/", ".");
    }
}
