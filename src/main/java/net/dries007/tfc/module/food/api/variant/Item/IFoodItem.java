package net.dries007.tfc.module.food.api.variant.Item;

import net.dries007.tfc.TerraFirmaGreg;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.module.food.api.type.FoodType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Интерфейс ICropItem представляет предмет урожая.
 */
public interface IFoodItem extends IHasModel {

    /**
     * Возвращает тип предмета.
     *
     * @return тип
     */
    @Nonnull
    FoodType getType();

    @Nonnull
    FoodItemVariant getItemVariant();

    /**
     * Возвращает расположение в реестре для данного подтипа предмета.
     *
     * @return Расположение в реестре
     */
    @Nonnull
    default String getName() {
        return String.format("food.%s.%s", getItemVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного подтипа предмета.
     *
     * @return Расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaGreg.getID(String.format("food/%s", getType()));
    }

}
