package net.dries007.tfc.api.types2.rock.util;

import net.dries007.tfc.api.types2.rock.RockCategory;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.minecraft.item.ItemBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IRockBlock extends IHasModel {
	@Nonnull
	RockVariant getRockVariant();

	@Nonnull
	RockType getRockType();

	@Nullable
	ItemBlock getItemBlock();

	@Nonnull
	default RockCategory getRockCategory() {
		return getRockType().getRockCategory();
	}

	default float getFinalHardness() {
		return getRockVariant().getHardnessBase() + getRockCategory().getHardnessModifier();
	}
}
