package su.terrafirmagreg.modules.food.api.types.type;

import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.food.api.types.category.FoodCategory;

import net.minecraft.util.text.TextComponentTranslation;

import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public class FoodType extends Type<FoodType> {

  @Getter
  private static final Set<FoodType> types = new LinkedHashSet<>();

  private final FoodCategory category;
  private final FoodData foodData;
  private final boolean heatable;
  private final float heatCapacity;
  private final float cookingTemp;
  private final Optional<String[]> oreDict;

  private FoodType(Builder builder) {
    super(builder.name);

    this.category = builder.category;
    this.foodData = builder.foodData;

    this.heatable = builder.cookingTemp >= 0;
    this.heatCapacity = builder.heatCapacity;
    this.cookingTemp = builder.cookingTemp;

    this.oreDict = builder.oreDict;

    if (!types.add(this)) {
      throw new RuntimeException(String.format("Type: [%s] already exists!", name));
    }
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("food.type.%s.name", this)).getFormattedText();
  }

  public String getRegistryKey(Variant<?, FoodType> variant) {
    return String.format("food/%s/%s", variant, this);
  }


  public static class Builder {

    private final String name;

    private FoodCategory category;
    private FoodData foodData;
    private float heatCapacity = 0;
    private float cookingTemp = -1;
    private Optional<String[]> oreDict;

    /**
     * Конструктор класса Builder.
     *
     * @param name название варианта еды
     */
    public Builder(String name) {
      this.name = name;
    }

    /**
     * Устанавливает категорию еды.
     *
     * @param category категория еды
     * @return объект Builder для цепочки вызовов
     */
    public Builder category(FoodCategory category) {
      this.category = category;
      return this;
    }

    /**
     * Устанавливает данные о пищевой ценности.
     *
     * @param hunger        уровень насыщения
     * @param saturation    уровень сытости
     * @param water         количество воды
     * @param grain         количество зерна
     * @param veg           количество овощей
     * @param fruit         количество фруктов
     * @param meat          количество мяса
     * @param dairy         количество молочных продуктов
     * @param decayModifier модификатор распада
     */
    public Builder foodData(int hunger, float saturation, float water, float grain, float veg, float fruit, float meat, float dairy, float decayModifier) {
      this.foodData = new FoodData(hunger, water, saturation, grain, fruit, veg, meat, dairy, decayModifier);
      return this;
    }

    /**
     * Устанавливает теплоемкость варианта еды.
     *
     * @param heatCapacity теплоемкость
     */
    public Builder heatCapacity(float heatCapacity) {
      this.heatCapacity = heatCapacity;
      return this;
    }

    /**
     * Устанавливает температуру приготовления варианта еды.
     *
     * @param cookingTemp температура приготовления
     */
    public Builder cookingTemp(float cookingTemp) {
      this.cookingTemp = cookingTemp;
      return this;
    }

    /**
     * Устанавливает имена в словаре руд для варианта еды.
     *
     * @param oreDict имена в словаре руд
     */
    public Builder addOreDict(String... oreDict) {
      this.oreDict = Optional.ofNullable(oreDict);
      return this;
    }

    /**
     * Создает и возвращает экземпляр класса FoodType с заданными параметрами.
     *
     * @return экземпляр класса FoodType
     */
    public FoodType build() {
      return new FoodType(this);
    }
  }
}
