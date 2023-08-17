package net.dries007.tfc.api.types.metal;

import gregtech.api.unification.material.Material;
import net.dries007.tfc.api.types.metal.variant.MetalBlockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;

import javax.annotation.Nullable;

/**
 * Интерфейс, представляющий блок металла.
 */
public interface IMetalBlock extends IHasModel, IItemProvider {

    /**
     * Возвращает вариант металлического блока.
     *
     * @return Вариант металлического блока.
     */
    MetalBlockVariant getBlockVariant();

    /**
     * Возвращает материал металлического блока.
     *
     * @return Материал металлического блока или null, если материал не определен.
     */
    @Nullable
    Material getMaterial();
}
