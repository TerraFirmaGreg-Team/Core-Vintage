package net.dries007.tfc.api.types.plant.type;

import net.dries007.tfc.api.types.plant.variant.PlantBlockVariant;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.*;

import static net.dries007.tfc.api.types.plant.variant.PlantBlockVariant.*;
import static net.dries007.tfc.world.classic.ChunkGenTFC.FRESH_WATER;
import static net.dries007.tfc.world.classic.ChunkGenTFC.SALT_WATER;

public class PlantType {

    private static final Set<PlantType> PLANT_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;
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

    private final PlantBlockVariant plantBlockVariant;
    private final Material material;
    private final boolean isClayMarking;
    private final boolean isSwampPlant;
    private final Optional<String> oreDictName;

    /**
     * Генерация растительного мира определяется динамически на основе действительных значений температуры и количества осадков.
     * <p>
     * Допустимыми средними температурами биомов являются те, которые попадают в диапазон
     * плюс-минус четверть полного диапазона температур растений
     * <p>
     * Пример: Лотос
     * Диапазон полной температуры: 10-50
     * Средняя температура: 30 ((10+50)/2)
     * Разница между максимальной и минимальной температурами: 40 (50-10)
     * Четверть этого диапазона: 10 (40/4)
     * Диапазон температуры при генерации мира: 20-40 (30 +- 10)
     *
     * @param name          имя этого растения
     * @param plantBlockVariant  тип растения
     * @param isClayMarking если это растение помечает землю с глиной
     * @param isSwampPlant  если это растение может появиться только в болотах
     * @param minGrowthTemp минимальная температура для роста
     * @param maxGrowthTemp максимальная температура для роста
     * @param minTemp       минимальная температура
     * @param maxTemp       максимальная температура
     * @param minRain       минимальные осадки
     * @param maxRain       максимальные осадки
     * @param minSun        минимальный уровень освещенности при генерации мира
     * @param maxSun        максимальный уровень освещенности при генерации мира
     * @param maxHeight     максимальная высота для двойных+ растений
     * @param minWaterDepth минимальное глубина воды для водных растений при генерации мира
     * @param maxWaterDepth максимальное глубина воды для водных растений при генерации мира
     * @param movementMod   модификатор для перемещения игрока по этому растению по X/Z
     * @param oreDictName   если не пустая, то запись в словаре растений для этого растения
     */
    public PlantType(@Nonnull String name, PlantBlockVariant plantBlockVariant, int[] stages, boolean isClayMarking,
                     boolean isSwampPlant, float minGrowthTemp, float maxGrowthTemp, float minTemp, float maxTemp,
                     float minRain, float maxRain, int minSun, int maxSun, int maxHeight, int minWaterDepth,
                     int maxWaterDepth, double movementMod, String oreDictName) {
        this.name = name;
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

        this.plantBlockVariant = plantBlockVariant;
        this.isClayMarking = isClayMarking;
        this.isSwampPlant = isSwampPlant;
        this.material = plantBlockVariant.getPlantMaterial();
        this.oreDictName = Optional.ofNullable(oreDictName);

        HashSet<Integer> hashSet = new HashSet<>();
        for (int stage : stages) {
            hashSet.add(stage);
        }
        this.numStages = hashSet.size() <= 1 ? 1 : hashSet.size() - 1;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("Plant name must contain any character: [%s]", name));
        }

        if (!PLANT_TYPES.add(this)) {
            throw new RuntimeException(String.format("Plant: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает список всех типов растений.
     *
     * @return Список всех типов растений.
     */
    public static Set<PlantType> getPlantTypes() {
        return PLANT_TYPES;
    }

    /**
     * Получить имя этого растения.
     *
     * @return имя растения
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Получить массив стадий роста этого растения.
     *
     * @return массив стадий роста
     */
    public int[] getStages() {
        return stages;
    }

    /**
     * Получить количество стадий роста этого растения.
     *
     * @return количество стадий роста
     */
    public int getNumStages() {
        return numStages;
    }

    /**
     * Получить минимальную температуру для роста этого растения.
     *
     * @return минимальная температура для роста
     */
    public float getMinGrowthTemp() {
        return minGrowthTemp;
    }

    /**
     * Получить максимальную температуру для роста этого растения.
     *
     * @return максимальная температура для роста
     */
    public float getMaxGrowthTemp() {
        return maxGrowthTemp;
    }

    /**
     * Получить минимальную температуру для этого растения.
     *
     * @return минимальная температура
     */
    public float getMinTemp() {
        return minTemp;
    }

    /**
     * Получить максимальную температуру для этого растения.
     *
     * @return максимальная температура
     */
    public float getMaxTemp() {
        return maxTemp;
    }

    /**
     * Получить минимальное количество осадков для этого растения.
     *
     * @return минимальное количество осадков
     */
    public float getMinRain() {
        return minRain;
    }

    /**
     * Получить максимальное количество осадков для этого растения.
     *
     * @return максимальное количество осадков
     */
    public float getMaxRain() {
        return maxRain;
    }

    /**
     * Получить минимальный уровень освещенности для этого растения.
     *
     * @return минимальный уровень освещенности
     */
    public int getMinSun() {
        return minSun;
    }

    /**
     * Получить максимальный уровень освещенности для этого растения.
     *
     * @return максимальный уровень освещенности
     */
    public int getMaxSun() {
        return maxSun;
    }

    /**
     * Получить максимальную высоту для двойных+ растений этого типа.
     *
     * @return максимальная высота
     */
    public int getMaxHeight() {
        return maxHeight;
    }

    /**
     * Получить минимальную глубину воды для этого растения.
     *
     * @return минимальная глубина воды
     */
    public int getMinWaterDepth() {
        return minWaterDepth;
    }

    /**
     * Получить максимальную глубину воды для этого растения.
     *
     * @return максимальная глубина воды
     */
    public int getMaxWaterDepth() {
        return maxWaterDepth;
    }

    /**
     * Получить модификатор перемещения игрока по этому растению по X/Z.
     *
     * @return модификатор перемещения
     */
    public double getMovementMod() {
        return movementMod;
    }

    /**
     * Получить тип растения этого растения.
     *
     * @return тип растения
     */
    public PlantBlockVariant getPlantVariant() {
        return plantBlockVariant;
    }

    /**
     * Получить материал, связанный с этим растением.
     *
     * @return материал растения
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Проверить, помечает ли это растение землю с глиной.
     *
     * @return true, если растение помечает землю с глиной, в противном случае - false
     */
    public boolean isClayMarking() {
        return isClayMarking;
    }

    /**
     * Проверить, может ли это растение появиться только в болотах.
     *
     * @return true, если растение может появиться только в болотах, в противном случае - false
     */
    public boolean isSwampPlant() {
        return isSwampPlant;
    }

    /**
     * Получить имя записи в словаре растений для этого растения.
     *
     * @return имя записи в словаре растений, если оно задано, иначе пустое значение
     */
    public Optional<String> getOreDictName() {
        return oreDictName;
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

    public int getStageForMonth(Month month) {
        return stages[month.ordinal()];
    }

    public int getStageForMonth() {
        return getStageForMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear());
    }

    public boolean isValidGrowthTemp(float temp) {
        return minGrowthTemp <= temp && maxGrowthTemp >= temp;
    }

    public IBlockState getWaterType() {
        if (plantBlockVariant == FLOATING_SEA || plantBlockVariant == WATER_SEA || plantBlockVariant == TALL_WATER_SEA || plantBlockVariant == EMERGENT_TALL_WATER_SEA) {
            return SALT_WATER;
        } else {
            return FRESH_WATER;
        }
    }

    public int getAgeForWorldgen(Random rand, float temp) {
        return rand.nextInt(Math.max(1, Math.min(Math.round(2.5f + ((temp - minGrowthTemp) / minGrowthTemp)), 4)));
    }

    public boolean canBePotted() {
        return plantBlockVariant == STANDARD ||
                plantBlockVariant == CACTUS ||
                plantBlockVariant == CREEPING ||
                plantBlockVariant == TALL_PLANT ||
                plantBlockVariant == DRY ||
                plantBlockVariant == DESERT ||
                plantBlockVariant == MUSHROOM;
    }

    public final EnumPlantType getEnumPlantType() {
        switch (plantBlockVariant) {
            case DESERT, DESERT_TALL_PLANT -> {
                if (isClayMarking) return EnumPlantType.DESERT_CLAY;
                else return EnumPlantType.NONE;
            }
            case DRY, DRY_TALL_PLANT -> {
                if (isClayMarking) return EnumPlantType.DRY_CLAY;
                else return EnumPlantType.DRY;
            }
            case REED, TALL_REED -> {
                return EnumPlantType.FRESH_BEACH;
            }
            case REED_SEA, TALL_REED_SEA -> {
                return EnumPlantType.SALT_BEACH;
            }
            case WATER, TALL_WATER, EMERGENT_TALL_WATER -> {
                return EnumPlantType.FRESH_WATER;
            }
            case WATER_SEA, TALL_WATER_SEA, EMERGENT_TALL_WATER_SEA -> {
                return EnumPlantType.SALT_WATER;
            }
            default -> {
                if (isClayMarking) return EnumPlantType.CLAY;
                else return EnumPlantType.NONE;
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

    public static class Builder {
        private final String name;
        private final PlantBlockVariant plantBlockVariant;
        private int[] stages;
        private float minGrowthTemp;
        private float maxGrowthTemp;
        private float minTemp;
        private float maxTemp;
        private float minRain;
        private float maxRain;
        private int minSun;
        private int maxSun;
        private int maxHeight;
        private int minWaterDepth;
        private int maxWaterDepth;
        private double movementMod;
        private boolean isClayMarking;
        private boolean isSwampPlant;
        private String oreDictName;

        // Конструктор
        public Builder(@Nonnull String name, PlantBlockVariant plantBlockVariant) {
            this.name = name;
            this.plantBlockVariant = plantBlockVariant;
            this.stages = new int[]{0};
            this.minGrowthTemp = 0;
            this.maxGrowthTemp = 0;
            this.minTemp = 0;
            this.maxTemp = 0;
            this.minRain = 0;
            this.maxRain = 0;
            this.minSun = 0;
            this.maxSun = 0;
            this.maxHeight = 1;
            this.minWaterDepth = 0;
            this.maxWaterDepth = 0;
            this.movementMod = 0D;

            this.isClayMarking = false;
            this.isSwampPlant = false;
            this.oreDictName = null;
        }


        // Установка стадий роста растения
        public Builder setStages(int[] stages) {
            this.stages = stages;
            return this;
        }

        // Установка флага, указывающего, помечает ли это растение глинистые земли
        public Builder setClayMarking() {
            this.isClayMarking = true;
            return this;
        }

        // Установка флага, указывающего, может ли это растение расти только в болоте
        public Builder setSwampPlant() {
            this.isSwampPlant = true;
            return this;
        }

        // Установка минимальной и максимальной температуры для роста
        public Builder setGrowthTemp(float minGrowthTemp, float maxGrowthTemp) {
            this.minGrowthTemp = minGrowthTemp;
            this.maxGrowthTemp = maxGrowthTemp;
            return this;
        }

        // Установка минимальной и максимальной температуры
        public Builder setTemp(float minTemp, float maxTemp) {
            this.minTemp = minTemp;
            this.maxTemp = maxTemp;
            return this;
        }

        // Установка минимального и максимального количества осадков
        public Builder setRain(float minRain, float maxRain) {
            this.minRain = minRain;
            this.maxRain = maxRain;
            return this;
        }

        // Установка минимального и максимального уровня освещения для генерации мира
        public Builder setSun(int minSun, int maxSun) {
            this.minSun = minSun;
            this.maxSun = maxSun;
            return this;
        }

        // Установка максимальной высоты для двойных растений
        public Builder setMaxHeight(int maxHeight) {
            this.maxHeight = maxHeight;
            return this;
        }

        // Установка минимальной и максимальной глубины воды для водных растений на генерации мира
        public Builder setWaterDepth(int minWaterDepth, int maxWaterDepth) {
            this.minWaterDepth = minWaterDepth;
            this.maxWaterDepth = maxWaterDepth;
            return this;
        }

        // Установка модификатора для перемещения игрока по этому растению по X/Z
        public Builder setMovementMod(double movementMod) {
            this.movementMod = movementMod;
            return this;
        }

        // Установка имени записи в словаре растений для этого растения
        public Builder setOreDictName(String oreDictName) {
            this.oreDictName = oreDictName;
            return this;
        }

        // Метод для создания объекта PlantType с использованием заданных значений
        public PlantType build() {
            return new PlantType(name, plantBlockVariant, stages, isClayMarking, isSwampPlant, minGrowthTemp, maxGrowthTemp,
                    minTemp, maxTemp, minRain, maxRain, minSun, maxSun, maxHeight, minWaterDepth, maxWaterDepth,
                    movementMod, oreDictName);
        }
    }

    public enum EnumPlantType implements IStringSerializable {
        CLAY,
        DESERT_CLAY,
        DRY_CLAY,
        DRY,
        FRESH_BEACH,
        SALT_BEACH,
        FRESH_WATER,
        SALT_WATER,
        NONE;

        /**
         * Возвращает имя перечисления в нижнем регистре.
         */
        @Nonnull
        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }

    public enum PlantValidity implements IStringSerializable {
        COLD,
        HOT,
        DRY,
        WET,
        VALID;

        /**
         * Возвращает имя перечисления в нижнем регистре.
         */
        @Nonnull
        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }
}
