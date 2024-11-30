package su.terrafirmagreg.modules.flora.api.types.type;

import su.terrafirmagreg.api.library.MCDate.Month;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.flora.api.types.category.FloraCategory;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.DESERT;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.DESERT_TALL_PLANT;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.DRY;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.DRY_TALL_PLANT;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.EMERGENT_TALL_WATER;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.EMERGENT_TALL_WATER_SEA;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.FLOATING_SEA;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.REED;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.REED_SEA;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_REED;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_REED_SEA;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_WATER;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_WATER_SEA;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.WATER;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.WATER_SEA;
import static su.terrafirmagreg.modules.worldgen.classic.ChunkGenClassic.FRESH_WATER;
import static su.terrafirmagreg.modules.worldgen.classic.ChunkGenClassic.SALT_WATER;

@Getter
public class FloraType extends Type<FloraType> {

  @Getter
  private static final Set<FloraType> types = new ObjectOpenHashSet<>();


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
  private final FloraCategory category;

  // Материал, связанный с растением
  private final Material material;

  // Помечает ли растение землю с глиной
  private final boolean isClayMarking;

  // Может ли растение появиться только в болотах
  private final boolean isSwampPlant;

  // Имя записи в словаре растений для растения
  private final Optional<String[]> oreDictName;


  private FloraType(Builder builder) {
    super(builder.name);

    this.stages = builder.stages;
    this.numStages = builder.numStages;
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

    this.category = builder.category;
    this.isClayMarking = builder.isClayMarking;
    this.isSwampPlant = builder.isSwampPlant;
    this.material = category.getMaterial();
    this.oreDictName = Optional.ofNullable(builder.oreDictName);

    if (!types.add(this)) {
      throw new RuntimeException(String.format("Type: [%s] already exists!", name));
    }
  }

  public static Builder builder(String name) {
    return new Builder(name);
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
      if (world.getBlockState(pos.down(i)) != water && world.getBlockState(pos.down(i)).getMaterial() != Material.CORAL) {
        return false;
      }
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

    if (depthCounter == 0 || maxDepth == 0) {return -1;}

    for (int i = 1; i <= depthCounter; ++i) {
      if (world.getBlockState(pos.down(i)) != water) {return -1;}
    }

    while (world.getBlockState(pos.down(depthCounter)) == water) {
      depthCounter++;
      if (depthCounter > maxDepth + 1) {return -1;}
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
    return getStageForMonth(Calendar.CALENDAR_TIME.getMonthOfYear());
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
    if (category == FLOATING_SEA || category == WATER_SEA || category == TALL_WATER_SEA || category == EMERGENT_TALL_WATER_SEA) {
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
    return category.isCanBePotted();
  }

  /**
   * Возвращает тип растения в виде перечисления EnumType.
   *
   * @return тип растения
   */
  public final EnumType getEnumPlantType() {
    if (category == DESERT || category == DESERT_TALL_PLANT) {
      if (isClayMarking) {return EnumType.DESERT_CLAY;} else {return EnumType.NONE;}

    } else if (category == DRY || category == DRY_TALL_PLANT) {
      if (isClayMarking) {return EnumType.DRY_CLAY;} else {return EnumType.DRY;}

    } else if (category == REED || category == TALL_REED) {
      return EnumType.FRESH_BEACH;

    } else if (category == REED_SEA || category == TALL_REED_SEA) {
      return EnumType.SALT_BEACH;

    } else if (category == WATER || category == TALL_WATER || category == EMERGENT_TALL_WATER) {
      return EnumType.FRESH_WATER;

    } else if (category == WATER_SEA || category == TALL_WATER_SEA || category == EMERGENT_TALL_WATER_SEA) {
      return EnumType.SALT_WATER;
    }
    if (isClayMarking) {return EnumType.CLAY;} else {return EnumType.NONE;}
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
    if (rain < minRain) {return PlantValidity.DRY;}
    if (rain > maxRain) {return PlantValidity.WET;}
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

  public ResourceLocation getTexture(Variant<?, FloraType> variant) {
    return ModUtils.resource(String.format("textures/blocks/flora/%s/%s.png", variant, this));
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("flora.type.%s.name", this)).getFormattedText();
  }

  public String getRegistryKey(Variant<?, FloraType> variant) {
    return String.format("flora/%s/%s", variant, this);
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

    private FloraCategory category;

    private int[] stages;
    private int numStages;
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

    public Builder(@Nonnull String name) {
      this.name = name;
      this.stages = new int[]{0};
      this.numStages = 0;
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

    // Установка варианта растения
    public Builder category(FloraCategory floraCategory) {
      this.category = floraCategory;
      return this;
    }


    // Установка стадий роста растения
    public Builder stages(int[] stages) {
      this.stages = stages;
      HashSet<Integer> hashSet = new HashSet<>();
      for (int stage : stages) {
        hashSet.add(stage);
      }
      this.numStages = hashSet.size() <= 1 ? 1 : hashSet.size() - 1;
      return this;
    }

    // Установка флага, указывающего, помечает ли это растение глинистые земли
    public Builder isClayMarking() {
      this.isClayMarking = true;
      return this;
    }

    // Установка флага, указывающего, может ли это растение расти только в болоте
    public Builder isSwampPlant() {
      this.isSwampPlant = true;
      return this;
    }

    // Установка минимальной и максимальной температуры для роста
    public Builder growthTemp(float minGrowthTemp, float maxGrowthTemp) {
      this.minGrowthTemp = minGrowthTemp;
      this.maxGrowthTemp = maxGrowthTemp;
      return this;
    }

    // Установка минимальной и максимальной температуры
    public Builder temp(float minTemp, float maxTemp) {
      this.minTemp = minTemp;
      this.maxTemp = maxTemp;
      return this;
    }

    // Установка минимального и максимального количества осадков
    public Builder rain(float minRain, float maxRain) {
      this.minRain = minRain;
      this.maxRain = maxRain;
      return this;
    }

    // Установка минимального и максимального уровня освещения для генерации мира
    public Builder sun(int minSun, int maxSun) {
      this.minSun = minSun;
      this.maxSun = maxSun;
      return this;
    }

    // Установка максимальной высоты для двойных растений
    public Builder maxHeight(int maxHeight) {
      this.maxHeight = maxHeight;
      return this;
    }

    // Установка минимальной и максимальной глубины воды для водных растений на генерации мира
    public Builder waterDepth(int minWaterDepth, int maxWaterDepth) {
      this.minWaterDepth = minWaterDepth;
      this.maxWaterDepth = maxWaterDepth;
      return this;
    }

    // Установка модификатора для перемещения игрока по этому растению по X/Z
    public Builder movementMod(double movementMod) {
      this.movementMod = movementMod;
      return this;
    }

    // Установка имени записи в словаре растений для этого растения
    public Builder addOreDict(String... oreDictName) {
      this.oreDictName = oreDictName;
      return this;
    }

    public FloraType build() {
      return new FloraType(this);
    }
  }
}
