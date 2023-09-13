package net.dries007.tfc.api.types.soil.variant.item;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.util.IHasModel;
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
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.identifier(String.format("soil/%s/%s", getItemVariant(), getType()));
    }

    /**
     * Возвращает расположение ресурса для данного подтипа деревянного предмета.
     *
     * @return расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.identifier(String.format("soil/%s", getItemVariant()));
    }

    /**
     * Возвращает локализованное имя для данного подтипа деревянного предмета.
     *
     * @return локализованное имя
     */
    @Nonnull
    default String getTranslationName() {
        return getRegistryLocation().toString().toLowerCase().replace(":", ".").replace("/", ".");
    }
}
