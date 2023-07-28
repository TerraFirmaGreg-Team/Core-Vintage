package net.dries007.tfc.api.types2.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.util.FallingBlockManager.Specification;
import net.dries007.tfc.api.util.ISoilTypeBlock;
import net.dries007.tfc.objects.blocks.soil.BlockSoil;
import net.dries007.tfc.objects.blocks.soil.BlockSoilFarmland;
import net.dries007.tfc.objects.blocks.soil.BlockSoilGrass;
import net.dries007.tfc.objects.blocks.soil.BlockSoilPath;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_ONLY;

@MethodsReturnNonnullByDefault
public enum SoilVariant implements IStringSerializable {
	DIRT(VERTICAL_AND_HORIZONTAL, BlockSoil::new),
	GRASS(VERTICAL_AND_HORIZONTAL, BlockSoilGrass::new),
	DRY_GRASS(VERTICAL_AND_HORIZONTAL, BlockSoilGrass::new),
	PATH(VERTICAL_ONLY, BlockSoilPath::new),
	CLAY(VERTICAL_ONLY, BlockSoil::new),
	CLAY_GRASS(VERTICAL_ONLY, BlockSoilGrass::new),
	FARMLAND(VERTICAL_ONLY, BlockSoilFarmland::new);
//	ROOTED_DIRT(VERTICAL_ONLY),
//	MUD(VERTICAL_ONLY),
//	MUD_BRICKS(VERTICAL_ONLY),
//	DRYING_BRICKS(VERTICAL_ONLY);


	public static final SoilVariant[] VALUES = SoilVariant.values();
	@Nullable
	private final Specification fallingSpecification;
	private final BiFunction<SoilVariant, SoilType, ISoilTypeBlock> blockFactory;

	SoilVariant(@Nullable Specification fallingSpecification, BiFunction<SoilVariant, SoilType, ISoilTypeBlock> blockFactory) {
		this.fallingSpecification = fallingSpecification;
		this.blockFactory = blockFactory;
	}

	public static SoilVariant valueOf(int i) {
		return i >= 0 && i < VALUES.length ? VALUES[i] : DIRT;
	}

	public ISoilTypeBlock createBlock(SoilType soilType) {
		return this.blockFactory.apply(this, soilType);
	}

	@Nullable
	public Specification getFallingSpecification() {
		return fallingSpecification;
	}

	public boolean canFall() {
		return fallingSpecification != null;
	}


	public SoilVariant getNonGrassVersion() {
		switch (this) {
			case GRASS:
			case DRY_GRASS:
				return DIRT;
			case CLAY_GRASS:
				return CLAY;
		}
		throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
	}

	public SoilVariant getGrassVersion(SoilVariant spreader) {
		switch (this) {
			case DIRT:
				return spreader == DRY_GRASS ? DRY_GRASS : GRASS;
			case CLAY:
				return CLAY_GRASS;
		}
		throw new IllegalArgumentException("You cannot get grass from rock types.");
	}


	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
