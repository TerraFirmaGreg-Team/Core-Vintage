package su.terrafirmagreg.modules.wood.api.types.variant.item;

import su.terrafirmagreg.api.library.types.variant.item.VariantItem;
import su.terrafirmagreg.modules.wood.ModuleWood;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class WoodItemVariant extends VariantItem<WoodItemVariant, WoodType> {

  @Getter
  private static final Set<WoodItemVariant> variants = new ObjectOpenHashSet<>();

  private WoodItemVariant(Builder builder) {
    super(builder.name);

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    WoodType.getTypes().forEach(type -> {
      var item = builder.factory.apply(this, type);
      if (map.put(type, item.getItem()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModuleWood.REGISTRY.item(item);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }


  public String getCustomResource() {
    return String.format("wood/%s", this.getName());
  }

  @Override
  public String getRegistryKey(WoodType type) {
    return String.format("wood/%s/%s", this.getName(), type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("wood.variant.%s.name", this)).getFormattedText();
  }


  public static class Builder {

    private final String name;

    private BiFunction<WoodItemVariant, WoodType, IWoodItem> factory;

    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<WoodItemVariant, WoodType, IWoodItem> factory) {
      this.factory = factory;
      return this;
    }

    public WoodItemVariant build() {
      return new WoodItemVariant(this);
    }
  }
}
