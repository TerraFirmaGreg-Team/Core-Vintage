package net.dries007.tfc.api.util;

import net.dries007.tfc.api.types2.BlockVariant;
import net.dries007.tfc.api.types2.StoneType;
import net.dries007.tfc.api.util.IHasModel;
import net.minecraft.item.ItemBlock;

;

public interface IStoneTypeBlock extends IHasModel {
	BlockVariant getBlockVariant();

	StoneType getStoneType();

	ItemBlock getItemBlock();

	default float getFinalHardness() {
		return getStoneType().getHardnessMultiplier() * getStoneType().getStoneCategory().getHardnessMultiplier();
	}
}
