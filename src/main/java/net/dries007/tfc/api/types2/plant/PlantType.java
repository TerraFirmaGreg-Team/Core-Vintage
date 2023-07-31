package net.dries007.tfc.api.types2.plant;

import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

import static net.dries007.tfc.api.types2.plant.PlantVariant.*;
import static net.dries007.tfc.world.classic.ChunkGenTFC.FRESH_WATER;
import static net.dries007.tfc.world.classic.ChunkGenTFC.SALT_WATER;

public enum PlantType implements IStringSerializable {
	ALLIUM(STANDARD, new int[]{6, 6, 7, 0, 1, 1, 2, 2, 3, 4, 5, 6}, false, false, 8f, 20f, -40f, 33f, 150f, 500f, 12, 15, 1, 0.8D, null),
	ATHYRIUM_FERN(STANDARD, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 13f, 25f, -35f, 31f, 200f, 500f, 9, 15, 1, 0.8D, null),
	BARREL_CACTUS(CACTUS, new int[]{0, 0, 0, 0, 1, 2, 2, 2, 2, 3, 3, 0}, false, false, 18f, 40f, -6f, 50f, 0f, 75f, 12, 15, 3, 0D, "blockCactus"),
	BLACK_ORCHID(STANDARD, new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2}, false, false, 20f, 35f, 10f, 50f, 300f, 500f, 12, 15, 1, 0.8D, null),
	BLOOD_LILY(STANDARD, new int[]{3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2}, false, false, 18f, 30f, 10f, 50f, 200f, 500f, 9, 15, 1, 0.9D, null),
	BLUE_ORCHID(STANDARD, new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2}, false, false, 20f, 35f, 10f, 50f, 300f, 500f, 12, 15, 1, 0.9D, null),
	BUTTERFLY_MILKWEED(STANDARD, new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5}, false, false, 18f, 24f, -40f, 32f, 75f, 300f, 12, 15, 1, 0.8D, null),
	CALENDULA(STANDARD, new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5}, false, false, 15f, 20f, -46f, 30f, 130f, 300f, 9, 15, 1, 0.8D, null),
	CANNA(STANDARD, new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0}, true, false, 18f, 30f, -12f, 36f, 150f, 500f, 9, 15, 1, 0.8D, null),
	DANDELION(STANDARD, new int[]{9, 9, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8}, false, false, 10f, 25f, -40f, 40f, 75f, 400f, 10, 15, 1, 0.9D, null),
	DUCKWEED(FLOATING, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 11f, 20f, -34f, 38f, 0f, 500f, 4, 15, 1, 2, 32, 0.8D, null),
	FIELD_HORSETAIL(STANDARD, new int[]{1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1}, false, false, 5f, 20f, -40f, 33f, 300f, 500f, 9, 15, 1, 0.7D, "reed"),
	FOUNTAIN_GRASS(SHORT_GRASS, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 35f, -12f, 40f, 75f, 150f, 12, 15, 1, 0.8D, null),
	FOXGLOVE(TALL_PLANT, new int[]{0, 0, 0, 0, 0, 1, 1, 2, 3, 3, 3, 4}, false, false, 15f, 25f, -34f, 34f, 150f, 300f, 9, 15, 1, 0.8D, null),
	GOLDENROD(STANDARD, new int[]{4, 4, 4, 0, 0, 0, 1, 2, 2, 2, 2, 3}, true, false, 15f, 23f, -29f, 32f, 75f, 300f, 9, 15, 1, 0.6D, null),
	GRAPE_HYACINTH(STANDARD, new int[]{3, 3, 3, 0, 1, 1, 2, 3, 3, 3, 3, 3}, false, false, 4f, 18f, -34f, 32f, 150f, 250f, 9, 15, 1, 0.8D, null),
	GUZMANIA(EPIPHYTE, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 0f, 40f, 15f, 50f, 300f, 500f, 4, 11, 1, 0.9D, null),
	HOUSTONIA(STANDARD, new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2}, false, false, 15f, 30f, -46f, 36f, 150f, 500f, 9, 15, 1, 0.9D, null),
	LABRADOR_TEA(STANDARD, new int[]{0, 0, 1, 2, 3, 4, 4, 5, 6, 0, 0, 0}, false, false, 1f, 25f, -50f, 33f, 300f, 500f, 10, 15, 1, 0.8D, null),
	LADY_FERN(STANDARD, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 25f, -34f, 32f, 200f, 500f, 9, 11, 1, 0.6D, null),
	LICORICE_FERN(EPIPHYTE, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 5f, 18f, -29f, 25f, 300f, 500f, 4, 11, 1, 0.7D, null),
	LOTUS(FLOATING, new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 0, 0}, false, false, 20f, 40f, 10f, 50f, 0f, 500f, 4, 15, 1, 1, 1, 0.9D, null),
	MEADS_MILKWEED(STANDARD, new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5}, false, false, 13f, 25f, -23f, 31f, 130f, 500f, 12, 15, 1, 0.8D, null),
	MORNING_GLORY(CREEPING, new int[]{2, 2, 2, 0, 0, 1, 1, 1, 1, 1, 2, 2}, false, false, 15f, 30f, -40f, 31f, 150f, 500f, 12, 15, 1, 0.9D, null),
	MOSS(CREEPING, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, -2f, 15f, -7f, 36f, 250f, 500f, 0, 11, 1, 0.7D, null),
	NASTURTIUM(STANDARD, new int[]{4, 4, 4, 0, 1, 2, 2, 2, 2, 2, 3, 3}, false, false, 18f, 30f, -46f, 38f, 150f, 500f, 12, 15, 1, 0.8D, null),
	ORCHARD_GRASS(SHORT_GRASS, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 20f, -29f, 30f, 75f, 300f, 9, 15, 1, 0.8D, null),
	OSTRICH_FERN(TALL_PLANT, new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 4, 0}, false, false, 10f, 18f, -40f, 33f, 300f, 500f, 4, 11, 2, 0.6D, null),
	OXEYE_DAISY(STANDARD, new int[]{5, 5, 5, 0, 1, 2, 3, 3, 3, 4, 4, 5}, false, false, 18f, 30f, -40f, 33f, 120f, 300f, 9, 15, 1, 0.9D, null),
	PAMPAS_GRASS(TALL_GRASS, new int[]{1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1}, true, false, 20f, 30f, -12f, 36f, 75f, 200f, 9, 15, 3, 0.6D, null),
	PEROVSKIA(DRY, new int[]{5, 5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4}, true, false, 18f, 35f, -29f, 32f, 0f, 200f, 9, 15, 1, 0.8D, null),
	PISTIA(FLOATING, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 0f, 500f, 4, 15, 1, 2, 32, 0.8D, null),
	POPPY(STANDARD, new int[]{4, 4, 4, 0, 1, 2, 2, 3, 3, 3, 3, 4}, false, false, 17f, 30f, -40f, 36f, 150f, 250f, 12, 15, 1, 0.9D, null),
	PORCINI(MUSHROOM, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 13f, 20f, 0f, 30f, 300f, 500f, 0, 12, 1, 0.8D, "mushroomBrown"),
	PRIMROSE(STANDARD, new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2}, false, false, 15f, 25f, -34f, 33f, 150f, 300f, 9, 11, 1, 0.9D, null),
	PULSATILLA(STANDARD, new int[]{0, 1, 2, 3, 3, 4, 5, 5, 5, 0, 0, 0}, false, false, 1f, 25f, -50f, 30f, 50f, 200f, 12, 15, 1, 0.8D, null),
	REINDEER_LICHEN(CREEPING, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 1f, 25f, -50f, 33f, 50f, 500f, 9, 15, 1, 0.7D, null),
	ROSE(TALL_PLANT, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, true, false, 11f, 21f, -29f, 34f, 150f, 300f, 9, 15, 2, 0.9D, null),
	ROUGH_HORSETAIL(REED, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 5f, 20f, -40f, 33f, 200f, 500f, 9, 15, 1, 0.7D, "reed"),
	RYEGRASS(SHORT_GRASS, new int[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0}, false, false, 10f, 18f, -46f, 32f, 150f, 300f, 12, 15, 1, 0.8D, null),
	SACRED_DATURA(STANDARD, new int[]{3, 3, 3, 0, 1, 2, 2, 2, 2, 2, 2, 2}, false, false, 20f, 30f, 5f, 33f, 75f, 150f, 12, 15, 1, 0.8D, null),
	SAGEBRUSH(DRY, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0}, false, false, 18f, 35f, -34f, 50f, 0f, 100f, 12, 15, 1, 0.5D, null),
	SAPPHIRE_TOWER(TALL_PLANT, new int[]{2, 3, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2}, false, false, 21f, 31f, -6f, 38f, 75f, 200f, 9, 15, 2, 0.6D, null),
	SARGASSUM(FLOATING_SEA, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 30f, 0f, 38f, 0f, 500f, 12, 15, 1, 5, 256, 0.9D, "seaweed"),
	SCUTCH_GRASS(SHORT_GRASS, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 35f, -17f, 50f, 150f, 500f, 12, 15, 1, 0.7D, null),
	SNAPDRAGON_PINK(STANDARD, new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5}, false, false, 15f, 25f, -28f, 36f, 150f, 300f, 12, 15, 1, 0.8D, null),
	SNAPDRAGON_RED(STANDARD, new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5}, false, false, 15f, 25f, -28f, 36f, 150f, 300f, 12, 15, 1, 0.8D, null),
	SNAPDRAGON_WHITE(STANDARD, new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5}, false, false, 15f, 25f, -28f, 36f, 150f, 300f, 12, 15, 1, 0.8D, null),
	SNAPDRAGON_YELLOW(STANDARD, new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5}, false, false, 15f, 25f, -28f, 36f, 150f, 300f, 12, 15, 1, 0.8D, null),
	SPANISH_MOSS(HANGING, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, true, 20f, 40f, 0f, 40f, 300f, 500f, 4, 15, 3, 0.7D, null),
	STRELITZIA(STANDARD, new int[]{0, 0, 1, 1, 2, 2, 0, 0, 1, 1, 2, 2}, false, false, 20f, 40f, 5f, 50f, 50f, 300f, 12, 15, 1, 0.8D, null),
	SWITCHGRASS(TALL_GRASS, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0}, false, false, 13f, 25f, -29f, 32f, 100f, 300f, 9, 15, 2, 0.7D, null),
	SWORD_FERN(STANDARD, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 18f, 25f, -40f, 30f, 100f, 500f, 4, 11, 1, 0.7D, null),
	TALL_FESCUE_GRASS(TALL_GRASS, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 25f, -29f, 30f, 300f, 500f, 12, 15, 2, 0.5D, null),
	TIMOTHY_GRASS(SHORT_GRASS, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 15f, 25f, -46f, 30f, 300f, 500f, 12, 15, 1, 0.8D, null),
	TOQUILLA_PALM(TALL_PLANT, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 250f, 500f, 9, 15, 2, 0.4D, null),
	TREE_FERN(TALL_PLANT, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, false, false, 20f, 40f, 10f, 50f, 300f, 500f, 9, 15, 4, 0D, null),
	TRILLIUM(STANDARD, new int[]{5, 5, 5, 0, 1, 2, 3, 3, 4, 4, 4, 4}, false, false, 15f, 25f, -34f, 33f, 150f, 300f, 4, 11, 1, 0.8D, null),
	TROPICAL_MILKWEED(STANDARD, new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 3, 0}, false, false, 20f, 35f, -6f, 36f, 120f, 300f, 12, 15, 1, 0.8D, null),
	TULIP_ORANGE(STANDARD, new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4}, false, false, 15f, 25f, -34f, 33f, 100f, 200f, 9, 15, 1, 0.9D, null),
	TULIP_PINK(STANDARD, new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4}, false, false, 15f, 25f, -34f, 33f, 100f, 200f, 9, 15, 1, 0.9D, null),
	TULIP_RED(STANDARD, new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4}, false, false, 15f, 25f, -34f, 33f, 100f, 200f, 9, 15, 1, 0.9D, null),
	TULIP_WHITE(STANDARD, new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4}, false, false, 15f, 25f, -34f, 33f, 100f, 200f, 9, 15, 1, 0.9D, null),
	VRIESEA(EPIPHYTE, new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0}, false, false, 0f, 40f, 15f, 50f, 300f, 500f, 4, 11, 1, 0.8D, null),
	WATER_CANNA(FLOATING, new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0}, true, false, 18f, 30f, -12f, 36f, 150f, 500f, 9, 15, 1, 1, 1, 0.8D, null),
	WATER_LILY(FLOATING, new int[]{5, 5, 6, 0, 1, 2, 2, 2, 2, 3, 4, 5}, false, false, 15f, 30f, -34f, 38f, 0f, 500f, 4, 15, 1, 1, 1, 0.8D, null),
	YUCCA(DESERT, new int[]{0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 3}, false, false, 20f, 30f, -34f, 36f, 0f, 75f, 9, 15, 1, 0.8D, null);

	private final int[] stages;
	private final int numStages;
	private final float minGrowthTemp;
	private final float maxGrowthTemp;
	private final float minTemp;
	private final float maxTemp;
	private final float minRain;
	private final float maxRain;
	private final int minSun;
	private final int maxSun;
	private final int maxHeight;
	private final int minWaterDepth;
	private final int maxWaterDepth;
	private final double movementMod;

	private final PlantVariant plantVariant;
	private final Material material;
	private final boolean isClayMarking;
	private final boolean isSwampPlant;
	private final Optional<String> oreDictName;

	/**
	 * Тип растения определяется динамически на основе допустимых температур и осадков
	 * <p>
	 * Допустимые средние температуры биомов находятся в диапазоне,
	 * который включает в себя
	 * или минимальное значение, или максимальное значение,
	 * или минимальное и максимальное значение
	 * <p>
	 * Пример: Лотос
	 * Диапазон полной температуры: 10-50
	 * Средняя температура: 30 ((10+50)/2)
	 * Разница между максимальной и минимальной температурами: 40 (50-10)
	 * Половина этого диапазона: 10 (40/4)
	 * Диапазон температуры для генерации мира: 20-40 (30 +- 10)
	 *
	 * @param plantVariant  тип растения
	 * @param isClayMarking если это растение помечает клейные земли
	 * @param isSwampPlant  если это растение может только в почве
	 * @param minGrowthTemp минимальная температура роста
	 * @param maxGrowthTemp максимальная температура роста
	 * @param minTemp       минимальная температура
	 * @param maxTemp       максимальная температура
	 * @param minRain       минимальный осадок
	 * @param maxRain       максимальный осадок
	 * @param minSun        минимальный уровень освещения на генерации мира
	 * @param maxSun        максимальный уровень освещения на генерации мира
	 * @param maxHeight     максимальная высота для двойных растений
	 * @param minWaterDepth минимальное глубина воды для водных растений на генерации мира
	 * @param maxWaterDepth максимальное глубина воды для водных растений на генерации мира
	 * @param movementMod   модификатор для перемещения игрока по этому растению по X/Z
	 * @param oreDictName   если не пустая, то запись в словаре растений для этого растения
	 */


	PlantType(PlantVariant plantVariant, int[] stages, boolean isClayMarking, boolean isSwampPlant, float minGrowthTemp, float maxGrowthTemp, float minTemp, float maxTemp, float minRain, float maxRain, int minSun, int maxSun, int maxHeight, int minWaterDepth, int maxWaterDepth, double movementMod, String oreDictName) {
		this.stages = stages;
		this.minGrowthTemp = minGrowthTemp;
		this.maxGrowthTemp = maxGrowthTemp;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		this.minRain = minRain;
		this.maxRain = maxRain;
		this.minSun = minSun;
		this.maxSun = maxSun;
		this.maxHeight = maxHeight;
		this.minWaterDepth = minWaterDepth;
		this.maxWaterDepth = maxWaterDepth;
		this.movementMod = movementMod;

		this.plantVariant = plantVariant;
		this.isClayMarking = isClayMarking;
		this.isSwampPlant = isSwampPlant;
		this.material = plantVariant.getPlantMaterial();
		this.oreDictName = Optional.ofNullable(oreDictName);

		HashSet<Integer> hashSet = new HashSet<>();
		for (int stage : stages) {
			hashSet.add(stage);
		}
		this.numStages = hashSet.size() <= 1 ? 1 : hashSet.size() - 1;

//		setRegistryName(name);
	}

	PlantType(PlantVariant plantVariant, int[] stages, boolean isClayMarking, boolean isSwampPlantType, float minGrowthTemp, float maxGrowthTemp, float minTemp, float maxTemp, float minRain, float maxRain, int minSun, int maxSun, int maxHeight, double movementMod, String oreDictName) {
		this(plantVariant, stages, isClayMarking, isSwampPlantType, minGrowthTemp, maxGrowthTemp, minTemp, maxTemp, minRain, maxRain, minSun, maxSun, maxHeight, 0, 0, movementMod, oreDictName);
	}

	public double getMovementMod() {
		return movementMod;
	}

	public boolean getIsClayMarking() {
		return isClayMarking;
	}

	public boolean getIsSwampPlant() {
		return isSwampPlant;
	}

	public boolean isValidLocation(float temp, float rain, int sunlight) {
		return isValidTemp(temp) && isValidRain(rain) && isValidSunlight(sunlight);
	}

	public boolean isValidTemp(float temp) {
		return getTempValidity(temp) == PlantValidity.VALID;
	}

	public boolean isValidTempForWorldGen(float temp) {
		return Math.abs(temp - getAvgTemp()) < Float.sum(maxTemp, -minTemp) / 4f;
	}

	public boolean isValidRain(float rain) {
		return getRainValidity(rain) == PlantValidity.VALID;
	}

	public boolean isValidSunlight(int sunlight) {
		return minSun <= sunlight && maxSun >= sunlight;
	}

	public boolean isValidFloatingWaterDepth(World world, BlockPos pos, IBlockState water) {
		int depthCounter = minWaterDepth;
		int maxDepth = maxWaterDepth;

		for (int i = 1; i <= depthCounter; ++i) {
			if (world.getBlockState(pos.down(i)) != water && world.getBlockState(pos.down(i)).getMaterial() != Material.CORAL)
				return false;
		}

		while (world.getBlockState(pos.down(depthCounter)) == water || world.getBlockState(pos.down(depthCounter)).getMaterial() == Material.CORAL) {
			depthCounter++;
		}
		return (maxDepth > 0) && depthCounter <= maxDepth + 1;
	}

	public int getValidWaterDepth(World world, BlockPos pos, IBlockState water) {
		int depthCounter = minWaterDepth;
		int maxDepth = maxWaterDepth;

		if (depthCounter == 0 || maxDepth == 0) return -1;

		for (int i = 1; i <= depthCounter; ++i) {
			if (world.getBlockState(pos.down(i)) != water) return -1;
		}

		while (world.getBlockState(pos.down(depthCounter)) == water) {
			depthCounter++;
			if (depthCounter > maxDepth + 1) return -1;
		}
		return depthCounter;
	}

	@SuppressWarnings("unused")
	public float getMinGrowthTemp() {
		return minGrowthTemp;
	}

	@SuppressWarnings("unused")
	public float getMaxGrowthTemp() {
		return maxGrowthTemp;
	}

	public int getStageForMonth(Month month) {
		return stages[month.ordinal()];
	}

	public int getStageForMonth() {
		return getStageForMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear());
	}

	public int getNumStages() {
		return numStages;
	}

	public boolean isValidGrowthTemp(float temp) {
		return minGrowthTemp <= temp && maxGrowthTemp >= temp;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public Optional<String> getOreDictName() {
		return oreDictName;
	}

	@Nonnull
	public PlantVariant getPlantType() {
		return plantVariant;
	}

	@Nonnull
	public Material getMaterial() {
		return material;
	}

	public IBlockState getWaterType() {
		if (plantVariant == FLOATING_SEA || plantVariant == WATER_SEA || plantVariant == TALL_WATER_SEA || plantVariant == EMERGENT_TALL_WATER_SEA) {
			return SALT_WATER;
		} else {
			return FRESH_WATER;
		}
	}

	public int getAgeForWorldgen(Random rand, float temp) {
		return rand.nextInt(Math.max(1, Math.min(Math.round(2.5f + ((temp - minGrowthTemp) / minGrowthTemp)), 4)));
	}

	public boolean canBePotted() {
		return plantVariant == STANDARD ||
				plantVariant == CACTUS ||
				plantVariant == CREEPING ||
				plantVariant == TALL_PLANT ||
				plantVariant == DRY ||
				plantVariant == DESERT ||
				plantVariant == MUSHROOM;
	}

	public final EnumPlantTypeTFC getEnumPlantTypeTFC() {
		switch (plantVariant) {
			case DESERT, DESERT_TALL_PLANT -> {
				if (isClayMarking) return EnumPlantTypeTFC.DESERT_CLAY;
				else return EnumPlantTypeTFC.NONE;
			}
			case DRY, DRY_TALL_PLANT -> {
				if (isClayMarking) return EnumPlantTypeTFC.DRY_CLAY;
				else return EnumPlantTypeTFC.DRY;
			}
			case REED, TALL_REED -> {
				return EnumPlantTypeTFC.FRESH_BEACH;
			}
			case REED_SEA, TALL_REED_SEA -> {
				return EnumPlantTypeTFC.SALT_BEACH;
			}
			case WATER, TALL_WATER, EMERGENT_TALL_WATER -> {
				return EnumPlantTypeTFC.FRESH_WATER;
			}
			case WATER_SEA, TALL_WATER_SEA, EMERGENT_TALL_WATER_SEA -> {
				return EnumPlantTypeTFC.SALT_WATER;
			}
			default -> {
				if (isClayMarking) return EnumPlantTypeTFC.CLAY;
				else return EnumPlantTypeTFC.NONE;
			}
		}
	}

	public PlantValidity getTempValidity(float temp) {
		if (temp < minTemp) {
			return PlantValidity.COLD;
		}
		if (temp > maxTemp) {
			return PlantValidity.HOT;
		}
		return PlantValidity.VALID;
	}

	public PlantValidity getRainValidity(float rain) {
		if (rain < minRain)
			return PlantValidity.DRY;
		if (rain > maxRain)
			return PlantValidity.WET;
		return PlantValidity.VALID;
	}

	private float getAvgTemp() {
		return Float.sum(minTemp, maxTemp) / 2f;
	}

	/**
	 * Возвращает имя перечисления в нижнем регистре.
	 */
	@Override
	public String getName() {
		return name().toLowerCase();
	}


}
