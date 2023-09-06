package net.dries007.tfc.api.types.tree.type;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.cells.ICellKit;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKits;
import com.ferreusveritas.dynamictrees.growthlogic.IGrowthLogicKit;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.world.classic.worldgen.trees.ITreeGenerator;
import net.dries007.tfc.world.classic.worldgen.trees.TreeGenDT;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import static com.ferreusveritas.dynamictrees.ModConstants.MODID;

/**
 * Класс Wood представляет тип дерева с определенными характеристиками.
 */
public class TreeType {
    private static final Set<TreeType> TREE_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final WoodType wood;
    private final int maxGrowthRadius;
    private final float dominance;
    private final int maxHeight;
    private final int maxDecayDistance;
    private final boolean isConifer;
    private final ITreeGenerator bushGenerator;
    private final float minGrowthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;
    private final float minDensity;
    private final float maxDensity;

    private final Month flowerMonthStart;
    private final int floweringMonths;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final FoodType fruit;
    private final float growthTime;
    /* Это открыто для замены, т.е. для динамических деревьев */
    private final ITreeGenerator generator;
    private final float[] paramMap;
    private final IGrowthLogicKit logicMap;
    private final ICellKit cellKit;

    private TreeType(Builder builder) {
        this.name = builder.wood.toString();
        this.wood = builder.wood;

        this.minTemp = builder.minTemp;
        this.maxTemp = builder.maxTemp;
        this.minRain = builder.minRain;
        this.maxRain = builder.maxRain;

        this.minDensity = builder.minDensity;
        this.maxDensity = builder.maxDensity;

        this.dominance = builder.dominance;
        this.maxGrowthRadius = builder.maxGrowthRadius;
        this.maxHeight = builder.maxHeight;
        this.maxDecayDistance = builder.maxDecayDistance;
        this.isConifer = builder.isConifer;
        this.minGrowthTime = builder.minGrowthTime;
        this.generator = builder.generator;
        this.bushGenerator = builder.bushGenerator;

        this.flowerMonthStart = builder.flowerMonthStart;
        this.floweringMonths = builder.floweringMonths;

        this.harvestMonthStart = builder.harvestMonthStart;
        this.harvestingMonths = builder.harvestingMonths;
        this.growthTime = builder.growthTime;
        this.fruit = builder.fruit;

        this.paramMap = builder.paramMap;
        this.logicMap = builder.logicMap;
        this.cellKit = builder.cellKit;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("TreeType name must contain any character: [%s]", name));
        }

        if (!TREE_TYPES.add(this)) {
            throw new RuntimeException(String.format("TreeType: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает список всех доступных типов дерева.
     *
     * @return список типов дерева
     */
    public static Set<TreeType> getTreeTypes() {
        return TREE_TYPES;
    }

    /**
     * Возвращает строковое представление древесины.
     *
     * @return Строковое представление древесины.
     */
    @Override
    public String toString() {
        return name;
    }

    @Nonnull
    public WoodType getWood() {
        return wood;
    }

    /**
     * Возвращает минимальное количество осадков, необходимое для роста дерева.
     *
     * @return минимальное количество осадков
     */
    public float getMinRain() {
        return minRain;
    }

    /**
     * Возвращает максимальное количество осадков, необходимое для роста дерева.
     *
     * @return максимальное количество осадков
     */
    public float getMaxRain() {
        return maxRain;
    }

    /**
     * Возвращает насколько это дерево доминирует по сравнению с другими деревьями.
     *
     * @return значение доминирования
     */
    public float getDominance() {
        return dominance;
    }

    /**
     * Возвращает максимальный радиус роста дерева.
     *
     * @return максимальный радиус роста
     */
    public int getMaxGrowthRadius() {
        return maxGrowthRadius;
    }

    /**
     * Возвращает максимальную высоту дерева.
     *
     * @return максимальная высота
     */
    public int getMaxHeight() {
        return maxHeight;
    }

    /**
     * Возвращает максимальное расстояние распада для листьев дерева.
     *
     * @return максимальное расстояние распада
     */
    public int getMaxDecayDistance() {
        return maxDecayDistance;
    }

    /**
     * Проверяет, является ли данное дерево хвойным.
     *
     * @return true, если дерево хвойное, иначе false
     */
    public boolean isConifer() {
        return isConifer;
    }

    /**
     * Возвращает генератор для создания дерева.
     *
     * @return генератор дерева
     */
    public ITreeGenerator getGenerator() {
        return generator;
    }

    /**
     * Возвращает генератор для создания маленьких кустов дерева.
     *
     * @return генератор кустов дерева
     */
    public ITreeGenerator getBushGenerator() {
        return bushGenerator;
    }


    /**
     * Возвращает минимальное время роста дерева.
     *
     * @return минимальное время роста
     */
    public float getMinGrowthTime() {
        return minGrowthTime;
    }

    /**
     * Проверяет, является ли указанное местоположение допустимым.
     *
     * @param temp    температура местоположения
     * @param rain    количество осадков в местоположении
     * @param density плотность в местоположении
     * @return {@code true}, если местоположение допустимо, иначе {@code false}
     */
    public boolean isValidLocation(float temp, float rain, float density) {
        return minTemp <= temp && maxTemp >= temp && minRain <= rain && maxRain >= rain && minDensity <= density && maxDensity >= density;
    }

    public boolean isValidConditions(float temp, float rain) {
        return minTemp - 5 < temp && temp < maxTemp + 5 && minRain - 50 < rain && rain < maxRain + 50;
    }

    public boolean isValidForGrowth(float temp, float rain) {
        return minTemp < temp && temp < maxTemp && minRain < rain && rain < maxRain;
    }

    /**
     * Проверяет, есть ли кусты в местоположении.
     *
     * @return {@code true}, если есть кусты, иначе {@code false}
     */
    public boolean hasBushes() {
        return bushGenerator != null;
    }

    /**
     * Создает дерево с использованием менеджера шаблонов, мира, позиции, генератора случайных чисел и флага, указывающего, является ли это генерацией мира.
     *
     * @param manager    менеджер шаблонов для создания дерева
     * @param world      мир, в котором будет создано дерево
     * @param pos        позиция, где будет создано дерево
     * @param rand       генератор случайных чисел
     * @param isWorldGen флаг, указывающий, является ли это генерацией мира
     * @return {@code true}, если дерево было успешно создано, иначе {@code false}
     */
    public boolean makeTree(TemplateManager manager, World world, BlockPos pos, Random rand, boolean isWorldGen) {
        if (generator.canGenerateTree(world, pos, this)) {
            generator.generateTree(manager, world, pos, this, rand, isWorldGen);
            return true;
        }
        return false;
    }

    public boolean makeTree(World world, BlockPos pos, Random rand, boolean isWorldGen) {
        if (!world.isRemote) {
            return makeTree(((WorldServer) world).getStructureTemplateManager(), world, pos, rand, isWorldGen);
        }
        return false;
    }

    public float getGrowthTime() {
        return growthTime;
    }


    public boolean isFlowerMonth(Month month) {
        Month testing = this.flowerMonthStart;
        for (int i = 0; i < this.floweringMonths; i++) {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    public boolean isHarvestMonth(Month month) {
        Month testing = this.harvestMonthStart;
        for (int i = 0; i < this.harvestingMonths; i++) {
            if (testing.equals(month)) return true;
            testing = testing.next();
        }
        return false;
    }

    public FoodType getFruit() {
        return fruit;
    }

    public ItemStack getFoodDrop() {
        return new ItemStack(TFCItems.getFoodItem(fruit));
    }

    public float[] getParamMap() {
        return paramMap;
    }

    public IGrowthLogicKit getGrowthLogicKit() {
        return logicMap;
    }

    public ICellKit getCellKit() {
        return cellKit;
    }

    public static class Builder {
        private final WoodType wood;
        private final ITreeGenerator generator;
        private float minGrowthTime;
        private float minTemp, maxTemp;
        private float minRain, maxRain;
        private float minDensity, maxDensity;
        private float dominance;
        private int maxHeight;
        private int maxGrowthRadius;
        private int maxDecayDistance;
        private boolean isConifer;
        private ITreeGenerator bushGenerator;
        private Month flowerMonthStart;
        private int floweringMonths;
        private Month harvestMonthStart;
        private int harvestingMonths;
        private FoodType fruit;
        private float growthTime;
        private float[] paramMap;
        private IGrowthLogicKit logicMap;
        private ICellKit cellKit;

        public Builder(@Nonnull WoodType wood) {
            this.wood = wood;
            this.minTemp = 0;
            this.maxTemp = 10;
            this.minRain = 0;
            this.maxRain = 100;
            this.generator = new TreeGenDT();
            this.maxGrowthRadius = 1;
            this.dominance = 0.001f * (maxTemp - minTemp) * (maxRain - minRain);
            this.maxHeight = 6;
            this.maxDecayDistance = 4;
            this.isConifer = false;
            this.bushGenerator = null;

            this.minGrowthTime = 7;
            this.minDensity = 0.1f;
            this.maxDensity = 2f;
            this.fruit = null;
            this.flowerMonthStart = null;
            this.floweringMonths = 0;
            this.harvestMonthStart = null;
            this.harvestingMonths = 0;
            this.growthTime = 0;
            this.paramMap = new float[]{0.20f, 10f, 3, 3, 1.00f};
            this.logicMap = GrowthLogicKits.nullLogic;
            this.cellKit = null;
        }


        public Builder setTemp(float minTemp, float maxTemp) {
            this.minTemp = minTemp;
            this.maxTemp = maxTemp;
            return this;
        }

        public Builder setRain(float minRain, float maxRain) {
            this.minRain = minRain;
            this.maxRain = maxRain;
            return this;
        }

        // Установить радиус роста
        public Builder setRadius(int maxGrowthRadius) {
            this.maxGrowthRadius = maxGrowthRadius;
            return this;
        }

        // Установить доминирование
        public Builder setDominance(float dominance) {
            this.dominance = dominance;
            return this;
        }

        // Установить максимальную высоту
        public Builder setHeight(int maxHeight) {
            this.maxHeight = maxHeight;
            return this;
        }

        // Установить максимальное расстояние распада для листьев дерева.
        public Builder setDecayDist(int maxDecayDistance) {
            this.maxDecayDistance = maxDecayDistance;
            return this;
        }

        // Установить хвойное дерево
        public Builder setConifer() {
            isConifer = true;
            return this;
        }

        // Установить генератор кустов по умолчанию
        public Builder setBushes() {
            bushGenerator = null;//GEN_BUSHES; // TODO генератор для кустов
            return this;
        }

        // Установить время роста
        public Builder setMinGrowthTime(float minGrowthTime) {
            this.minGrowthTime = minGrowthTime;
            return this;
        }

        // Установить плотность
        public Builder setDensity(float min, float max) {
            this.minDensity = min;
            this.maxDensity = max;
            return this;
        }

        public Builder setFruitTree(FoodType fruit, float growthTime) {
            this.fruit = fruit;
            this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;
            //this.generator = GEN_FRUIT; // TODO генератор для фруктов GEN_FRUIT
            return this;
        }

        public Builder setFlowerMonth(Month flowerMonthStart, int floweringMonths) {
            this.flowerMonthStart = flowerMonthStart;
            this.floweringMonths = floweringMonths;
            return this;
        }

        public Builder setHarvestMonth(Month harvestMonthStart, int harvestingMonths) {
            this.harvestMonthStart = harvestMonthStart;
            this.harvestingMonths = harvestingMonths;
            return this;
        }

        public Builder setParamMap(float[] paramMap) {
            this.paramMap = paramMap;
            return this;
        }

        public Builder setGrowthLogicKit(String logicMap) {
            this.logicMap = TreeRegistry.findGrowthLogicKit(logicMap);
            return this;
        }

        public Builder setCellKit(String cellKit) {
            this.cellKit = TreeRegistry.findCellKit(new ResourceLocation(MODID, cellKit));
            return this;
        }

        // Метод для построения объекта WoodType
        public TreeType build() {
            return new TreeType(this);
        }
    }
}
