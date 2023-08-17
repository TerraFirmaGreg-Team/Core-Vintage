package net.dries007.tfc.api.types.crop;

import net.dries007.tfc.api.types.crop.type.CropType;
import net.dries007.tfc.api.types.crop.variant.CropBlockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public interface ICropBlock extends IHasModel, IItemProvider {

    /**
     * Возвращает вариант блока культуры.
     *
     * @return Вариант блока культуры.
     */
    @Nonnull
    CropBlockVariant getBlockVariant();

    /**
     * Возвращает тип культуры.
     *
     * @return Тип культуры.
     */
    @Nonnull
    CropType getType();

    /**
     * Возвращает местоположение регистрации блока культуры.
     *
     * @return Местоположение регистрации блока культуры.
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return new ResourceLocation(MOD_ID, String.format("crop/%s/%s", getBlockVariant(), getType()));
    }

    /**
     * Возвращает местоположение ресурса блока культуры.
     *
     * @return Местоположение ресурса блока культуры.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return new ResourceLocation(MOD_ID, String.format("crop/%s", getBlockVariant()));
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
