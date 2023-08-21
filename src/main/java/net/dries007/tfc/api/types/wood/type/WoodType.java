package net.dries007.tfc.api.types.wood.type;

import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.api.types.trees.ITreeGenerator;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static net.dries007.tfc.api.types.trees.TreeGenerators.*;

/**
 * Класс Wood представляет тип дерева с определенными характеристиками.
 */
public class WoodType {
    private static final Set<WoodType> WOOD_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    private final int color;
    private final int maxGrowthRadius;
    private final float dominance;
    private final int maxHeight;
    private final int maxDecayDistance;
    private final boolean isConifer;
    private final ITreeGenerator bushGenerator;
    private final boolean canMakeTannin;
    private final float minGrowthTime;
    private final float minTemp;
    private final float maxTemp;
    private final float minRain;
    private final float maxRain;
    private final float minDensity;
    private final float maxDensity;
    private final float burnTemp;
    private final int burnTicks;

    private final Month flowerMonthStart;
    private final int floweringMonths;
    private final Month harvestMonthStart;
    private final int harvestingMonths;
    private final FoodType fruit;
    private final float growthTime;
    /* Это открыто для замены, т.е. для динамических деревьев */
    private final ITreeGenerator generator;

    private WoodType(Builder builder) {
        this.name = builder.name;
        this.color = builder.color;

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
        this.burnTemp = builder.burnTemp;
        this.burnTicks = builder.burnTicks;
        this.generator = builder.generator;
        this.bushGenerator = builder.bushGenerator;
        this.canMakeTannin = builder.canMakeTannin;

        this.flowerMonthStart = builder.flowerMonthStart;
        this.floweringMonths = builder.floweringMonths;

        this.harvestMonthStart = builder.harvestMonthStart;
        this.harvestingMonths = builder.harvestingMonths;
        this.growthTime = builder.growthTime;
        this.fruit = builder.fruit;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("WoodType name must contain any character: [%s]", name));
        }

        if (!WOOD_TYPES.add(this)) {
            throw new RuntimeException(String.format("WoodType: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает список всех доступных типов дерева.
     *
     * @return список типов дерева
     */
    public static Set<WoodType> getWoodTypes() {
        return WOOD_TYPES;
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

    /**
     * Возвращает цвет дерева.
     *
     * @return цвет дерева
     */
    public int getColor() {
        return color;
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
     * Проверяет, может ли дерево производить дубильные вещества.
     *
     * @return true, если дерево может производить дубильные вещества, иначе false
     */
    public boolean canMakeTannin() {
        return canMakeTannin;
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
     * Возвращает температуру, при которой дерево будет гореть в костре или подобном устройстве.
     *
     * @return температура горения
     */
    public float getBurnTemp() {
        return burnTemp;
    }

    /**
     * Возвращает количество тиков, в течение которых дерево будет гореть в костре или подобном устройстве.
     *
     * @return количество тиков горения
     */
    public int getBurnTicks() {
        return burnTicks;
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
        return this.fruit;
    }

    public ItemStack getFoodDrop() {
        return new ItemStack(TFCBlocks.getFoodItem(this.getFruit()));
    }

    @SideOnly(Side.CLIENT)
    public void addInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
            tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) minRain, (int) maxRain));
            tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", minTemp), String.format("%.1f", maxTemp)));
        } else {
            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
        }
    }

    public static class Builder {
        private final String name;
        private float minGrowthTime;
        private int color;
        private float minTemp, maxTemp;
        private float minRain, maxRain;
        private ITreeGenerator generator;
        private float minDensity, maxDensity;
        private float dominance;
        private int maxHeight;
        private int maxGrowthRadius;
        private int maxDecayDistance;
        private boolean isConifer;
        private ITreeGenerator bushGenerator;
        private boolean canMakeTannin;
        private float burnTemp;
        private int burnTicks;
        private Month flowerMonthStart;
        private int floweringMonths;
        private Month harvestMonthStart;
        private int harvestingMonths;
        private FoodType fruit;
        private float growthTime;

        public Builder(@Nonnull String name) {
            this.name = name;
            this.color = 0xFFFFFF;
            this.minTemp = 0;
            this.maxTemp = 10;
            this.minRain = 0;
            this.maxRain = 100;
            this.generator = GEN_NORMAL; // Заменить на ген DT по умолчанию, и удалить setGenerator(), так как для кустов вызывается setBushes()
            this.maxGrowthRadius = 1;
            this.dominance = 0.001f * (maxTemp - minTemp) * (maxRain - minRain);
            this.maxHeight = 6;
            this.maxDecayDistance = 4;
            this.isConifer = false;
            this.bushGenerator = null;
            this.canMakeTannin = false;
            this.minGrowthTime = 7;
            this.minDensity = 0.1f;
            this.maxDensity = 2f;
            this.burnTemp = 675;
            this.burnTicks = 1500;

            this.fruit = null;
            this.flowerMonthStart = null;
            this.floweringMonths = 0;
            this.harvestMonthStart = null;
            this.harvestingMonths = 0;
            this.growthTime = 0;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
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

        // Установить генератор деревьев
        public Builder setGenerator(@Nonnull ITreeGenerator generator) {
            this.generator = generator;
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
            bushGenerator = GEN_BUSHES;
            return this;
        }

        // Установить генератор кустов
        public Builder setBushes(ITreeGenerator bushGenerator) {
            this.bushGenerator = bushGenerator;
            return this;
        }

        // Установить возможность производить танин
        public Builder setTannin() {
            canMakeTannin = true;
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

        // Установить температуру и количество тиков горения
        public Builder setBurnInfo(float burnTemp, int burnTicks) {
            this.burnTemp = burnTemp;
            this.burnTicks = burnTicks;
            return this;
        }

        public Builder setFruitTree(FoodType fruit, float growthTime) {
            this.fruit = fruit;
            this.growthTime = growthTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;
            this.generator = GEN_FRUIT;
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

        // Метод для построения объекта WoodType
        public WoodType build() {
            return new WoodType(this);
        }
    }
}
