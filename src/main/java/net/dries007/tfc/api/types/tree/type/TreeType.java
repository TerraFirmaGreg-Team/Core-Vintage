package net.dries007.tfc.api.types.tree.type;

import com.ferreusveritas.dynamictrees.api.IGenFeature;
import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.api.cells.ICellKit;
import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKits;
import com.ferreusveritas.dynamictrees.growthlogic.IGrowthLogicKit;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.compat.dynamictrees.blocks.BlockTreeBranch;
import net.dries007.tfc.compat.dynamictrees.blocks.BlockTreeBranchThick;
import net.dries007.tfc.compat.dynamictrees.trees.WoodTreeSpecies;
import net.dries007.tfc.module.wood.common.WoodStorage;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.world.classic.worldgen.trees.ITreeGenerator;
import net.dries007.tfc.world.classic.worldgen.trees.TreeGenDT;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.TemplateManager;

import javax.annotation.Nonnull;
import java.util.*;

import static com.ferreusveritas.dynamictrees.ModConstants.MODID;
import static net.dries007.tfc.api.types.food.variant.Item.FoodItemVariants.INGREDIENT;
import static net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariants.LEAVES;
import static net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariants.LOG;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.BLOCKS;
import static net.dries007.tfc.common.objects.items.TFCItems.ITEMS;

/**
 * Класс Wood представляет тип дерева с определенными характеристиками.
 */
public class TreeType extends TreeFamily {
    private static final Set<TreeType> TREE_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final ResourceLocation name;
    @Nonnull
    private final WoodType wood;
    private final int maxGrowthRadius;
    private final float dominance;
    private final int maxHeight;
    private final int maxDecayDistance;
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
    public boolean hasConiferVariants;
    private boolean thick;
    private final int soilLongevity;
    private final List<IGenFeature> modules;

    private TreeType(Builder builder) {
        super(builder.name);
        this.name = builder.name;
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
        this.hasConiferVariants = builder.hasConiferVariants;
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
        this.modules = builder.modules;
        this.soilLongevity = builder.soilLongevity;
        this.thick = builder.thick;

        setPrimitiveLog(builder.primitiveLog);
        setDynamicBranch(builder.thick ? new BlockTreeBranchThick(wood) : new BlockTreeBranch(wood));
        setCommonSpecies(new WoodTreeSpecies(name, this, new LeavesProperties(builder.primitiveLeaves, builder.cellKit)));
        getRegisterableBlocks(BLOCKS);
        getRegisterableItems(ITEMS);

        if (name.getPath().isEmpty()) {
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
        return name.getPath();
    }

    @Nonnull
    public WoodType getWood() {
        return wood;
    }

    @Override
    public BlockBranch createBranch() {
        return isThick() ? new BlockTreeBranchThick(wood) : new BlockTreeBranch(wood);
    }

    public float[] getParamMap() {
        return paramMap;
    }

    public IGrowthLogicKit getGrowthLogicKit() {
        return logicMap;
    }

    @Override
    public ItemStack getPrimitiveLogItemStack(int qty) {
        var stack = new ItemStack(WoodStorage.getWoodBlock(LOG, wood), qty);
        stack.setCount(MathHelper.clamp(qty, 0, 64));
        return stack;
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
        return hasConiferVariants;
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
     * Создает дерево с использованием менеджера шаблонов, мира, позиции, генератора случайных чисел и флага,
     * указывающего, является ли это генерацией мира.
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
        return new ItemStack(TFCItems.getFoodItem(INGREDIENT, fruit));
    }

    public List<IGenFeature> getModule() {
        return modules;
    }

    public int getSoilLongevity() {
        return soilLongevity;
    }

    @Override
    public boolean isThick() {
        return thick;
    }


    public static class Builder {
        private final ITreeGenerator generator = new TreeGenDT();
        private ResourceLocation name;
        private WoodType wood;
        private float minGrowthTime;
        private float minTemp, maxTemp;
        private float minRain, maxRain;
        private float minDensity, maxDensity;
        private float dominance;
        private int maxHeight;
        private int maxGrowthRadius;
        private int maxDecayDistance;
        private boolean hasConiferVariants = false;
        private ITreeGenerator bushGenerator;
        private Month flowerMonthStart;
        private int floweringMonths;
        private Month harvestMonthStart;
        private int harvestingMonths;
        private FoodType fruit;
        private float growthTime;
        private float[] paramMap;
        private IGrowthLogicKit logicMap = GrowthLogicKits.nullLogic;
        private ICellKit cellKit;
        private IBlockState primitiveLeaves;
        private IBlockState primitiveLog;
        private ItemStack stickItemStack;
        private boolean thick = false;
        private List<IGenFeature> modules = new ArrayList<>();
        private int soilLongevity = 8;

        public Builder setName(ResourceLocation name) {
            this.name = name;
            return this;
        }

        public Builder setName(String path) {
            return setName(TerraFirmaCraft.identifier(path));
        }

        public Builder setWoodType(WoodType wood) {
            setName(wood.toString());

            this.wood = wood;
            this.primitiveLeaves = WoodStorage.getWoodBlock(LEAVES, wood).getDefaultState();
            this.primitiveLog = WoodStorage.getWoodBlock(LOG, wood).getDefaultState();
            return this;
        }

        public Builder setStick(ItemStack stick) {
            this.stickItemStack = stick;
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
            hasConiferVariants = true;
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

        public Builder setParamMap(float tapering, float energy, int upProbability, int lowestBranchHeight, float growthRate) {
            this.paramMap = new float[]{tapering, energy, upProbability, lowestBranchHeight, growthRate};
            return this;
        }

        public Builder setGrowthLogicKit(String logicMap) {
            this.logicMap = TreeRegistry.findGrowthLogicKit(logicMap);
            return this;
        }

        public Builder setCellKit(ResourceLocation kit) {
            this.cellKit = TreeRegistry.findCellKit(kit);
            return this;
        }

        public Builder setCellKit(String modid, String kit) {
            setCellKit(new ResourceLocation(modid, kit));
            return this;
        }

        public Builder setCellKit(String kit) {
            setCellKit(new ResourceLocation(MODID, kit));
            return this;
        }

        public Builder setThick() {
            this.thick = true;
            return this;
        }

        public Builder setGenFeature(IGenFeature module) {
            modules.add(module);
            return this;
        }

        public Builder setSoilLongevity(int longevity) {
            this.soilLongevity = longevity;
            return this;
        }

        // Метод для построения объекта WoodType
        public TreeType build() {
            return new TreeType(this);
        }
    }
}
