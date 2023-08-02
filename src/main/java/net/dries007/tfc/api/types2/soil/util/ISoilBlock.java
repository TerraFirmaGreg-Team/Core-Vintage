package net.dries007.tfc.api.types2.soil.util;

import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.IGetItems;
import net.dries007.tfc.api.util.IHasModel;


public interface ISoilBlock extends IHasModel, IGetItems {
	SoilVariant getSoilVariant();

	SoilType getSoilType();
}
