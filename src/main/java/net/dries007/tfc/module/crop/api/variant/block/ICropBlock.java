package net.dries007.tfc.module.crop.api.variant.block;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.crop.api.type.CropType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public interface ICropBlock extends IHasModel, IItemProvider {

    /**
     * Возвращает вариант блока культуры.
     *
     * @return Вариант блока культуры.
     */
    @Nonnull
    CropType getType();

    /**
     * Возвращает тип культуры.
     *
     * @return Тип культуры.
     */
    @Nonnull
    CropBlockVariant getBlockVariant();

    /**
     * Возвращает местоположение регистрации блока культуры.
     *
     * @return Местоположение регистрации блока культуры.
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.identifier(String.format("crop/%s/%s", getBlockVariant(), getType()));
    }

    /**
     * Возвращает местоположение ресурса блока культуры.
     *
     * @return Местоположение ресурса блока культуры.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.identifier(String.format("crop/%s/%s", getBlockVariant(), getType()));
    }

    /**
     * Возвращает имя перевода блока культуры.
     *
     * @return Имя перевода блока культуры.
     */
    @Nonnull
    default String getTranslationName() {
        return getRegistryLocation().toString().toLowerCase()
                .replace(":", ".")
                .replace("/", ".");
    }
}
