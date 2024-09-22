package su.terrafirmagreg.modules.wood.api.types.variant.item;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.types.variant.Variant;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentTranslation;


import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class WoodItemVariant extends Variant<WoodItemVariant> {

  private static final Set<WoodItemVariant> variants = new ObjectOpenHashSet<>();

  private final Map<WoodType, Item> map = new Object2ObjectOpenHashMap<>();
  private final BiFunction<WoodItemVariant, WoodType, ? extends Item> factory;

  private WoodItemVariant(Builder builder) {
    super(builder.name);

    this.factory = builder.factory;

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public Item get(WoodType type) {
    var item = this.map.get(type);
    if (item != null) {
      return item;
    }
    throw new RuntimeException(String.format("Item is null: %s, %s", this, type));
  }

  public String getRegistryKey(WoodType type) {
    return String.format("wood/%s/%s", this.getName(), type);
  }

  public String getCustomResource() {
    return String.format("wood/%s", this.getName());
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(
            String.format("wood.variant.%s.name", this)).getFormattedText();
  }


  public static class Builder {

    private final String name;

    private BiFunction<WoodItemVariant, WoodType, ? extends Item> factory;

    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<WoodItemVariant, WoodType, ? extends Item> factory) {
      this.factory = factory;
      return this;
    }

    public WoodItemVariant build(RegistryManager registry) {

      var variant = new WoodItemVariant(this);
      WoodType.getTypes().forEach(type -> {
        if (variant.getMap().put(type, factory.apply(variant, type)) != null) {
          throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
      });
      registry.items(variant.getMap().values());

      return variant;
    }
  }
}
