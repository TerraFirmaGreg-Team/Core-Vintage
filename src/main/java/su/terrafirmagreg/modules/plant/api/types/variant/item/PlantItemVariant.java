package su.terrafirmagreg.modules.plant.api.types.variant.item;

import su.terrafirmagreg.data.lib.types.variant.item.VariantItem;
import su.terrafirmagreg.modules.plant.ModulePlant;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

public class PlantItemVariant extends VariantItem<PlantItemVariant, PlantType> {

  @Getter
  private static final Set<PlantItemVariant> variants = new ObjectOpenHashSet<>();


  protected PlantItemVariant(Builder builder) {
    super(builder.name);

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    PlantType.getTypes().forEach(type -> {
      var item = builder.factory.apply(this, type);
      if (map.put(type, item.getItem()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModulePlant.REGISTRY.item(item);
    });
  }

  public String getRegistryKey(SoilType type) {
    return String.format("plant/%s/%s", this.getName(), type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("plant.variant.%s.name", this)).getFormattedText();
  }

  public static class Builder {

    private final String name;

    private BiFunction<PlantItemVariant, PlantType, IPlantItem> factory;

    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<PlantItemVariant, PlantType, IPlantItem> factory) {
      this.factory = factory;
      return this;
    }


    public PlantItemVariant build() {
      return new PlantItemVariant(this);
    }
  }
}
