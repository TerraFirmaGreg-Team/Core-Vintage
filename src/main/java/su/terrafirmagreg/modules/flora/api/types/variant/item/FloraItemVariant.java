package su.terrafirmagreg.modules.flora.api.types.variant.item;

import su.terrafirmagreg.api.library.types.variant.item.VariantItem;
import su.terrafirmagreg.modules.flora.ModuleFlora;
import su.terrafirmagreg.modules.flora.api.types.type.FloraType;

import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

public class FloraItemVariant extends VariantItem<FloraItemVariant, FloraType> {

  @Getter
  private static final Set<FloraItemVariant> variants = new ObjectOpenHashSet<>();


  protected FloraItemVariant(Builder builder) {
    super(builder.name);

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    FloraType.getTypes().forEach(type -> {
      var item = builder.factory.apply(this, type);
      if (map.put(type, item.getItem()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModuleFlora.REGISTRY.item(item);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public String getRegistryKey(FloraType type) {
    return String.format("plant/%s/%s", this.getName(), type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("plant.variant.%s.name", this)).getFormattedText();
  }

  public static class Builder {

    private final String name;

    private BiFunction<FloraItemVariant, FloraType, IFloraItem> factory;

    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<FloraItemVariant, FloraType, IFloraItem> factory) {
      this.factory = factory;
      return this;
    }


    public FloraItemVariant build() {
      return new FloraItemVariant(this);
    }
  }
}
