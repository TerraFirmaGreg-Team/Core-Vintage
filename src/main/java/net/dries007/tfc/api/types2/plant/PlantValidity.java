package net.dries007.tfc.api.types2.plant;

import net.minecraft.util.IStringSerializable;

public enum PlantValidity implements IStringSerializable {
	COLD,
	HOT,
	DRY,
	WET,
	VALID;

	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
