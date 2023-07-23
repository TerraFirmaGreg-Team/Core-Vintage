package net.dries007.tfc.api.types2;

import net.dries007.tfc.api.util.IStoneTypeBlock;
import net.dries007.tfc.api.util.TriFunction;
import net.dries007.tfc.objects.blocks.stone2.*;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

import static net.dries007.tfc.api.types2.BlockVariant.*;

/**
 * Тип блока, принимает первым параметром, класс,
 * который будет создаваться, вторым параметром варианты для которых он создаст класс
 */
@Nonnull
public enum BlockType implements IStringSerializable {
	/**
	 * Обычный блок без каких-либо доп. механик
	 */
	ORDINARY(BlockOrdinaryTFG::new, RAW, SMOOTH, COBBLE, BRICK),
	/**
	 * Обычный блок, но с механикой падения
	 */
	FALLABLE(BlockFallableTFG::new, GRAVEL),
	/**
	 * Обычный блок без каких-либо доп. механик
	 */
	CRACKED(BlockOrdinaryTFG::new, BRICK),
	/**
	 * Обычный блок без каких-либо доп. механик
	 */
	CHISELED(BlockOrdinaryTFG::new, BRICK),
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

	private final TriFunction<BlockType, BlockVariant, StoneType, IStoneTypeBlock> blockFactory;
	private final BlockVariant[] blockVariants;

	BlockType(TriFunction<BlockType, BlockVariant, StoneType, IStoneTypeBlock> blockFactory, BlockVariant... blockVariants) {
		this.blockVariants = blockVariants;
		this.blockFactory = blockFactory;
	}

	public IStoneTypeBlock createBlock(BlockVariant blockVariant, StoneType stoneType) {
		return this.blockFactory.apply(this, blockVariant, stoneType);
	}

	public BlockVariant[] getBlockVariants() {
		return blockVariants;
	}

	@Nonnull
	@Override
	public String getName() {
		return name().toLowerCase();
	}
}
