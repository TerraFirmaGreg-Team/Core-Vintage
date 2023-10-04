package net.dries007.tfc.module.plant.api.types.variant.item;

import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.plant.api.types.type.PlantType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public interface IPlantItem extends IHasModel {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
     */
    @Nonnull
    PlantItemVariant getItemVariant();

    /**
     * Возвращает тип дерева предмета.
     *
     * @return тип дерева
     */
    @Nonnull
    PlantType getType();

    /**
     * Возвращает расположение в реестре для данного подтипа предмета.
     *
     * @return Расположение в реестре
     */
    @Nonnull
    default String getName() {
        return String.format("plant.%s.%s", getItemVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного подтипа предмета.
     *
     * @return Расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return Helpers.getID(String.format("plant/%s", getType()));
    }
}
