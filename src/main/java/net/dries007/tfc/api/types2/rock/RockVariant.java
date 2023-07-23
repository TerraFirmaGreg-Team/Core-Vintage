package net.dries007.tfc.api.types2.rock;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.util.FallingBlockManager.Specification;
import net.dries007.tfc.client.TFCSounds;
import net.minecraft.block.material.Material;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.*;

/**
 * Варианты блоков
 */
@MethodsReturnNonnullByDefault
public enum RockVariant implements IStringSerializable {
	RAW(0, 6.5f, 10f, COLLAPSABLE),
	COBBLE(0, 5.5f, 10f, new Specification(true, () -> TFCSounds.ROCK_SLIDE_SHORT)),
	BRICK(0, 6.5f, 10f, null),
	SMOOTH(0, 6.5f, 10f, COLLAPSABLE),
	GRAVEL(0, 2.0f, 0f, VERTICAL_AND_HORIZONTAL),
	SAND(0, 0.5f, 0f, VERTICAL_AND_HORIZONTAL),
	ANVIL(0, 2.0f, 10f, COLLAPSABLE),
	SPIKE(0, 4f, 10f, null);


	public static final RockVariant[] VALUES = RockVariant.values();

	public static RockVariant valueOf(int i)
	{
		return i >= 0 && i < VALUES.length ? VALUES[i] : RAW;
	}



	private final int harvestLevel;
	private final float hardnessBase;
	private final float resistance;
	@Nullable
	private final Specification fallingSpecification;


	RockVariant(int harvestLevel, float hardnessBase, float resistance, @Nullable Specification fallingSpecification) {
		this.harvestLevel = harvestLevel;
		this.hardnessBase = hardnessBase;
		this.resistance = resistance;
		this.fallingSpecification = fallingSpecification;
	}

	public int getHarvestLevel() {
		return harvestLevel;
	}

	public float getHardnessBase() {
		return hardnessBase;
	}

	public float getResistance()
	{
		return resistance;
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


	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
