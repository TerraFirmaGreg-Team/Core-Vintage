package net.dries007.tfc.api.types2.rock;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

import static net.dries007.tfc.api.types2.rock.RockCategory.*;


/**
 * Перечисление, которое представляет различные типы камня. 20 типов
 */
@Nonnull
public enum RockType implements IStringSerializable {

	// Igneous Intrusive
	GRANITE(IGNEOUS_INTRUSIVE),
	DIORITE(IGNEOUS_INTRUSIVE),
	GABBRO(IGNEOUS_INTRUSIVE),

	// Sedimentary
	SHALE(SEDIMENTARY),
	CLAYSTONE(SEDIMENTARY),
	LIMESTONE(SEDIMENTARY),
	CONGLOMERATE(SEDIMENTARY),
	DOLOMITE(SEDIMENTARY),
	CHERT(SEDIMENTARY),
	CHALK(SEDIMENTARY, true),

	// Igneous Extrusive
	RHYOLITE(IGNEOUS_EXTRUSIVE),
	BASALT(IGNEOUS_EXTRUSIVE),
	ANDESITE(IGNEOUS_EXTRUSIVE, true),
	DACITE(IGNEOUS_EXTRUSIVE),

	// Metamorphic
	QUARTZITE(METAMORPHIC, true),
	SLATE(METAMORPHIC),
	PHYLLITE(METAMORPHIC),
	SCHIST(METAMORPHIC),
	GNEISS(METAMORPHIC),
	MARBLE(METAMORPHIC, true);

	private static final RockType[] VALUES = values();
	public static RockType valueOf(int i) {
		return i >= 0 && i < VALUES.length ? VALUES[i] : GRANITE;
	}

	private final RockCategory rockCategory;
	private final boolean isFlux;

	RockType(@Nonnull RockCategory rockCategory) {
		this(rockCategory, false);
	}

	RockType(@Nonnull RockCategory rockCategory, boolean isFlux) {
		this.rockCategory = rockCategory;
		this.isFlux = isFlux;
	}

	public @Nonnull RockCategory getRockCategory() {
		return rockCategory;
	}

	public boolean isFlux() {
		return isFlux;
	}

	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Override
	public @Nonnull String getName() {
		return name().toLowerCase();
	}
}
