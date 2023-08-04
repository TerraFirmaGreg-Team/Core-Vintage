package net.dries007.tfc.api.types.rock.util;

import net.dries007.tfc.api.types.rock.Rock;
import net.dries007.tfc.api.types.rock.RockCategory;

import javax.annotation.Nonnull;

public interface IRockItem {
	@Nonnull
	Rock getRock();

	@Nonnull
	default RockCategory getRockCategory() {
		return getRock().getRockCategory();
	}
}
