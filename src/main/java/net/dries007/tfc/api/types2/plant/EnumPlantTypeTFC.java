package net.dries007.tfc.api.types2.plant;

import net.minecraft.util.IStringSerializable;

public enum EnumPlantTypeTFC implements IStringSerializable {
	CLAY,
	DESERT_CLAY,
	DRY_CLAY,
	DRY,
	FRESH_BEACH,
	SALT_BEACH,
	FRESH_WATER,
	SALT_WATER,
	NONE;

	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
