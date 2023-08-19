package net.dries007.tfc.api.types.crop;

import net.dries007.tfc.api.types.crop.type.CropType;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

/**
 * Интерфейс ICropItem представляет предмет урожая.
 */
public interface ICropItem {

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
     * @param subType подтип предмета
     * @return расположение в реестре
     */
    @Nonnull
    default ResourceLocation getRegistryLocation(String subType) {
        return new ResourceLocation(MOD_ID, String.format("crop/%s/%s", subType, getType()));
    }

    /**
     * Возвращает расположение ресурса для данного подтипа предмета.
     *
     * @param subType подтип предмета
     * @return расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation(String subType) {
        return new ResourceLocation(MOD_ID, String.format("crop/%s", subType));
    }

    /**
     * Возвращает локализованное имя для данного подтипа предмета.
     *
     * @param subType подтип предмета
     * @return локализованное имя
     */
    @Nonnull
    default String getTranslationName(String subType) {
        return getRegistryLocation(subType).toString().toLowerCase().replace(":", ".").replace("/", ".");
    }
}
