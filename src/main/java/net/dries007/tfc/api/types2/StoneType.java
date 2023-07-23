package net.dries007.tfc.api.types2;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

import static net.dries007.tfc.api.types2.StoneCategory.*;


/**
 * Перечисление, которое представляет различные типы камня.
 */
@Nonnull
public enum StoneType implements IStringSerializable {

	// Igneous
	GRANITE(IGNEOUS, 1.6F, 1.39F),
	DIORITE(IGNEOUS, 1.6F, 1.39F),
	GABBRO(IGNEOUS, 1.0F, 1.0F),
	BRECCIA(IGNEOUS, 1.0F, 1.0F),
	PORPHYRY(IGNEOUS, 1.0F, 1.0F),
	RHYOLITE(IGNEOUS, 1.3F, 1.26F),
	BASALT(IGNEOUS, 1.4F, 1.31F),
	ANDESITE(IGNEOUS, 1.4F, 1.31F),
	DACITE(IGNEOUS, 1.2F, 1.2F),
	PERIDOTITE(IGNEOUS, 1.5F, 1.35F),

	// Sedimentary
	SHALE(SEDIMENTARY, 0.5F, 0.29F),
	CLAYSTONE(SEDIMENTARY, 1.0F, 1.0F),
	LIMESTONE(SEDIMENTARY, 0.5F, 0.29F),
	CONGLOMERATE(SEDIMENTARY, 1.0F, 1.0F),
	DOLOMITE(SEDIMENTARY, 0.5F, 0.29F),
	CHERT(SEDIMENTARY, 0.9F, 0.86F),
	CHALK(SEDIMENTARY, 0.5F, 0.29F),
	MUDSTONE(SEDIMENTARY, 1.0F, 1.0F),
	PSAMMOLITE(SEDIMENTARY, 1.0F, 1.0F),
	SILTSTONE(SEDIMENTARY, 0.6F, 0.4F),

	// Metamorphic
	ORTHOQUARTZITE(METAMORPHIC, 1.3F, 1.26F),
	SLATE(METAMORPHIC, 1.1F, 1.11F),
	PHYLLITE(METAMORPHIC, 1.1F, 1.11F),
	SCHIST(METAMORPHIC, 1.0F, 1.0F),
	GNEISS(METAMORPHIC, 1.1F, 1.11F),
	MARBLE(METAMORPHIC, 1.1F, 1.11F),
	CATLINITE(METAMORPHIC, 1.1F, 1.11F),
	BLUESCHIST(METAMORPHIC, 0.7F, 0.54F),
	GREENSCHIST(METAMORPHIC, 0.7F, 0.54F),
	NOVACULITE(METAMORPHIC, 1.1F, 1.11F),
	STEATITE(METAMORPHIC, 0.4F, 0.2F),
	KOMATIITE(METAMORPHIC, 1.5F, 1.35F);


	private final StoneCategory stoneCategory;
	private final float hardnessMultiplier;
	private final float resistance;

	StoneType(@Nonnull StoneCategory stoneCategory, float hardnessMultiplier, float resistance) {
		this.stoneCategory = stoneCategory;
		this.hardnessMultiplier = hardnessMultiplier;
		this.resistance = resistance;
	}

	public StoneCategory getStoneCategory() {
		return stoneCategory;
	}

	public float getHardnessMultiplier() {
		return hardnessMultiplier;
	}

	public float getResistance() {
		return resistance;
	}

	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Override
	public @Nonnull String getName() {
		return name().toLowerCase();
	}
}
