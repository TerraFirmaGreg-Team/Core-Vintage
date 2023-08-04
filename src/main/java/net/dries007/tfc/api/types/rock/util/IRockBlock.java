package net.dries007.tfc.api.types.rock.util;

import net.dries007.tfc.api.types.rock.Rock;
import net.dries007.tfc.api.types.rock.RockCategory;
import net.dries007.tfc.api.types.rock.RockVariant;
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
