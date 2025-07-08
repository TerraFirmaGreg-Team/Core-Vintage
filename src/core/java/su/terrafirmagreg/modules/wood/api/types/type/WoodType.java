package su.terrafirmagreg.modules.wood.api.types.type;

import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.modules.core.feature.calendar.spi.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.spi.ICalendar;
import su.terrafirmagreg.modules.core.feature.calendar.spi.Month;
import su.terrafirmagreg.modules.wood.api.generator.ITreeGenerator;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.TemplateManager;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.dries007.tfc.types.DefaultTrees;

import lombok.Getter;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;

@Getter
public class WoodType extends Type<WoodType> {

  @Getter
  private static final Set<WoodType> types = new ObjectOpenHashSet<>();

  private final int color;
  private final int burnTicks;
  private final float burnTemp;
  private final boolean canMakeTannin;

  // old
  private String logicMap;
  private Supplier<ItemStack> fruit;
  private ITreeGenerator bushGenerator;
  private ITreeGenerator generator;

  private float minGrowthTime;
  private float minTemp;
  private float maxTemp;
  private float minRain;
  private float maxRain;
  private float minDensity;
  private float maxDensity;
  private float ripeningTime;
  private float dominance;

  private int maxGrowthRadius;
  private int numStages;
  private int soilLongevity;
  private int maxHeight;
  private int maxDecayDistance;

  private boolean thick;
  private boolean isConifer;

  private int[] stages;
  private float[] paramMap;

  private WoodType(Builder builder) {
    super("wood", builder.name);

    this.color = builder.color;

    this.burnTemp = builder.burnTemp;
    this.burnTicks = builder.burnTicks;
    this.canMakeTannin = builder.canMakeTannin;
    this.generator = builder.generator;
    this.bushGenerator = builder.bushGenerator;

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

    this.fruit = builder.fruit;
    this.stages = builder.stages;
    this.ripeningTime = builder.ripeningTime;

    this.numStages = builder.numStages;

    this.paramMap = builder.paramMap;
    this.logicMap = builder.logicMap;
    this.soilLongevity = builder.soilLongevity;
    this.thick = builder.thick;

    if (!types.add(this)) {
      throw new RuntimeException(String.format("Type: [%s] already exists!", this.name));
    }
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public boolean isValidLocation(float temp, float rain, float density) {
    return minTemp <= temp && maxTemp >= temp && minRain <= rain && maxRain >= rain && minDensity <= density && maxDensity >= density;
  }

  public boolean isValidConditions(float temp, float rain) {
    return minTemp - 5 < temp && temp < maxTemp + 5 && minRain - 50 < rain && rain < maxRain + 50;
  }

  public boolean isValidForGrowth(float temp, float rain) {
    return minTemp < temp && temp < maxTemp && minRain < rain && rain < maxRain;
  }

  public int getStageForMonth() {
    return getStageForMonth(Calendar.CALENDAR_TIME.getMonthOfYear());
  }

  public int getStageForMonth(Month month) {
    return stages[month.ordinal()];
  }

  public ItemStack getFoodDrop() {
    return this.fruit.get();
  }

  public Supplier<ItemStack> getDrop() {
    return fruit;
  }

  public boolean makeTree(World world, BlockPos pos, Random rand, boolean isWorldGen) {
    if (!world.isRemote) {
      return makeTree(((WorldServer) world).getStructureTemplateManager(), world, pos, rand, isWorldGen);
    }
    return false;
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
//    if (generator.canGenerateTree(world, pos, this)) {
//      generator.generateTree(manager, world, pos, this, rand, isWorldGen);
//      return true;
//    }
    return false;
  }

  /**
   * Проверяет, есть ли кусты в местоположении.
   *
   * @return {@code true}, если есть кусты, иначе {@code false}
   */
  public boolean hasBushes() {
    return bushGenerator != null;
  }

  public static class Builder {

    private final String name;

    private ITreeGenerator generator;
    private ITreeGenerator bushGenerator;
    private String logicMap;
    private ResourceLocation cellKit;
    private Supplier<ItemStack> fruit;

    private float[] paramMap;
    private int[] stages;

    private float burnTemp;
    private float ripeningTime;
    private float minTemp;
    private float maxTemp;
    private float minRain;
    private float maxRain;
    private float minDensity;
    private float maxDensity;
    private float dominance;
    private float minGrowthTime;

    private int burnTicks;
    private int color;
    private int maxHeight;
    private int maxGrowthRadius;
    private int maxDecayDistance;
    private int soilLongevity;
    private int numStages;

    private boolean canMakeTannin;
    private boolean isConifer;
    private boolean thick;

    public Builder(String name) {
      this.name = name;

      this.burnTemp = 0;
      this.burnTicks = 0;
      this.color = 0xff000000;
      this.canMakeTannin = false;
      this.bushGenerator = null;

    }

    public Builder color(int color) {
      this.color = color;
      return this;
    }

    // Установить температуру и количество тиков горения
    public Builder burnInfo(float burnTemp, int burnTicks) {
      this.burnTemp = burnTemp;
      this.burnTicks = burnTicks;
      return this;
    }

    // Установить возможность производить танин
    public Builder isCanMakeTannin() {
      canMakeTannin = true;
      return this;
    }

    public Builder generator(ITreeGenerator generator) {
      this.generator = generator;
      return this;
    }

    // Установить генератор кустов по умолчанию
    public Builder bushes() {
      this.bushGenerator = DefaultTrees.GEN_BUSHES; // TODO генератор для кустов
      return this;
    }

    // Установить кастомный генератор кустов
    public Builder bushes(ITreeGenerator bushGenerator) {
      this.bushGenerator = bushGenerator;
      return this;
    }

    public Builder rainInfo(float minRain, float maxRain) {
      this.minRain = minRain;
      this.maxRain = maxRain;
      return this;
    }

    public Builder tempInfo(float minTemp, float maxTemp) {
      this.minTemp = minTemp;
      this.maxTemp = maxTemp;
      return this;
    }


    // Установить радиус роста
    public Builder radius(int maxGrowthRadius) {
      this.maxGrowthRadius = maxGrowthRadius;
      return this;
    }

    // Установить доминирование
    public Builder dominance(float dominance) {
      this.dominance = dominance;
      return this;
    }

    // Установить максимальную высоту
    public Builder height(int maxHeight) {
      this.maxHeight = maxHeight;
      return this;
    }

    // Установить максимальное расстояние распада для листьев дерева.
    public Builder decayDist(int maxDecayDistance) {
      this.maxDecayDistance = maxDecayDistance;
      return this;
    }

    // Установить хвойное дерево
    public Builder isConifer() {
      this.isConifer = true;
      return this;
    }

    // Установить минимальное время роста
    public Builder minGrowthTime(float minGrowthTime) {
      this.minGrowthTime = minGrowthTime;
      return this;
    }

    // Установить плотность
    public Builder density(float minDensity, float maxDensity) {
      this.minDensity = minDensity;
      this.maxDensity = maxDensity;
      return this;
    }

    // Fruit Tree
    // Установить фрукт для этого дерева и время его созревания
    public Builder fruit(Supplier<ItemStack> fruit, float ripeningTime) {
      this.fruit = fruit;
      this.ripeningTime = ripeningTime * Calendar.CALENDAR_TIME.getDaysInMonth() * ICalendar.HOURS_IN_DAY;
      return this;
    }

    // Установить стадии дерева
    public Builder stages(int[] stages) {
      this.stages = stages;
      HashSet<Integer> hashSet = new HashSet<>();
      for (int stage : stages) {
        hashSet.add(stage);
      }
      this.numStages = hashSet.size() <= 1 ? 1 : hashSet.size() - 1;
      return this;
    }

    // DT Tree
    public Builder paramMap(float tapering, float energy, int upProbability, int lowestBranchHeight, float growthRate) {
      this.paramMap = new float[]{tapering, energy, upProbability, lowestBranchHeight, growthRate};
      return this;
    }

    public Builder growthLogicKit(String logicMap) {
      this.logicMap = logicMap;
      return this;
    }

    public Builder cellKit(String kit) {
      this.cellKit = new ResourceLocation("dynamictrees", kit);
      return this;
    }

    public Builder cellKit(ResourceLocation cellKit) {
      this.cellKit = cellKit;
      return this;
    }

    public Builder isThick() {
      this.thick = true;
      return this;
    }

    public Builder setSoilLongevity(int longevity) {
      this.soilLongevity = longevity;
      return this;
    }


    public WoodType build() {
      return new WoodType(this);
    }
  }
}
