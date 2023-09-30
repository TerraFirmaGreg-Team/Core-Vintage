package net.dries007.tfc.module.agriculture.api.bush;

import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.agriculture.api.bush.type.BushType;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public interface IBushBlock extends IHasModel, IItemProvider {

    /**
     * Возвращает тип блока куста.
     *
     * @return Тип блока культуры.
     */
    @Nonnull
    BushType getType();

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @Nonnull
    default String getName() {
        return String.format("berry_bush.%s", getType());
    }


    /**
     * Возвращает местоположение ресурса блока породы.
     *
     * @return Расположение ресурса.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return Helpers.getID(String.format("berry_bush/%s", getType()));
    }


    /**
     * Размеры куста.
     */
    enum Size {
        SMALL,
        MEDIUM,
        LARGE
    }
}
