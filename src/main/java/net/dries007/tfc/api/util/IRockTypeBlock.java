package net.dries007.tfc.api.util;

import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.minecraft.item.ItemBlock;

public interface IRockTypeBlock extends IHasModel {
	RockVariant getRockVariant();

	RockType getRockType();

	ItemBlock getItemBlock();

	default float getFinalHardness() {
		return getRockVariant().getHardnessBase() + getRockType().getRockCategory().getHardnessModifier();
	}
}
