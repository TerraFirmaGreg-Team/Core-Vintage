package net.dries007.tfc.api.types2.rock.util;

import net.dries007.tfc.api.types2.rock.Rock;
import net.dries007.tfc.api.types2.rock.RockCategory;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;

import javax.annotation.Nonnull;

public interface IRockBlock extends IHasModel, IItemProvider {
	@Nonnull
	RockVariant getRockVariant();

	@Nonnull
	Rock getRock();

	@Nonnull
	default RockCategory getRockCategory() {
		return getRock().getRockCategory();
	}

	default float getFinalHardness() {
		return getRockVariant().getHardnessBase() + getRockCategory().getHardnessModifier();
	}
}
