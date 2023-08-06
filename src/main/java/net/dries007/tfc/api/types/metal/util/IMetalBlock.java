package net.dries007.tfc.api.types.metal.util;

import gregtech.api.unification.material.Material;
import net.dries007.tfc.api.types.metal.MetalVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;

import javax.annotation.Nullable;


public interface IMetalBlock extends IHasModel, IItemProvider {

    MetalVariant getMetalVariant();

    @Nullable
    Material getMaterial();
}
