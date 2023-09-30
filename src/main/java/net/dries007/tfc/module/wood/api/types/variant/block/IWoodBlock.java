package net.dries007.tfc.module.wood.api.types.variant.block;

import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.core.api.util.IItemProvider;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.wood.api.types.type.WoodType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;


/**
 * Интерфейс IWoodBlock представляет деревянный блок.
 */
public interface IWoodBlock extends IHasModel, IItemProvider {

    /**
     * Возвращает вариант деревянного блока.
     *
     * @return вариант деревянного блока
     */
    WoodBlockVariant getBlockVariant();

    /**
     * Возвращает тип дерева блока.
     *
     * @return тип дерева
     */
    WoodType getType();


    /**
     * Возвращает расположение в реестре для данного деревянного блока.
     *
     * @return расположение в реестре
     */
    @Nonnull
    default String getName() {
        return String.format("wood.%s.%s", getBlockVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного деревянного блока.
     *
     * @return расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return Helpers.getID(String.format("wood/%s", getBlockVariant()));
    }
}
