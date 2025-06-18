package su.terrafirmagreg.framework.manager.registry.base.generation.spi;

import su.terrafirmagreg.modules.core.feature.calendar.spi.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.spi.ICalendar;
import su.terrafirmagreg.modules.core.feature.calendar.spi.Month;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import lombok.Getter;

import java.util.HashSet;
import java.util.function.Supplier;

@Getter
public abstract class BaseGeneratorTree extends BaseGenerator { //implements ITreeGenerator

  public final Builder builder;
  public final Supplier<IBlockState> log;
  public final Supplier<IBlockState> leaves;

  private final int maxGrowthRadius;
  private final float dominance;
  private final int maxHeight;
  private final int maxDecayDistance;
  private final float minGrowthTime;
  private final float minTemp;
  private final float maxTemp;
  private final float minRain;
  private final float maxRain;
  private final float minDensity;
  private final float maxDensity;
  private final float ripeningTime;
  private final Supplier<Item> fruit;
  private final int[] stages;
  private final int numStages;

  private final float[] paramMap;
  private final String logicMap;
  private final int soilLongevity;
  private final boolean thick;
  private final boolean isConifer;

  public BaseGeneratorTree(Builder builder) {

    this.builder = builder;
    this.log = builder.log;
    this.leaves = builder.leaves;

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
    return new ItemStack(this.fruit.get());
  }

  public Supplier<Item> getDrop() {
    return fruit;
  }


  @Getter
  public static class Builder {

    private String name;
    private String logicMap;
    private ResourceLocation cellKit;

    private Supplier<Item> fruit;
    private Supplier<IBlockState> log;
    private Supplier<IBlockState> leaves;

    private float[] paramMap;
    private int[] stages;

    private float ripeningTime;
    private float minTemp;
    private float maxTemp;
    private float minRain;
    private float maxRain;
    private float minDensity;
    private float maxDensity;
    private float dominance;
    private float minGrowthTime;

    private int maxHeight;
    private int maxGrowthRadius;
    private int maxDecayDistance;
    private int soilLongevity;
    private int numStages;

    private boolean isConifer;
    private boolean thick;

    protected Builder() {
      this.dominance = 0.001f * (maxTemp - minTemp) * (maxRain - minRain);
      this.minGrowthTime = 7;
      this.minDensity = 0.1f;
      this.maxDensity = 2f;
      this.maxHeight = 6;

      this.maxGrowthRadius = 1;
      this.maxDecayDistance = 4;
      this.soilLongevity = 8;

      this.isConifer = false;
      this.thick = false;

    }

    public static Builder builder() {
      return new Builder();
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder log(Supplier<IBlockState> log) {
      this.log = log;
      return this;
    }

    public Builder leaves(Supplier<IBlockState> leaves) {
      this.leaves = leaves;
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
    public Builder fruit(Supplier<Item> fruit, float ripeningTime) {
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
  }
}
