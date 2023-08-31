package net.dries007.tfc.api.types.wood;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.item.WoodItemVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Интерфейс IWoodItem представляет деревянный предмет.
 */
public interface IWoodItem extends IHasModel {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
     */
    @Nonnull
    WoodItemVariant getItemVariant();

    /**
     * Возвращает тип дерева предмета.
     *
     * @return тип дерева
     */
    @Nonnull
    WoodType getType();

    /**
     * Возвращает расположение в реестре для данного подтипа деревянного предмета.
     *
     * @return расположение в реестре
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.identifier(String.format("wood/%s/%s", getItemVariant(), getType()));
    }

    /**
     * Возвращает расположение ресурса для данного подтипа деревянного предмета.
     *
     * @return расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.identifier(String.format("wood/%s", getItemVariant()));
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
