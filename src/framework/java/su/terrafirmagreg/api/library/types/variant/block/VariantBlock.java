package su.terrafirmagreg.api.library.types.variant.block;

import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.library.types.variant.Variant;

import net.minecraft.block.Block;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;
import java.util.function.BiFunction;

@Getter
public abstract class VariantBlock<V, T extends Type<T>> extends Variant<V, T> {


  protected final Map<T, Block> map;
  protected final BiFunction<V, T, Block> factory;

  protected VariantBlock(String name, BiFunction<V, T, Block> factory) {
    super(name);

    this.map = new Object2ObjectOpenHashMap<>();
    this.factory = factory;
  }


  public Block get(T type) {
    var block = map.get(type);
    if (block == null) {
      throw new RuntimeException(String.format("Block is null: %s, %s", this, type));
    }
    return block;
  }
}
