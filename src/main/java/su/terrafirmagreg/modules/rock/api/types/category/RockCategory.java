package su.terrafirmagreg.modules.rock.api.types.category;

import su.terrafirmagreg.data.lib.types.category.Category;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.dries007.tfc.objects.ToolMaterials;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

@Getter
public class RockCategory extends Category<RockCategory> {

  @Getter
  private static final Set<RockCategory> categories = new ObjectOpenHashSet<>();

  private final boolean layer1;
  private final boolean layer2;
  private final boolean layer3;
  private final boolean hasAnvil;
  private final float caveGenMod;
  private final float caveFreqMod;
  private final float hardnessModifier;
  private final TextFormatting textFormatting;
  private final ToolMaterial toolMaterial;

  private RockCategory(Builder builder) {
    super(builder.name);

    this.layer1 = builder.layer1;
    this.layer2 = builder.layer2;
    this.layer3 = builder.layer3;
    this.hasAnvil = builder.hasAnvil;
    this.caveGenMod = builder.caveGenMod;
    this.caveFreqMod = builder.caveFreqMod;
    this.hardnessModifier = builder.hardnessModifier;
    this.textFormatting = builder.textFormatting;
    this.toolMaterial = builder.toolMaterial;

    if (!categories.add(this)) {
      throw new RuntimeException(String.format("RockCategory: [%s] already exists!", name));
    }
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  @Nullable
  public static RockCategory getByName(@NotNull String name) {
    return categories
      .stream()
      .filter(s -> s.getName().equals(name))
      .findFirst()
      .orElse(null);
  }

  /**
   * Возвращает экземпляр породы по индексу.
   *
   * @param i Индекс породы.
   * @return Экземпляр породы.
   */
  public static RockCategory valueOf(int i) {
    var values = new RockCategory[categories.size()];
    values = categories.toArray(values);

    return i >= 0 && i < values.length ? values[i] : values[i % values.length];
  }

  /**
   * Возвращает индекс породы в списке.
   *
   * @param type Порода.
   * @return Индекс породы.
   */
  public static int indexOf(RockCategory type) {
    return new ArrayList<>(categories).indexOf(type);
  }

  /**
   * Возвращает локализованное имя категории породы.
   *
   * @return Локализованное имя категории породы.
   */
  public String getLocalizedName() {
    return textFormatting + new TextComponentTranslation(
      String.format("rock.category.%s.name", this)).getFormattedText();
  }

  /**
   * Представляет слой породы и определяет, принадлежит ли порода к данному слою.
   */
  public enum Layer implements Predicate<RockType> {
    BOTTOM(3, x -> x.getCategory().layer3),
    MIDDLE(2, x -> x.getCategory().layer2),
    TOP(1, x -> x.getCategory().layer1);

    public final int layer;
    private final Predicate<RockType> filter;

    /**
     * Создает экземпляр слоя породы с указанными параметрами.
     *
     * @param layer  Номер слоя породы.
     * @param filter Фильтр, определяющий, принадлежит ли порода к данному слою.
     */
    Layer(int layer, Predicate<RockType> filter) {
      this.layer = layer;
      this.filter = filter;
    }

    /**
     * Проверяет, принадлежит ли указанная порода к данному слою.
     *
     * @param type Порода для проверки.
     * @return true, если порода принадлежит к данному слою, в противном случае - false.
     */
    @Override
    public boolean test(RockType type) {
      return filter.test(type);
    }
  }

  public static class Builder {

    private final String name;

    private boolean layer1;
    private boolean layer2;
    private boolean layer3;
    private boolean hasAnvil;
    private float caveGenMod;
    private float caveFreqMod;
    private float hardnessModifier;
    private TextFormatting textFormatting;
    private ToolMaterial toolMaterial;

    public Builder(@NotNull String name) {

      this.name = name;

      this.layer1 = false;
      this.layer2 = false;
      this.layer3 = false;

      this.caveGenMod = 0f;
      this.caveFreqMod = 0f;
      this.hardnessModifier = 0f;
      this.textFormatting = TextFormatting.RESET;
      this.hasAnvil = false;
      this.toolMaterial = ToolMaterials.IGNEOUS_INTRUSIVE;
    }

    /**
     * Устанавливает значения флагов для слоев породы.
     *
     * @param layer1 Флаг, указывающий, присутствует ли категория в первом слое пород.
     * @param layer2 Флаг, указывающий, присутствует ли категория во втором слое пород.
     * @param layer3 Флаг, указывающий, присутствует ли категория в третьем слое пород.
     * @return Возвращает объект Builder для цепочки вызовов.
     */
    public Builder layer(boolean layer1, boolean layer2, boolean layer3) {
      this.layer1 = layer1;
      this.layer2 = layer2;
      this.layer3 = layer3;
      return this;
    }

    /**
     * Устанавливает значения модификаторов генерации породы в пещерах.
     *
     * @param caveGenMod  Модификатор генерации породы в пещерах.
     * @param caveFreqMod Модификатор частоты генерации породы в пещерах.
     * @return Возвращает объект Builder для цепочки вызовов.
     */
    public Builder caveMod(float caveGenMod, float caveFreqMod) {
      this.caveGenMod = caveGenMod;
      this.caveFreqMod = caveFreqMod;
      return this;
    }

    /**
     * Устанавливает значение модификатора прочности породы.
     *
     * @param hardnessModifier Модификатор прочности породы.
     * @return Возвращает объект Builder для цепочки вызовов.
     */
    public Builder hardnessModifier(float hardnessModifier) {
      this.hardnessModifier = hardnessModifier;
      return this;
    }

    /**
     * Устанавливает значение форматирования текста для отображения категории.
     *
     * @param textFormatting Форматирование текста для отображения категории.
     * @return Возвращает объект Builder для цепочки вызовов.
     */
    public Builder textFormatting(@NotNull TextFormatting textFormatting) {
      this.textFormatting = textFormatting;
      return this;
    }

    public Builder toolMaterial(ToolMaterial toolMaterial) {
      this.toolMaterial = toolMaterial;
      return this;
    }

    /**
     * Устанавливает флаг, указывающий, имеет ли категория наковальню.
     *
     * @return Возвращает объект Builder для цепочки вызовов.
     */
    public Builder hasAnvil() {
      this.hasAnvil = true;
      return this;
    }

    public RockCategory build() {

      return new RockCategory(this);
    }
  }

}
