package su.terrafirmagreg.modules.food.api.types.variant.item;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodData;
import su.terrafirmagreg.modules.food.api.types.category.FoodCategory;


import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@Getter
public class FoodItemVariant {

  @Getter
  private static final Set<FoodItemVariant> types = new LinkedHashSet<>();

  private final String name;
  private final FoodCategory category;
  private final FoodData foodData;
  private final boolean heatable;
  private final float heatCapacity;
  private final float cookingTemp;
  private final Optional<String[]> oreDict;

  private FoodItemVariant(Builder builder) {
    this.name = builder.name;
    this.category = builder.category;
    this.foodData = builder.foodData;

    this.heatable = builder.cookingTemp >= 0;
    this.heatCapacity = builder.heatCapacity;
    this.cookingTemp = builder.cookingTemp;

    this.oreDict = builder.oreDict;

    if (name.isEmpty()) {
      throw new RuntimeException(
          String.format("FoodType name must contain any character: [%s]", name));
    }

    if (!types.add(this)) {
      throw new RuntimeException(String.format("FoodType: [%s] already exists!", name));
    }
  }

  @Override
  public String toString() {
    return name;
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
    public Builder foodData(int hunger, float saturation, float water, float grain, float veg,
        float fruit, float meat, float dairy, float decayModifier) {
      this.foodData = new FoodData(hunger, water, saturation, grain, fruit, veg, meat, dairy,
          decayModifier);
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
    public FoodItemVariant build() {
      return new FoodItemVariant(this);
    }
  }
}
