package net.dries007.tfc.api.types2.rock;

import net.dries007.tfc.api.util.IStoneTypeBlock;
import net.dries007.tfc.api.util.TriFunction;
import net.dries007.tfc.objects.blocks.rock.BlockRock;
import net.dries007.tfc.objects.blocks.rock.BlockRockFallable;
import net.dries007.tfc.objects.blocks.stone2.*;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

import static net.dries007.tfc.api.types2.rock.RockVariant.*;

/**
 * Тип блока, принимает первым параметром, класс,
 * который будет создаваться, вторым параметром варианты для которых он создаст класс
 */
@Nonnull
public enum RockBlockType implements IStringSerializable {
	/**
	 * Обычный блок без каких-либо доп. механик
	 */
	ORDINARY(BlockRock::new, RAW, SMOOTH, BRICK),
	/**
	 * Обычный блок, но с механикой падения
	 */
	FALLABLE(BlockRockFallable::new, GRAVEL, COBBLE, SAND),
	/**
	 * Обычный блок без каких-либо доп. механик
	 */
	CRACKED(BlockRock::new, BRICK),
	/**
	 * Обычный блок без каких-либо доп. механик
	 */
	CHISELED(BlockRock::new, BRICK),
	/**
	 * Обычный блок, но с механикой мха
	 */
	MOSSY(BlockMossyTFG::new, COBBLE, BRICK),
	/**
	 * Ступенька
	 */
	STAIRS(BlockStairTFG::new, RAW, COBBLE, BRICK, SMOOTH),
	/**
	 * Двойной полублок
	 */
	SLAB_DOUBLE(BlockSlabTFG.Double::new, RAW, COBBLE, BRICK, SMOOTH),
	/**
	 * Полублок
	 */
	SLAB(BlockSlabTFG.Half::new, RAW, COBBLE, BRICK, SMOOTH),
	/**
	 * Блок стены
	 */
	WALL(BlockWallTFG::new, RAW, COBBLE, BRICK, SMOOTH),
	/**
	 * Блок кнопки
	 */
	BUTTON(BlockButtonTFG::new, RAW),
	/**
	 * Блок нажимной пластины
	 */
	PRESSURE_PLATE(BlockPressurePlateTFG::new, RAW),
	/**
	 * Лежачие предметы
	 */
	//LOOSE(BlockLooseRockTFG::new, RAW),
	/**
	 * Сталагмиты, сталактиты
	 */
	SPELEOTHEM(BlockSpeleothemTFG::new, RAW);

	private final TriFunction<RockBlockType, RockVariant, RockType, IStoneTypeBlock> blockFactory;
	private final RockVariant[] rockVariants;

	RockBlockType(TriFunction<RockBlockType, RockVariant, RockType, IStoneTypeBlock> blockFactory, RockVariant... rockVariants) {
		this.rockVariants = rockVariants;
		this.blockFactory = blockFactory;
	}

	public IStoneTypeBlock createBlock(RockVariant rockVariant, RockType stoneType) {
		return this.blockFactory.apply(this, rockVariant, stoneType);
	}

	public RockVariant[] getBlockVariants() {
		return rockVariants;
	}

	@Nonnull
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
