package net.dries007.tfc.api.types.wood.util;

import net.dries007.tfc.api.types.wood.Wood;
import net.dries007.tfc.api.types.wood.WoodVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;


public interface IWoodBlock extends IHasModel, IItemProvider {
	WoodVariant getWoodVariant();

	Wood getWood();
}
