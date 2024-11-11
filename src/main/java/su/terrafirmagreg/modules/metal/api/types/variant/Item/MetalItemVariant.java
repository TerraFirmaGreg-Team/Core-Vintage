package su.terrafirmagreg.modules.metal.api.types.variant.Item;

import su.terrafirmagreg.api.library.types.variant.item.VariantItem;
import su.terrafirmagreg.modules.metal.ModuleMetal;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;

import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class MetalItemVariant extends VariantItem<MetalItemVariant, MetalType> {

  @Getter
  private static final Set<MetalItemVariant> variants = new ObjectLinkedOpenHashSet<>();

  private MetalItemVariant(Builder builder) {
    super(builder.name);

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    MetalType.getTypes().forEach(type -> {
      var item = builder.factory.apply(this, type);
      if (map.put(type, item.getItem()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModuleMetal.REGISTRY.item(item);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public String getRegistryKey(MetalType type) {
    return String.format("metal/%s/%s", name, type);
  }


  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("metal.variant.%s.name", this)).getFormattedText();
  }


  public static class Builder {

    private final String name;

    private BiFunction<MetalItemVariant, MetalType, IMetalItem> factory;

    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<MetalItemVariant, MetalType, IMetalItem> factory) {
      this.factory = factory;
      return this;
    }

    public MetalItemVariant build() {
      return new MetalItemVariant(this);
    }
  }
}
