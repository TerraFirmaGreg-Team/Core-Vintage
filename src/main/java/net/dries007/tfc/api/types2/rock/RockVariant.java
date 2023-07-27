package net.dries007.tfc.api.types2.rock;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.util.FallingBlockManager.Specification;
import net.dries007.tfc.api.util.IRockTypeBlock;
import net.dries007.tfc.objects.blocks.rock.*;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.*;

/**
 * Варианты блоков
 */
@MethodsReturnNonnullByDefault
public enum RockVariant implements IStringSerializable {
	RAW(BlockRock::new, 0, 6.5f, 10f, COLLAPSABLE, true),
	COBBLE(BlockRockFallable::new, 0, 5.5f, 10f, VERTICAL_AND_HORIZONTAL_ROCK),
	BRICK(BlockRock::new, 0, 6.5f, 10f, null),
	CRACKED(BlockRock::new, 0, 6.5f, 10f, null),
	CHISELED(BlockRock::new, 0, 6.5f, 10f, null),
	SMOOTH(BlockRock::new, 0, 6.5f, 10f, COLLAPSABLE),
	GRAVEL(BlockRockFallable::new, 0, 2.0f, 0f, VERTICAL_AND_HORIZONTAL),
	SAND(BlockRockFallable::new, 0, 0.5f, 0f, VERTICAL_AND_HORIZONTAL),
	ANVIL(BlockRockAnvil::new, 0, 2.0f, 10f, COLLAPSABLE),
	PRESSURE_PLATE(BlockRockPressurePlate::new, 0, 4f, 10f, null),
	BUTTON(BlockRockButton::new, 0, 4f, 10f, null),
	LOOSE(BlockRockLoose::new, 0, 4f, 10f, null),
	SPELEOTHEM(BlockRockSpeleothem::new, 0, 4f, 10f, null);


	public static final RockVariant[] VALUES = RockVariant.values();
	private final BiFunction<RockVariant, RockType, IRockTypeBlock> blockFactory;
	private final int harvestLevel;
	private final float hardnessBase;
	private final float resistance;
	private final boolean variants;
	@Nullable
	private final Specification fallingSpecification;

	RockVariant(BiFunction<RockVariant, RockType, IRockTypeBlock> blockFactory, int harvestLevel, float hardnessBase, float resistance, @Nullable Specification fallingSpecification, boolean variants) {
		this.blockFactory = blockFactory;
		this.harvestLevel = harvestLevel;
		this.hardnessBase = hardnessBase;
		this.resistance = resistance;
		this.variants = variants;
		this.fallingSpecification = fallingSpecification;
	}

	RockVariant(BiFunction<RockVariant, RockType, IRockTypeBlock> blockFactory, int harvestLevel, float hardnessBase, float resistance, @Nullable Specification fallingSpecification) {
		this.blockFactory = blockFactory;
		this.harvestLevel = harvestLevel;
		this.hardnessBase = hardnessBase;
		this.resistance = resistance;
		this.variants = false;
		this.fallingSpecification = fallingSpecification;
	}

	public static RockVariant valueOf(int i) {
		return i >= 0 && i < VALUES.length ? VALUES[i] : RAW;
	}

	public IRockTypeBlock createBlock(RockType stoneType) {
		return this.blockFactory.apply(this, stoneType);
	}

	public int getHarvestLevel() {
		return harvestLevel;
	}

	public float getHardnessBase() {
		return hardnessBase;
	}

	public float getResistance() {
		return resistance;
	}

	public boolean hasVariants() {
		return variants;
	}

	@Nullable
	public Specification getFallingSpecification() {
		return fallingSpecification;
	}

	public boolean canFall() {
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
