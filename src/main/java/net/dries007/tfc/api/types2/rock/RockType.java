package net.dries007.tfc.api.types2.rock;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

import static net.dries007.tfc.api.types2.rock.RockCategory.*;


/**
 * Перечисление, которое представляет различные типы камня. 20 типов
 */
@Nonnull
public enum RockType implements IStringSerializable {

	// igneous_intrusive
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
	CHALK(SEDIMENTARY),

	// igneous_extrusive
	RHYOLITE(IGNEOUS_EXTRUSIVE),
	BASALT(IGNEOUS_EXTRUSIVE),
	ANDESITE(IGNEOUS_EXTRUSIVE),
	DACITE(IGNEOUS_EXTRUSIVE),

	// Metamorphic
	QUARTZITE(METAMORPHIC),
	SLATE(METAMORPHIC),
	PHYLLITE(METAMORPHIC),
	SCHIST(METAMORPHIC),
	GNEISS(METAMORPHIC),
	MARBLE(METAMORPHIC);

	public static final RockType[] VALUES = values();
	public static RockType valueOf(int i) {
		return i >= 0 && i < VALUES.length ? VALUES[i] : GRANITE;
	}

	private final RockCategory rockCategory;

	RockType(@Nonnull RockCategory rockCategory) {
		this.rockCategory = rockCategory;
	}


	public RockCategory getRockCategory() {
		return rockCategory;
	}

//	public boolean isFluxStone()
//	{
//		return isFluxStone;
//	}


	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Override
	public @Nonnull String getName() {
		return name().toLowerCase();
	}
}
