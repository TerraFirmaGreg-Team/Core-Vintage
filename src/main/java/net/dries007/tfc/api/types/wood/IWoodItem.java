package net.dries007.tfc.api.types.wood;

import net.dries007.tfc.api.types.wood.type.WoodType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

/**
 * Интерфейс IWoodItem представляет деревянный предмет.
 */
public interface IWoodItem {

    /**
     * Возвращает тип дерева предмета.
     *
     * @return тип дерева
     */
    @Nonnull
    WoodType getWoodType();

    /**
     * Возвращает расположение в реестре для данного подтипа деревянного предмета.
     *
     * @param subType подтип деревянного предмета
     * @return расположение в реестре
     */
    @Nonnull
    default ResourceLocation getRegistryLocation(String subType) {
        return new ResourceLocation(MOD_ID, String.format("wood/%s/%s", subType, getWoodType()));
    }

    /**
     * Возвращает расположение ресурса для данного подтипа деревянного предмета.
     *
     * @param subType подтип деревянного предмета
     * @return расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation(String subType) {
        return new ResourceLocation(MOD_ID, String.format("wood/%s", subType));
    }

    /**
     * Возвращает локализованное имя для данного подтипа деревянного предмета.
     *
     * @param subType подтип деревянного предмета
     * @return локализованное имя
     */
    @Nonnull
    default String getTranslationName(String subType) {
        return getRegistryLocation(subType).toString().toLowerCase().replace(":", ".").replace("/", ".");
    }
}
