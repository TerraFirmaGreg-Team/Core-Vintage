package net.dries007.tfc.api.types2.rock.util;

import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.minecraft.item.ItemBlock;

public interface IRockBlock extends IHasModel {
	RockVariant getRockVariant();

	RockType getRockType();

	ItemBlock getItemBlock();

	default float getFinalHardness() {
		return getRockVariant().getHardnessBase() + getRockType().getRockCategory().getHardnessModifier();
	}
}
