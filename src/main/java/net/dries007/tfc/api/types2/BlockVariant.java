package net.dries007.tfc.api.types2;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.IStringSerializable;

/**
 * Варианты блоков
 */
@MethodsReturnNonnullByDefault
public enum BlockVariant implements IStringSerializable {
	RAW(0),
	COBBLE(0),
	BRICK(0),
	SMOOTH(0),
	OVERGROWN(0),
	OVERGROWN_SNOWED(0),
	GRAVEL(0);


	private final int harvestLevel;


	BlockVariant(int harvestLevel) {
		this.harvestLevel = harvestLevel;
	}

	public int getHarvestLevel() {
		return harvestLevel;
	}


	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
