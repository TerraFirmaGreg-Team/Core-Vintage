package su.terrafirmagreg.data.lib.types.variant.item;

import su.terrafirmagreg.data.lib.types.type.Type;
import su.terrafirmagreg.data.lib.types.variant.Variant;

import net.minecraft.item.Item;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class VariantItem<V, T extends Type<T>> extends Variant<V, T> {

  protected final Map<T, Item> map;

  protected VariantItem(String name) {
    super(name);

    this.map = new Object2ObjectOpenHashMap<>();
  }


  public Item get(T type) {
    var item = map.get(type);
    if (item == null) {
      throw new RuntimeException(String.format("Item is null: %s, %s", this, type));
    }
    return item;
  }
}
