package net.dries007.tfc.api.capability.food;

import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;

public enum Nutrient {
	GRAIN(TextFormatting.GOLD),
	FRUIT(TextFormatting.GREEN),
	VEGETABLES(TextFormatting.DARK_GREEN),
	PROTEIN(TextFormatting.RED), // This may still be referred to as "meat" in various places. They are the same thing.
	DAIRY(TextFormatting.DARK_PURPLE);

	public static final int TOTAL = values().length;
	private static final Nutrient[] VALUES = values();
	private final TextFormatting color;

	Nutrient(TextFormatting color) {
		this.color = color;
	}

	@NotNull
	public static Nutrient valueOf(int i) {
		return i >= 0 && i < VALUES.length ? VALUES[i] : GRAIN;
	}

	@NotNull
	public TextFormatting getColor() {
		return color;
	}
}
