package su.terrafirmagreg.modules.food.api.types.variant.item;

import su.terrafirmagreg.api.library.types.variant.item.VariantItem;
import su.terrafirmagreg.modules.food.ModuleFood;
import su.terrafirmagreg.modules.food.api.types.type.FoodType;

import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

public class FoodItemVariant extends VariantItem<FoodItemVariant, FoodType> {

  @Getter
  private static final Set<FoodItemVariant> variants = new ObjectOpenHashSet<>();


  protected FoodItemVariant(Builder builder) {
    super(builder.name);

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    FoodType.getTypes().forEach(type -> {
      var item = builder.factory.apply(this, type);
      if (map.put(type, item.getItem()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModuleFood.REGISTRY.item(item);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public String getRegistryKey(FoodType type) {
    return String.format("food/%s/%s", this.getName(), type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("food.variant.%s.name", this)).getFormattedText();
  }

  public static class Builder {

    private final String name;

    private BiFunction<FoodItemVariant, FoodType, IFoodItem> factory;

    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<FoodItemVariant, FoodType, IFoodItem> factory) {
      this.factory = factory;
      return this;
    }


    public FoodItemVariant build() {
      return new FoodItemVariant(this);
    }
  }
}
