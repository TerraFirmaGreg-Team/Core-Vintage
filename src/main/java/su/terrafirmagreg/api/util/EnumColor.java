package su.terrafirmagreg.api.util;

import lombok.Getter;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

public enum EnumColor implements IStringSerializable {
	WHITE(0, 15, "white", "white", 16383998, TextFormatting.WHITE),
	ORANGE(1, 14, "orange", "orange", 16351261, TextFormatting.GOLD),
	MAGENTA(2, 13, "magenta", "magenta", 13061821, TextFormatting.AQUA),
	LIGHT_BLUE(3, 12, "light_blue", "lightBlue", 3847130, TextFormatting.BLUE),
	YELLOW(4, 11, "yellow", "yellow", 16701501, TextFormatting.YELLOW),
	LIME(5, 10, "lime", "lime", 8439583, TextFormatting.GREEN),
	PINK(6, 9, "pink", "pink", 15961002, TextFormatting.LIGHT_PURPLE),
	GRAY(7, 8, "gray", "gray", 4673362, TextFormatting.DARK_GRAY),
	SILVER(8, 7, "silver", "silver", 10329495, TextFormatting.GRAY),
	CYAN(9, 6, "cyan", "cyan", 1481884, TextFormatting.DARK_AQUA),
	PURPLE(10, 5, "purple", "purple", 8991416, TextFormatting.DARK_PURPLE),
	BLUE(11, 4, "blue", "blue", 3949738, TextFormatting.DARK_BLUE),
	BROWN(12, 3, "brown", "brown", 8606770, TextFormatting.GOLD),
	GREEN(13, 2, "green", "green", 6192150, TextFormatting.DARK_GREEN),
	RED(14, 1, "red", "red", 11546150, TextFormatting.DARK_RED),
	BLACK(15, 0, "black", "black", 1908001, TextFormatting.BLACK),
	COLORLESS(15, 0, "colorless", "colorless", 0, TextFormatting.RESET);

	private static final EnumColor[] META_LOOKUP = new EnumColor[values().length];
	private static final EnumColor[] DYE_DMG_LOOKUP = new EnumColor[values().length];

	static {
		for (EnumColor enumdyecolor : values()) {
			META_LOOKUP[enumdyecolor.getMetadata()] = enumdyecolor;
			DYE_DMG_LOOKUP[enumdyecolor.getDyeDamage()] = enumdyecolor;
		}
	}

	/**
	 * An int containing the corresponding RGB color for this dye color.
	 */
	public final int colorValue;
	private final int meta;
	@Getter
	private final int dyeDamage;
	private final String name;
	@Getter
	private final String translationKey;
	/**
	 * An array containing 3 floats ranging from 0.0 to 1.0: the red, green, and blue components of the corresponding
	 * color.
	 */
	private final float[] colorComponentValues;
	private final TextFormatting chatColor;

	EnumColor(int metaIn, int dyeDamageIn, String nameIn, String unlocalizedNameIn, int colorValueIn, TextFormatting chatColorIn) {
		this.meta = metaIn;
		this.dyeDamage = dyeDamageIn;
		this.name = nameIn;
		this.translationKey = unlocalizedNameIn;
		this.colorValue = colorValueIn;
		this.chatColor = chatColorIn;
		int i = (colorValueIn & 16711680) >> 16;
		int j = (colorValueIn & 65280) >> 8;
		int k = (colorValueIn & 255);
		this.colorComponentValues = new float[]{(float) i / 255.0F, (float) j / 255.0F, (float) k / 255.0F};
	}

	public static EnumColor byDyeDamage(int damage) {
		if (damage < 0 || damage >= DYE_DMG_LOOKUP.length) {
			damage = 0;
		}

		return DYE_DMG_LOOKUP[damage];
	}

	public static EnumColor byMetadata(int meta) {
		if (meta < 0 || meta >= META_LOOKUP.length) {
			meta = 0;
		}

		return META_LOOKUP[meta];
	}

	public int getMetadata() {
		return this.meta;
	}

	@SideOnly(Side.CLIENT)
	public String getDyeColorName() {
		return this.name;
	}

	/**
	 * Gets the RGB color corresponding to this dye color.
	 */
	@SideOnly(Side.CLIENT)
	public int getColorValue() {
		return this.colorValue;
	}

	/**
	 * Gets an array containing 3 floats ranging from 0.0 to 1.0: the red, green, and blue components of the
	 * corresponding color.
	 */
	public float[] getColorComponentValues() {
		return this.colorComponentValues;
	}

	public String toString() {
		return this.translationKey;
	}

	public @NotNull String getName() {
		return this.name;
	}
}
