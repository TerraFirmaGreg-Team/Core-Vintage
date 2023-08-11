package net.dries007.tfc.api.types.plant.type;

import net.dries007.tfc.api.types.plant.PlantVariant;
import net.minecraft.block.material.Material;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class PlantType {

    private static final Set<PlantType> PLANT_TYPES = new LinkedHashSet<>();

    private String name;
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
     * @param plantVariant  тип растения
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
    public PlantType(@Nonnull String name, PlantVariant plantVariant, int[] stages, boolean isClayMarking,
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
    }

    public static class Builder {
        private String name;
        private PlantVariant plantVariant;
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
        public Builder(@Nonnull String name, PlantVariant plantVariant) {
            this.name = name;
            this.plantVariant = plantVariant;
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
            return new PlantType(name, plantVariant, stages, isClayMarking, isSwampPlant, minGrowthTemp, maxGrowthTemp,
                    minTemp, maxTemp, minRain, maxRain, minSun, maxSun, maxHeight, minWaterDepth, maxWaterDepth,
                    movementMod, oreDictName);
        }
    }
}
