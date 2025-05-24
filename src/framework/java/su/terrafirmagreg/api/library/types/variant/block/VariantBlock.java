package su.terrafirmagreg.api.library.types.variant.block;

import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.library.types.variant.IVariant;
import su.terrafirmagreg.api.library.types.variant.Variant;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

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

  public static boolean isVariant(IBlockState blockState, Variant<?, ?>... variants) {

    return isVariant(blockState.getBlock(), variants);
  }

  public static boolean isVariant(Block block, Variant<?, ?>... variants) {
    if (block instanceof IVariant<?> variantIn) {
      return isVariant(variantIn.getVariant(), variants);
    }
    return false;
  }


  public Block get(T type) {
    var block = map.get(type);
    if (block == null) {
      throw new RuntimeException(String.format("Block is null: %s, %s", this, type));
    }
    return block;
  }
}
