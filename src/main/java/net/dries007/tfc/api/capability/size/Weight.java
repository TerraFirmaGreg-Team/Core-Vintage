package net.dries007.tfc.api.capability.size;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum Weight implements IStringSerializable {
	VERY_LIGHT(64),
	LIGHT(32),
	MEDIUM(16),
	HEAVY(4),
	VERY_HEAVY(1);

	private final int stackSize;

	Weight(int stackSize) {
		this.stackSize = stackSize;
	}

	public int getStackSize() {
		return stackSize;
	}

	public String getLocalizedName() {
		return I18n.format("tfc.enum.weight." + getName() + ".name");
	}

	@Nonnull
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
