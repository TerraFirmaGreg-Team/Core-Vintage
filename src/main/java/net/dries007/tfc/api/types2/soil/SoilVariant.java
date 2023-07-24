package net.dries007.tfc.api.types2.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.*;

@MethodsReturnNonnullByDefault
public enum SoilVariant implements IStringSerializable {
	DIRT(VERTICAL_AND_HORIZONTAL),
	GRASS(VERTICAL_AND_HORIZONTAL),
	DRY_GRASS(VERTICAL_AND_HORIZONTAL),
	PATH(VERTICAL_ONLY),
	CLAY(VERTICAL_ONLY),
	CLAY_GRASS(VERTICAL_ONLY),
	FARMLAND(VERTICAL_ONLY),
	ROOTED_DIRT(VERTICAL_ONLY),
	MUD(VERTICAL_ONLY),
	MUD_BRICKS(VERTICAL_ONLY),
	DRYING_BRICKS(VERTICAL_ONLY);


	public static final SoilVariant[] VALUES = SoilVariant.values();

	public static SoilVariant valueOf(int i)
	{
		return i >= 0 && i < VALUES.length ? VALUES[i] : DIRT;
	}

	@Nullable
	private final FallingBlockManager.Specification fallingSpecification;

	SoilVariant(@Nullable FallingBlockManager.Specification fallingSpecification) {
		this.fallingSpecification = fallingSpecification;
	}

	@Nullable
	public FallingBlockManager.Specification getFallingSpecification()
	{
		return fallingSpecification;
	}

	public boolean canFall()
	{
		return fallingSpecification != null;
	}


	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
