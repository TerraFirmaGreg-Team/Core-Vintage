package net.dries007.tfc.api.types2.rock;

import net.dries007.tfc.api.types2.rock.util.IRockBlock;
import net.dries007.tfc.api.util.TriFunction;
import net.dries007.tfc.objects.blocks.rock.BlockRockMossy;
import net.dries007.tfc.objects.blocks.rock.BlockRockSlab;
import net.dries007.tfc.objects.blocks.rock.BlockRockStair;
import net.dries007.tfc.objects.blocks.rock.BlockRockWall;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

import static net.dries007.tfc.api.types2.rock.RockVariant.*;

/**
 * Типы блока, принимает первым параметром, класс,
 * который будет создаваться, вторым параметром варианты для которых он создаст класс
 */
@Nonnull
public enum RockBlockType implements IStringSerializable {
	ORDINARY(null, RAW, COBBLE, BRICK, CRACKED, CHISELED, SMOOTH, GRAVEL, SAND, ANVIL, PRESSURE_PLATE, BUTTON, LOOSE, SPELEOTHEM),
	/**
	 * Блок с мхом
	 */
	MOSSY(BlockRockMossy::new, COBBLE, BRICK),
	/**
	 * Ступенька
	 */
	STAIRS(BlockRockStair::new, RAW, COBBLE, BRICK, SMOOTH),
	/**
	 * Двойной полублок
	 */
	SLAB_DOUBLE(BlockRockSlab.Double::new, RAW, COBBLE, BRICK, SMOOTH),
	/**
	 * Полублок
	 */
	SLAB(BlockRockSlab.Half::new, RAW, COBBLE, BRICK, SMOOTH),
	/**
	 * Блок стены
	 */
	WALL(BlockRockWall::new, RAW, COBBLE, BRICK, SMOOTH);

	private final TriFunction<RockBlockType, RockVariant, RockType, IRockBlock> blockFactory;
	private final RockVariant[] rockVariants;

	RockBlockType(TriFunction<RockBlockType, RockVariant, RockType, IRockBlock> blockFactory, RockVariant... rockVariants) {
		this.rockVariants = rockVariants;
		this.blockFactory = blockFactory;
	}

	public IRockBlock createBlockType(RockVariant rockVariant, RockType stoneType) {
		if (this.blockFactory == null) {
			return rockVariant.createBlock(stoneType);
		}
		return this.blockFactory.apply(this, rockVariant, stoneType);
	}

	public RockVariant[] getRockVariants() {
		return rockVariants;
	}

	@Nonnull
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
