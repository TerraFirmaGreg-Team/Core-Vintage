package net.dries007.tfc.api.types.wood;

import net.dries007.tfc.api.types.wood.block.variant.WoodVariant;
import net.dries007.tfc.api.types.wood.type.Wood;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;


public interface IWoodBlock extends IHasModel, IItemProvider {
    WoodVariant getWoodVariant();

    Wood getWood();
}
