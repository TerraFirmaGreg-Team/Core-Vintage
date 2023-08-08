package net.dries007.tfc.api.types.wood;

import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodVariant_old;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;


public interface IWoodBlock extends IHasModel, IItemProvider {
    WoodVariant_old getWoodVariant();

    WoodType getWood();
}
