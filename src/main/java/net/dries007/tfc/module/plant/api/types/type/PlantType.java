package net.dries007.tfc.module.plant.api.types.type;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.plant.api.types.variant.block.PlantEnumVariant;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static net.dries007.tfc.module.plant.StoragePlant.PLANT_BLOCKS;
import static net.dries007.tfc.module.plant.api.types.variant.block.PlantEnumVariant.*;
import static net.dries007.tfc.world.classic.ChunkGenTFC.FRESH_WATER;
import static net.dries007.tfc.world.classic.ChunkGenTFC.SALT_WATER;

public class PlantType {

    // Список всех типов растений
    private static final Set<PlantType> PLANT_TYPES = new ObjectLinkedOpenHashSet<>();

    // Имя растения
    @Nonnull
    private final String name;

    // Массив стадий роста растения
    private final int[] stages;

    // Количество стадий роста растения
    private final int numStages;

    // Минимальная и максимальная температура для роста растения
    private final float minGrowthTemp;
    private final float maxGrowthTemp;

    // Минимальная и максимальная температура, при которой растение может существовать
    private final float minTemp;
    private final float maxTemp;

    // Минимальное и максимальное количество осадков для роста растения
    private final float minRain;
    private final float maxRain;

    // Минимальное и максимальное количество солнечного света для роста растения
    private final int minSun;
    private final int maxSun;

    // Максимальная высота для двойных+ растений этого типа
    private final int maxHeight;

    // Минимальная и максимальная глубина воды для роста растения
    private final int minWaterDepth;
    private final int maxWaterDepth;

    // Модификатор перемещения игрока по растению по X/Z
    private final double movementMod;

    // Вариант блока растения
    private final PlantEnumVariant plantBlockVariant;

    // Материал, связанный с растением
    private final Material material;

    // Помечает ли растение землю с глиной
    private final boolean isClayMarking;

    // Может ли растение появиться только в болотах
    private final boolean isSwampPlant;

    // Имя записи в словаре растений для растения
    private final Optional<String[]> oreDictName;

    /**
     * Конструктор класса PlantType.
     *
     * @param builder объект Builder для создания экземпляра PlantType
     */
    private PlantType(Builder builder) {
        this.name = builder.name;
        this.stages = builder.stages;
        this.minGrowthTemp = builder.minGrowthTemp;
        this.maxGrowthTemp = builder.maxGrowthTemp;
        this.minTemp = builder.minTemp;
        this.maxTemp = builder.maxTemp;
        this.minRain = builder.minRain;
        this.maxRain = builder.maxRain;
        this.minSun = builder.minSun;
        this.maxSun = builder.maxSun;
        this.maxHeight = builder.maxHeight;
        this.minWaterDepth = builder.minWaterDepth;
        this.maxWaterDepth = builder.maxWaterDepth;
        this.movementMod = builder.movementMod;

        this.plantBlockVariant = builder.plantBlockVariant;
        this.isClayMarking = builder.isClayMarking;
        this.isSwampPlant = builder.isSwampPlant;
        this.material = plantBlockVariant.getPlantMaterial();
        this.oreDictName = Optional.ofNullable(builder.oreDictName);

        HashSet<Integer> hashSet = new HashSet<>();
        for (int stage : stages) hashSet.add(stage);
        this.numStages = hashSet.size() <= 1 ? 1 : hashSet.size() - 1;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("Plant name must contain any character: [%s]", name));
        }

        if (!PLANT_TYPES.add(this)) {
            throw new RuntimeException(String.format("Plant: [%s] already exists!", name));
        }

        if (PLANT_BLOCKS.put(new Pair<>(this.getPlantVariant(), this), this.getPlantVariant().create(this)) != null)
            throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this.getPlantVariant(), this));
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
     * Получить количество стадий роста этого растения.
     *
     * @return количество стадий роста
     */
    public int getNumStages() {
        return numStages;
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
    public PlantEnumVariant getPlantVariant() {
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
    public Optional<String[]> getOreDictName() {
        return oreDictName;
    }

    /**
     * Проверяет, является ли данное местоположение подходящим для роста растения.
     *
     * @param temp     температура
     * @param rain     количество осадков
     * @param sunlight количество солнечного света
     * @return true, если местоположение подходит для роста растения, иначе false
     */
    public boolean isValidLocation(float temp, float rain, int sunlight) {
        return isValidTemp(temp) && isValidRain(rain) && isValidSunlight(sunlight);
    }

    /**
     * Проверяет, является ли данная температура подходящей для роста растения.
     *
     * @param temp температура
     * @return true, если температура подходит для роста растения, иначе false
     */
    public boolean isValidTemp(float temp) {
        return getTempValidity(temp) == PlantValidity.VALID;
    }

    /**
     * Проверяет, является ли данная температура подходящей для генерации мира.
     *
     * @param temp температура
     * @return true, если температура подходит для генерации мира, иначе false
     */
    public boolean isValidTempForWorldGen(float temp) {
        return Math.abs(temp - getAvgTemp()) < Float.sum(maxTemp, -minTemp) / 4f;
    }

    /**
     * Проверяет, является ли данное количество осадков подходящим для роста растения.
     *
     * @param rain количество осадков
     * @return true, если количество осадков подходит для роста растения, иначе false
     */
    public boolean isValidRain(float rain) {
        return getRainValidity(rain) == PlantValidity.VALID;
    }

    /**
     * Проверяет, является ли данное количество солнечного света подходящим для роста растения.
     *
     * @param sunlight количество солнечного света
     * @return true, если количество солнечного света подходит для роста растения, иначе false
     */
    public boolean isValidSunlight(int sunlight) {
        return minSun <= sunlight && maxSun >= sunlight;
    }

    /**
     * Проверяет, является ли данная глубина плавающей воды подходящей для роста растения.
     *
     * @param world мир
     * @param pos   позиция
     * @param water блок воды
     * @return true, если глубина плавающей воды подходит для роста растения, иначе false
     */
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

    /**
     * Возвращает подходящую глубину воды для роста растения.
     *
     * @param world мир
     * @param pos   позиция
     * @param water блок воды
     * @return глубина воды для роста растения, -1 если не подходит
     */
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

    /**
     * Возвращает стадию роста для указанного месяца.
     *
     * @param month месяц
     * @return стадия роста для указанного месяца
     */
    public int getStageForMonth(Month month) {
        return stages[month.ordinal()];
    }

    /**
     * Возвращает стадию роста для текущего месяца.
     *
     * @return стадия роста для текущего месяца
     */
    public int getStageForMonth() {
        return getStageForMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear());
    }

    /**
     * Проверяет, является ли данная температура подходящей для роста растения.
     *
     * @param temp температура
     * @return true, если температура подходит для роста растения, иначе false
     */
    public boolean isValidGrowthTemp(float temp) {
        return minGrowthTemp <= temp && maxGrowthTemp >= temp;
    }

    /**
     * Возвращает тип воды для растения.
     *
     * @return тип воды для растения
     */
    public IBlockState getWaterType() {
        if (plantBlockVariant == FLOATING_SEA || plantBlockVariant == WATER_SEA || plantBlockVariant == TALL_WATER_SEA || plantBlockVariant == EMERGENT_TALL_WATER_SEA) {
            return SALT_WATER;
        } else {
            return FRESH_WATER;
        }
    }

    /**
     * Возвращает возраст для генерации мира.
     *
     * @param rand случайное число
     * @param temp температура
     * @return возраст для генерации мира
     */
    public int getAgeForWorldgen(Random rand, float temp) {
        return rand.nextInt(Math.max(1, Math.min(Math.round(2.5f + ((temp - minGrowthTemp) / minGrowthTemp)), 4)));
    }

    /**
     * Проверяет, может ли растение быть посажено в горшок.
     *
     * @return true, если растение может быть посажено в горшок, иначе false
     */
    public boolean canBePotted() {
        return plantBlockVariant == STANDARD ||
                plantBlockVariant == CACTUS ||
                plantBlockVariant == CREEPING ||
                plantBlockVariant == TALL_PLANT ||
                plantBlockVariant == DRY ||
                plantBlockVariant == DESERT ||
                plantBlockVariant == MUSHROOM;
    }

    /**
     * Возвращает тип растения в виде перечисления EnumType.
     *
     * @return тип растения
     */
    public final EnumType getEnumPlantType() {
        switch (plantBlockVariant) {
            case DESERT, DESERT_TALL_PLANT -> {
                if (isClayMarking) return EnumType.DESERT_CLAY;
                else return EnumType.NONE;
            }
            case DRY, DRY_TALL_PLANT -> {
                if (isClayMarking) return EnumType.DRY_CLAY;
                else return EnumType.DRY;
            }
            case REED, TALL_REED -> {
                return EnumType.FRESH_BEACH;
            }
            case REED_SEA, TALL_REED_SEA -> {
                return EnumType.SALT_BEACH;
            }
            case WATER, TALL_WATER, EMERGENT_TALL_WATER -> {
                return EnumType.FRESH_WATER;
            }
            case WATER_SEA, TALL_WATER_SEA, EMERGENT_TALL_WATER_SEA -> {
                return EnumType.SALT_WATER;
            }
            default -> {
                if (isClayMarking) return EnumType.CLAY;
                else return EnumType.NONE;
            }
        }
    }

    /**
     * Возвращает валидность температуры для растения.
     *
     * @param temp температура
     * @return валидность температуры для растения
     */
    public PlantValidity getTempValidity(float temp) {
        if (temp < minTemp) {
            return PlantValidity.COLD;
        }
        if (temp > maxTemp) {
            return PlantValidity.HOT;
        }
        return PlantValidity.VALID;
    }

    /**
     * Возвращает валидность осадков для растения.
     *
     * @param rain количество осадков
     * @return валидность осадков для растения
     */
    public PlantValidity getRainValidity(float rain) {
        if (rain < minRain)
            return PlantValidity.DRY;
        if (rain > maxRain)
            return PlantValidity.WET;
        return PlantValidity.VALID;
    }

    /**
     * Возвращает среднюю температуру.
     *
     * @return средняя температура
     */
    private float getAvgTemp() {
        return Float.sum(minTemp, maxTemp) / 2f;
    }

    /**
     * Перечисление типов растений.
     */
    public enum EnumType {
        CLAY,
        DESERT_CLAY,
        DRY_CLAY,
        DRY,
        FRESH_BEACH,
        SALT_BEACH,
        FRESH_WATER,
        SALT_WATER,
        NONE
    }

    /**
     * Перечисление валидности растения.
     */
    public enum PlantValidity {
        COLD,
        HOT,
        DRY,
        WET,
        VALID
    }

    public static class Builder {
        private final String name;
        private final PlantEnumVariant plantBlockVariant;
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
        private String[] oreDictName;

        public Builder(@Nonnull String name, PlantEnumVariant plantBlockVariant) {
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
        public Builder setOreDictName(String... oreDictName) {
            this.oreDictName = oreDictName;
            return this;
        }

        public PlantType build() {
            return new PlantType(this);
        }
    }
}
