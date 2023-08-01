/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.api.types2.rock.util;

import net.dries007.tfc.api.types2.rock.RockCategory;
import net.dries007.tfc.api.types2.rock.RockType;

import javax.annotation.Nonnull;

public interface IRockItem {
	@Nonnull
	RockType getRockType();

	default RockCategory getRockCategory() {
		return getRockType().getRockCategory();
	}
}
