package net.dries007.tfc.api.types2.rock;

import net.dries007.tfc.TerraFirmaCraft;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dries007.tfc.api.types2.rock.RockCategory.*;


/**
 * Перечисление, которое представляет различные типы камня. 20 типов
 */
@Nonnull
public enum Rock implements IStringSerializable {
	// Igneous Intrusive
	GRANITE(IGNEOUS_INTRUSIVE),
	DIORITE(IGNEOUS_INTRUSIVE),
	GABBRO(IGNEOUS_INTRUSIVE),

	// Sedimentary
	SHALE(SEDIMENTARY),
	CLAYSTONE(SEDIMENTARY),
	LIMESTONE(SEDIMENTARY, true),
	CONGLOMERATE(SEDIMENTARY),
	DOLOMITE(SEDIMENTARY, true),
	CHERT(SEDIMENTARY),
	CHALK(SEDIMENTARY, true),

	// Igneous Extrusive
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
	MARBLE(METAMORPHIC, true);

	public static final IProperty<Rock> ROCKTYPE = PropertyEnum.create("rocktype", Rock.class);
	private static final Rock[] VALUES = values();
	private final RockCategory rockCategory;
	private final boolean isFlux;

	Rock(@Nonnull RockCategory rockCategory) {
		this(rockCategory, false);
	}

	Rock(@Nonnull RockCategory rockCategory, boolean isFlux) {
		this.rockCategory = rockCategory;
		this.isFlux = isFlux;
	}

	public static Rock valueOf(int i) {
		return i >= 0 && i < VALUES.length ? VALUES[i] : VALUES[i % VALUES.length];
	}

	@Nonnull
	public RockCategory getRockCategory() {
		return rockCategory;
	}

	public boolean isFlux() {
		return isFlux;
	}

	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Nonnull
	@Override
	public String getName() {
		return name().toLowerCase();
	}

	@Nonnull
	public ResourceLocation getTexture() {
		return new ResourceLocation(TerraFirmaCraft.MOD_ID, "textures/blocks/rock/raw/" + this.getName() + ".png");
	}
}
