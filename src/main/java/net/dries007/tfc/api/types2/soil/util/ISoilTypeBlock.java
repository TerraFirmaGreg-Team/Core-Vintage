package net.dries007.tfc.api.types2.soil.util;

import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.minecraft.item.ItemBlock;


public interface ISoilTypeBlock extends IHasModel {
	SoilVariant getSoilVariant();

	SoilType getSoilType();

	ItemBlock getItemBlock();
}
