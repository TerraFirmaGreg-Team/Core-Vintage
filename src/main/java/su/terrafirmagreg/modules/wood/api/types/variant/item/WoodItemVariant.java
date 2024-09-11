package su.terrafirmagreg.modules.wood.api.types.variant.item;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.types.variant.Variant;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.item.Item;


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
  private BiFunction<WoodItemVariant, WoodType, ? extends Item> factory;

  private WoodItemVariant(String name) {
    super(name);

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("CropItemVariant: [%s] already exists!", name));
    }
  }

  public static WoodItemVariant builder(String name) {
    return new WoodItemVariant(name);
  }

  public Item get(WoodType type) {
    var item = this.map.get(type);
    if (item != null) {
      return item;
    }
    throw new RuntimeException(String.format("Item wood is null: %s, %s", this, type));
  }

  public WoodItemVariant setFactory(BiFunction<WoodItemVariant, WoodType, ? extends Item> factory) {
    this.factory = factory;
    return this;
  }

  public WoodItemVariant build(RegistryManager registry) {
    WoodType.getTypes().forEach(type -> {
      if (map.put(type, factory.apply(this, type)) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
    });
    registry.items(map.values());
    return this;
  }
}
