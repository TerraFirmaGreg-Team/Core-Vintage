package su.terrafirmagreg.api.library.types.variant;

import net.minecraft.block.state.IBlockState;

import su.terrafirmagreg.api.library.types.type.Type;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public abstract class Variant<V, T extends Type<T>> implements Comparable<Variant<V, T>> {

  protected final String name;

  protected Variant(String name) {
    this.name = name;

    if (name.isEmpty()) {
      throw new RuntimeException(String.format("Variant name must contain any character: [%s]", name));
    }
  }

  public static boolean isVariant(IBlockState blockState, Variant<?, ?>... variants) {
    if (blockState.getBlock() instanceof IVariant<?> variantIn) {
      return isVariant(variantIn.getVariant(), variants);
    }
    return false;
  }

  public static boolean isVariant(Variant<?, ?> variantIn, Variant<?, ?>... variants) {
    for (var variant : variants) {
      if (variantIn == variant) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return name;
  }


  @Override
  public int compareTo(@NotNull Variant<V, T> type) {
    return this.name.compareTo(type.getName());
  }

  public abstract String getRegistryKey(T type);

  public abstract String getLocalizedName();
}
