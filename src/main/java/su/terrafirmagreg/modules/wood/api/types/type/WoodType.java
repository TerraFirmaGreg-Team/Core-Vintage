package su.terrafirmagreg.modules.wood.api.types.type;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.data.lib.MCDate.Month;
import su.terrafirmagreg.data.lib.types.type.Type;
import su.terrafirmagreg.data.lib.types.variant.Variant;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.dries007.tfc.api.util.ITreeGenerator;
import net.dries007.tfc.types.DefaultTrees;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendar;

import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Getter
public class WoodType extends Type<WoodType> {

  @Getter
  private static final Set<WoodType> types = new ObjectOpenHashSet<>();

  private final int color;
  private final float burnTemp;
  private final int burnTicks;
  private final boolean canMakeTannin;
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
  private final float ripeningTime;
  private final Supplier<Item> fruit;
  private final int[] stages;
  private final int numStages;
  private final ITreeGenerator generator;
  private final float[] paramMap;
  private final String logicMap;
  private final int soilLongevity;
  private final boolean thick;
  private final boolean isConifer;

  private WoodType(Builder builder) {
    super(builder.name);

    this.color = builder.color;

    this.burnTemp = builder.burnTemp;
    this.burnTicks = builder.burnTicks;
    this.canMakeTannin = builder.canMakeTannin;

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

    this.fruit = builder.fruit;
    this.stages = builder.stages;
    this.ripeningTime = builder.ripeningTime;

    this.numStages = builder.numStages;

    this.paramMap = builder.paramMap;
    this.logicMap = builder.logicMap;
    this.soilLongevity = builder.soilLongevity;
    this.thick = builder.thick;

    if (!types.add(this)) {
      throw new RuntimeException(String.format("WoodType: [%s] already exists!", this.name));
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
    return new ItemStack(this.fruit.get());
  }

  public Supplier<Item> getDrop() {
    return fruit;
  }

//  public boolean makeTree(World world, BlockPos pos, Random rand, boolean isWorldGen) {
//    if (!world.isRemote) {
//      return makeTree(((WorldServer) world).getStructureTemplateManager(), world, pos, rand, isWorldGen);
//    }
//    return false;
//  }
//
//  /**
//   * Создает дерево с использованием менеджера шаблонов, мира, позиции, генератора случайных чисел и флага, указывающего, является ли это генерацией мира.
//   *
//   * @param manager    менеджер шаблонов для создания дерева
//   * @param world      мир, в котором будет создано дерево
//   * @param pos        позиция, где будет создано дерево
//   * @param rand       генератор случайных чисел
//   * @param isWorldGen флаг, указывающий, является ли это генерацией мира
//   * @return {@code true}, если дерево было успешно создано, иначе {@code false}
//   */
//  public boolean makeTree(TemplateManager manager, World world, BlockPos pos, Random rand, boolean isWorldGen) {
//    if (generator.canGenerateTree(world, pos, this)) {
//      generator.generateTree(manager, world, pos, this, rand, isWorldGen);
//      return true;
//    }
//    return false;
//  }

  /**
   * Проверяет, есть ли кусты в местоположении.
   *
   * @return {@code true}, если есть кусты, иначе {@code false}
   */
  public boolean hasBushes() {
    return bushGenerator != null;
  }

  @SideOnly(Side.CLIENT)
  public void addInfo(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (GuiScreen.isShiftKeyDown()) {
      tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
      tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) minRain, (int) maxRain));
      tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", minTemp), String.format("%.1f", maxTemp)));
    } else {
      tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
    }
  }

  public ResourceLocation getTexture(Variant<?, WoodType> variant) {
    return ModUtils.resource(String.format("textures/blocks/wood/%s/%s.png", variant, this));
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("wood.type.%s.name", this)).getFormattedText();
  }

  public String getRegistryKey(Variant<?, WoodType> variant) {
    return String.format("wood/%s/%s", variant, this);
  }

  public static class Builder {

    private final String name;

    private ITreeGenerator generator;
    private ITreeGenerator bushGenerator;
    private ResourceLocation cellKit;
    private String logicMap;
    private Supplier<Item> fruit;
    private float[] paramMap;
    private int[] stages;
    private float ripeningTime;
    private float minTemp;
    private float maxTemp;
    private float minRain;
    private float maxRain;
    private float dominance;
    private float minGrowthTime;
    private float minDensity;
    private float maxDensity;
    private float burnTemp;
    private int burnTicks;
    private int numStages;
    private int maxHeight;
    private int maxGrowthRadius;
    private int maxDecayDistance;
    private int soilLongevity;
    private int color;
    private boolean isConifer;
    private boolean thick;
    private boolean canMakeTannin;

    public Builder(String name) {
      this.name = name;

      this.bushGenerator = null;
      this.dominance = 0.001f * (maxTemp - minTemp) * (maxRain - minRain);
      this.minGrowthTime = 7;
      this.minDensity = 0.1f;
      this.maxDensity = 2f;
      this.burnTemp = 0;
      this.burnTicks = 0;
      this.maxHeight = 6;
      this.maxGrowthRadius = 1;
      this.maxDecayDistance = 4;
      this.soilLongevity = 8;
      this.color = 0xff000000;
      this.isConifer = false;
      this.thick = false;
      this.canMakeTannin = false;

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

    public Builder generator(ITreeGenerator generator) {
      this.generator = generator;
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

    public WoodType build() {
      return new WoodType(this);
    }
  }
}
