package net.dries007.tfc.api.types2.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.IStringSerializable;

@MethodsReturnNonnullByDefault
public enum SoilType implements IStringSerializable {

	SILT,
	LOAM,
	SANDY_LOAM,
	SILTY_LOAM;

	private static final SoilType[] VALUES = values();
	public static SoilType valueOf(int i) {
		return i >= 0 && i < VALUES.length ? VALUES[i] : SILT;
	}



	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
