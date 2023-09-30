package net.dries007.tfc.module.agriculture.api.crop.variant.block;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.agriculture.api.crop.type.CropType;
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
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @Nonnull
    default String getName() {
        return String.format("crop.%s.%s", getBlockVariant(), getType());
    }

    /**
     * Возвращает расположение в реестре для данного подтипа блока.
     *
     * @return Расположение в реестре.
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.getID(String.format("crop.%s.%s", getBlockVariant(), getType()));
    }

    /**
     * Возвращает местоположение ресурса блока породы.
     *
     * @return Расположение ресурса.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.getID(String.format("crop/%s/%s", getBlockVariant(), getType()));
    }
}
