package su.terrafirmagreg.modules.wood.api.types.type;

import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.modules.wood.api.generator.ITreeGenerator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.TemplateManager;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.dries007.tfc.types.DefaultTrees;

import lombok.Getter;

import java.util.Random;
import java.util.Set;

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

    if (!types.add(this)) {
      throw new RuntimeException(String.format("Type: [%s] already exists!", this.name));
    }
  }

  public static Builder builder(String name) {
    return new Builder(name);
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

    private float burnTemp;
    private int burnTicks;
    private int color;
    private boolean canMakeTannin;

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


    public WoodType build() {
      return new WoodType(this);
    }
  }
}
