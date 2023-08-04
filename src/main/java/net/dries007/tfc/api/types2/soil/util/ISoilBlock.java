package net.dries007.tfc.api.types2.soil.util;

import net.dries007.tfc.api.types2.soil.Soil;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;


public interface ISoilBlock extends IHasModel, IItemProvider {
		SoilVariant getSoilVariant();

		Soil getSoil();
}
