//package su.terrafirmagreg.modules.wood.api.types.variant.tree;
//
//import net.minecraft.client.gui.GuiScreen;
//import net.minecraft.client.resources.I18n;
//import net.minecraft.client.util.ITooltipFlag;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.ResourceLocation;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.text.TextFormatting;
//import net.minecraft.world.World;
//import net.minecraft.world.WorldServer;
//import net.minecraft.world.gen.structure.template.TemplateManager;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//
//import lombok.Getter;
//import net.dries007.tfc.types.DefaultTrees;
//import net.dries007.tfc.util.calendar.CalendarTFC;
//import net.dries007.tfc.util.calendar.ICalendar;
//import net.dries007.tfc.util.calendar.Month;
//import org.jetbrains.annotations.NotNull;
//import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
//
//import java.util.*;
//import java.util.function.Supplier;
//
//
///**
// * Класс Wood представляет тип дерева с определенными характеристиками.
// */
//
//public class WoodTreeVariant {
//
//    private static final Set<WoodTreeVariant> WOOD_TREE_VARIANTS = new LinkedHashSet<>();
//
//    @NotNull
//    private final String name;
//    @NotNull
//    @Getter
//    private final WoodType woodType;
//    @Getter
//    private final int maxGrowthRadius;
//    @Getter
//    private final float dominance;
//    @Getter
//    private final int maxHeight;
//    @Getter
//    private final int maxDecayDistance;
//    @Getter
//    private final ITreeGen bushGenerator;
//    @Getter
//    private final float minGrowthTime;
//    @Getter
//    private final float minTemp;
//    @Getter
//    private final float maxTemp;
//    @Getter
//    private final float minRain;
//    @Getter
//    private final float maxRain;
//    @Getter
//    private final float minDensity;
//    @Getter
//    private final float maxDensity;
//    private final float growthTime;
//    @Getter
//    private final Supplier<Item> fruit;
//    @Getter
//    private final int[] stages;
//    private final int numStages;
//    @Getter
//    private final ITreeGen generator;
//    @Getter
//    private final float[] paramMap;
//    @Getter
//    private final String logicMap;
//    @Getter
//    private final int soilLongevity;
//    @Getter
//    private final boolean thick;
//    @Getter
//    private final boolean isConifer;
//
//    private WoodTreeVariant(Builder builder) {
//        this.name = builder.name;
//        this.woodType = builder.woodType;
//
//        this.minTemp = builder.minTemp;
//        this.maxTemp = builder.maxTemp;
//        this.minRain = builder.minRain;
//        this.maxRain = builder.maxRain;
//
//        this.minDensity = builder.minDensity;
//        this.maxDensity = builder.maxDensity;
//
//        this.dominance = builder.dominance;
//        this.maxGrowthRadius = builder.maxGrowthRadius;
//        this.maxHeight = builder.maxHeight;
//        this.maxDecayDistance = builder.maxDecayDistance;
//        this.isConifer = builder.isConifer;
//        this.minGrowthTime = builder.minGrowthTime;
//        this.generator = builder.generator;
//        this.bushGenerator = builder.bushGenerator;
//
//        this.fruit = builder.fruit;
//        this.stages = builder.stages;
//        this.growthTime = builder.ripeningTime;
//
//        this.numStages = builder.numStages;
//
//        this.paramMap = builder.paramMap;
//        this.logicMap = builder.logicMap;
//        this.soilLongevity = builder.soilLongevity;
//        this.thick = builder.thick;
//
//        if (name.isEmpty())
//            throw new RuntimeException(String.format("WoodTreeVariant name must contain any character: [%s]", name));
//
//        if (!WOOD_TREE_VARIANTS.add(this))
//            throw new RuntimeException(String.format("WoodTreeVariant: [%s] already exists!", name));
//    }
//
//    /**
//     * Возвращает список всех доступных типов дерева.
//     *
//     * @return список типов дерева
//     */
//    public static Set<WoodTreeVariant> getWoodTreeVariants() {
//        return WOOD_TREE_VARIANTS;
//    }
//
//    @NotNull
//    public static WoodTreeVariant getWoodTreesVariantByType(WoodType type) {
//        var tree = WoodTreeVariant.getWoodTreeVariants()
//                .stream()
//                .filter(entry -> entry.getWoodType() == type)
//                .findFirst()
//                .orElse(null);
//        if (tree != null) return tree;
//        throw new RuntimeException(String.format("Tree is null: %s", type));
//    }
//
//    @NotNull
//    @Override
//    public String toString() {
//        return name;
//    }
//
//    /**
//     * Проверяет, является ли указанное местоположение допустимым.
//     *
//     * @param temp    температура местоположения
//     * @param rain    количество осадков в местоположении
//     * @param density плотность в местоположении
//     * @return {@code true}, если местоположение допустимо, иначе {@code false}
//     */
//    public boolean isValidLocation(float temp, float rain, float density) {
//        return minTemp <= temp && maxTemp >= temp && minRain <= rain && maxRain >= rain && minDensity <= density && maxDensity >= density;
//    }
//
//    public boolean isValidConditions(float temp, float rain) {
//        return minTemp - 5 < temp && temp < maxTemp + 5 && minRain - 50 < rain && rain < maxRain + 50;
//    }
//
//    public boolean isValidForGrowth(float temp, float rain) {
//        return minTemp < temp && temp < maxTemp && minRain < rain && rain < maxRain;
//    }
//
//    public int getStageForMonth(Month month) {
//        return stages[month.ordinal()];
//    }
//
//    public int getStageForMonth() {
//        return getStageForMonth(CalendarTFC.CALENDAR_TIME.getMonthOfYear());
//    }
//
//    public ItemStack getFoodDrop() {
//        return new ItemStack(this.fruit.get());
//    }
//
//    public Supplier<Item> getDrop() {
//        return fruit;
//    }
//
//
//    /**
//     * Проверяет, есть ли кусты в местоположении.
//     *
//     * @return {@code true}, если есть кусты, иначе {@code false}
//     */
//    public boolean hasBushes() {
//        return bushGenerator != null;
//    }
//
//    /**
//     * Создает дерево с использованием менеджера шаблонов, мира, позиции, генератора случайных чисел и флага,
//     * указывающего, является ли это генерацией мира.
//     *
//     * @param manager    менеджер шаблонов для создания дерева
//     * @param world      мир, в котором будет создано дерево
//     * @param pos        позиция, где будет создано дерево
//     * @param rand       генератор случайных чисел
//     * @param isWorldGen флаг, указывающий, является ли это генерацией мира
//     * @return {@code true}, если дерево было успешно создано, иначе {@code false}
//     */
//    public boolean makeTree(TemplateManager manager, World world, BlockPos pos, Random rand, boolean isWorldGen) {
//        if (generator.canGenerateTree(world, pos, this)) {
//            generator.generateTree(manager, world, pos, this, rand, isWorldGen);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean makeTree(World world, BlockPos pos, Random rand, boolean isWorldGen) {
//        if (!world.isRemote) {
//            return makeTree(((WorldServer) world).getStructureTemplateManager(), world, pos, rand, isWorldGen);
//        }
//        return false;
//    }
//
//    @SideOnly(Side.CLIENT)
//    public void addInfo(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
//        if (GuiScreen.isShiftKeyDown()) {
//            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
//            tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) minRain, (int) maxRain));
//            tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", minTemp), String.format("%.1f", maxTemp)));
//        } else {
//            tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
//        }
//    }
//
//    public static class Builder {
//
//        private final String name;
//        private ITreeGen generator;
//        private WoodType woodType;
//        private float minGrowthTime = 7;
//        private float minTemp;
//        private float maxTemp;
//        private float minRain;
//        private float maxRain;
//        private float minDensity = 0.1f;
//        private float maxDensity = 2f;
//        private float dominance = 0.001f * (maxTemp - minTemp) * (maxRain - minRain);
//        private int maxHeight = 6;
//        private int maxGrowthRadius = 1; // default values
//        private int maxDecayDistance = 4;
//        private boolean isConifer = false;
//        private ITreeGen bushGenerator = null;
//        private float[] paramMap;
//        private String logicMap;
//        private ResourceLocation cellKit;
//        private boolean thick = false;
//        private int soilLongevity = 8;
//        private Supplier<Item> fruit;
//        private int[] stages;
//        private float ripeningTime;
//        private int numStages;
//
//        public Builder(@NotNull String name, float minRain, float maxRain, float minTemp, float maxTemp) {
//            this.name = name;
//
//            this.minRain = minRain;
//            this.maxRain = maxRain;
//
//            this.minTemp = minTemp;
//            this.maxTemp = maxTemp;
//        }
//
//        public Builder setWoodType(WoodType woodType) {
//            this.woodType = woodType;
//            return this;
//        }
//
//        public Builder setGenerator(ITreeGen generator) {
//            this.generator = generator;
//            return this;
//        }
//
//        // Установить радиус роста
//        public Builder setRadius(int maxGrowthRadius) {
//            this.maxGrowthRadius = maxGrowthRadius;
//            return this;
//        }
//
//        // Установить доминирование
//        public Builder setDominance(float dominance) {
//            this.dominance = dominance;
//            return this;
//        }
//
//        // Установить максимальную высоту
//        public Builder setHeight(int maxHeight) {
//            this.maxHeight = maxHeight;
//            return this;
//        }
//
//        // Установить максимальное расстояние распада для листьев дерева.
//        public Builder setDecayDist(int maxDecayDistance) {
//            this.maxDecayDistance = maxDecayDistance;
//            return this;
//        }
//
//        // Установить хвойное дерево
//        public Builder setConifer() {
//            isConifer = true;
//            return this;
//        }
//
//        // Установить генератор кустов по умолчанию
//        public Builder setBushes() {
//            bushGenerator = DefaultTrees.GEN_BUSHES; // TODO генератор для кустов
//            return this;
//        }
//
//        // Установить кастомный генератор кустов
//        public Builder setBushes(ITreeGen bushGenerator) {
//            this.bushGenerator = bushGenerator;
//            return this;
//        }
//
//        // Установить минимальное время роста
//        public Builder setMinGrowthTime(float minGrowthTime) {
//            this.minGrowthTime = minGrowthTime;
//            return this;
//        }
//
//        // Установить плотность
//        public Builder setDensity(float min, float max) {
//            this.minDensity = min;
//            this.maxDensity = max;
//            return this;
//        }
//
//        // Fruit Tree
//        // Установить фрукт для этого дерева и время его созревания
//        public Builder setFruit(Supplier<Item> fruit, float ripeningTime) {
//            this.fruit = fruit;
//            this.ripeningTime = ripeningTime * CalendarTFC.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;
//            return this;
//        }
//
//        // Установить стадии дерева
//        public Builder setStages(int[] stages) {
//            this.stages = stages;
//            HashSet<Integer> hashSet = new HashSet<>();
//            for (int stage : stages) {
//                hashSet.add(stage);
//            }
//            this.numStages = hashSet.size() <= 1 ? 1 : hashSet.size() - 1;
//            return this;
//        }
//
//
//        // DT Tree
//        public Builder setParamMap(float tapering, float energy, int upProbability, int lowestBranchHeight, float growthRate) {
//            this.paramMap = new float[]{tapering, energy, upProbability, lowestBranchHeight, growthRate};
//            return this;
//        }
//
//        public Builder setGrowthLogicKit(String logicMap) {
//            this.logicMap = logicMap;
//            return this;
//        }
//
//        public Builder setCellKit(String kit) {
//            this.cellKit = new ResourceLocation("dynamictrees", kit);
//            return this;
//        }
//
//        public Builder setCellKit(ResourceLocation cellKit) {
//            this.cellKit = cellKit;
//            return this;
//        }
//
//        public Builder setThick() {
//            this.thick = true;
//            return this;
//        }
//
//        public Builder setSoilLongevity(int longevity) {
//            this.soilLongevity = longevity;
//            return this;
//        }
//
//        public WoodTreeVariant build() {
//            return new WoodTreeVariant(this);
//        }
//    }
//}
