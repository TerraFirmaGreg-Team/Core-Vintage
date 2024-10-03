package su.terrafirmagreg.data.lib.types.variant.block;

import su.terrafirmagreg.data.lib.types.type.Type;
import su.terrafirmagreg.data.lib.types.variant.Variant;

import net.minecraft.block.Block;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class VariantBlock<V, T extends Type<T>> extends Variant<V, T> {

  protected final Map<T, Block> map;

  protected VariantBlock(String name) {
    super(name);

    this.map = new Object2ObjectOpenHashMap<>();
  }


  public Block get(T type) {
    var block = map.get(type);
    if (block == null) {
      throw new RuntimeException(String.format("Block is null: %s, %s", this, type));
    }
    return block;
  }

  public abstract String getRegistryKey(T type);

  public abstract String getLocalizedName();

}
