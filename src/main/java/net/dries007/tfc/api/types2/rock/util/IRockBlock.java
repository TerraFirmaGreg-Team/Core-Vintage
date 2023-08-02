package net.dries007.tfc.api.types2.rock.util;

import net.dries007.tfc.api.types2.rock.RockCategory;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;

import javax.annotation.Nonnull;

public interface IRockBlock extends IHasModel, IItemProvider {
	@Nonnull
	RockVariant getRockVariant();

	@Nonnull
	RockType getRockType();

	@Nonnull
	default RockCategory getRockCategory() {
		return getRockType().getRockCategory();
	}

	default float getFinalHardness() {
		return getRockVariant().getHardnessBase() + getRockCategory().getHardnessModifier();
	}
}
