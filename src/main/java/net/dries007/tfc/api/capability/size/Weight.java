/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.api.capability.size;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum Weight implements IStringSerializable {
	VERY_LIGHT(64),
	LIGHT(32),
	MEDIUM(16),
	HEAVY(4),
	VERY_HEAVY(1);

	public final int stackSize;

	Weight(int stackSize) {
		this.stackSize = stackSize;
	}

	public boolean isSmallerThan(Weight other) {
		return this.stackSize > other.stackSize;
	}

	@Nonnull
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
