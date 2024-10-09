package su.terrafirmagreg.data.lib.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.data.lib.types.type.Type;
import su.terrafirmagreg.data.lib.types.variant.Variant;

import net.minecraft.block.Block;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;

@Getter
public abstract class VariantBlock<B extends IBlockSettings, V, T extends Type<T>> extends Variant<V, T> {

  // public abstract class VariantBlock<T extends IBlockType<?, ?>> extends Variant<T, T.getType()> {

  // VariantBlock<V, T extends Type<T>> extends Variant<V, T> {

  protected final Map<T, B> map;

  protected VariantBlock(String name) {
    super(name);

    this.map = new Object2ObjectOpenHashMap<>();
  }


  public Block get(T type) {
    var block = map.get(type);
    if (block == null) {
      throw new RuntimeException(String.format("Block is null: %s, %s", this, type));
    }
    return block.getBlock();
  }
}
