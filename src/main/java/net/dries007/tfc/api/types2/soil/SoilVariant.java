package net.dries007.tfc.api.types2.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.FallingBlockManager.Specification;
import net.dries007.tfc.api.util.ISoilTypeBlock;
import net.dries007.tfc.objects.blocks.soil.BlockSoil;
import net.dries007.tfc.objects.blocks.soil.BlockSoilFallable;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_ONLY;

@MethodsReturnNonnullByDefault
public enum SoilVariant implements IStringSerializable {
	DIRT(VERTICAL_AND_HORIZONTAL, BlockSoilFallable::new),
	GRASS(VERTICAL_AND_HORIZONTAL, BlockSoil::new),
	DRY_GRASS(VERTICAL_AND_HORIZONTAL, BlockSoil::new),
	PATH(VERTICAL_ONLY, BlockSoil::new),
	CLAY(VERTICAL_ONLY, BlockSoilFallable::new),
	CLAY_GRASS(VERTICAL_ONLY, BlockSoil::new),
	FARMLAND(VERTICAL_ONLY, BlockSoil::new);
//	ROOTED_DIRT(VERTICAL_ONLY),
//	MUD(VERTICAL_ONLY),
//	MUD_BRICKS(VERTICAL_ONLY),
//	DRYING_BRICKS(VERTICAL_ONLY);


	public static final SoilVariant[] VALUES = SoilVariant.values();

	public static SoilVariant valueOf(int i)
	{
		return i >= 0 && i < VALUES.length ? VALUES[i] : DIRT;
	}

	@Nullable
	private final Specification fallingSpecification;

	private final BiFunction<SoilVariant, SoilType, ISoilTypeBlock> blockFactory;

	SoilVariant(@Nullable Specification fallingSpecification, BiFunction<SoilVariant, SoilType, ISoilTypeBlock> blockFactory) {
		this.fallingSpecification = fallingSpecification;
		this.blockFactory = blockFactory;
	}

	public ISoilTypeBlock createBlock(SoilType soilType) {
		return this.blockFactory.apply(this, soilType);
	}

	@Nullable
	public Specification getFallingSpecification()
	{
		return fallingSpecification;
	}

	public boolean canFall()
	{
		return fallingSpecification != null;
	}


	public SoilVariant getNonGrassVersion() {
		switch (this) {
			case GRASS:
				return DIRT;
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
