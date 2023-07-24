package net.dries007.tfc.api.types2.soil;

import net.dries007.tfc.api.util.ISoilTypeBlock;
import net.dries007.tfc.api.util.TriFunction;
import net.dries007.tfc.objects.blocks.soil.BlockSoil;
import net.dries007.tfc.objects.blocks.soil.BlockSoilFallable;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

import static net.dries007.tfc.api.types2.soil.SoilVariant.*;

public enum SoilBlockType implements IStringSerializable {

	ORDINARY(BlockSoil::new, GRASS, DRY_GRASS, CLAY_GRASS),
	FALLING(BlockSoilFallable::new, DIRT, CLAY);

	private final TriFunction<SoilBlockType, SoilVariant, SoilType, ISoilTypeBlock> blockFactory;
	private final SoilVariant[] soilVariants;

	SoilBlockType(TriFunction<SoilBlockType, SoilVariant, SoilType, ISoilTypeBlock> blockFactory, SoilVariant... soilVariants) {
		this.soilVariants = soilVariants;
		this.blockFactory = blockFactory;
	}

	public ISoilTypeBlock createBlock(SoilVariant soilVariants, SoilType soilType) {
		return this.blockFactory.apply(this, soilVariants, soilType);
	}

	public SoilVariant[] getSoilVariants() {
		return soilVariants;
	}

	@Nonnull
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
