package net.dries007.tfc.api.types.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.IStringSerializable;

@MethodsReturnNonnullByDefault
public enum Soil implements IStringSerializable {

	SILT,
	LOAM,
	SANDY_LOAM,
	SILTY_LOAM;

	private static final Soil[] VALUES = values();

	public static Soil valueOf(int i) {
		return i >= 0 && i < VALUES.length ? VALUES[i] : VALUES[i % VALUES.length];
	}


	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
