package net.dries007.tfc.api.types2.wood.util;

import net.dries007.tfc.api.types2.wood.Wood;
import net.dries007.tfc.api.types2.wood.WoodVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;


public interface IWoodBlock extends IHasModel, IItemProvider {
		WoodVariant getWoodVariant();

		Wood getWood();
}
