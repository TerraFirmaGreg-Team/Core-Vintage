/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.api.capability.size;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum Size implements IStringSerializable {
	TINY, // Fits in anything
	VERY_SMALL, // Fits in anything
	SMALL, // Fits in small vessels
	NORMAL, // Fits in large vessels
	LARGE, // Fits in chests, Pit kilns can hold four
	VERY_LARGE, // Pit kilns can only hold one
	HUGE; // Counts towards overburdened, fits in nothing


	public boolean isSmallerThan(Size other) {
		return this.ordinal() < other.ordinal();
	}

	@Nonnull
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
