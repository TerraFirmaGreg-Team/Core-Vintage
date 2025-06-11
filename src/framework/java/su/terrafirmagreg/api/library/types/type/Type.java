package su.terrafirmagreg.api.library.types.type;

import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;

import net.minecraft.block.Block;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public abstract class Type<T extends Type<T>> implements Comparable<Type<T>> {

  protected final String name;

  protected Type(String name) {
    this.name = name;

    if (name.isEmpty()) {
      throw new RuntimeException(String.format("Type name must contain any character: [%s]", name));
    }
  }

  public static boolean isType(Type<?> typeIn, Type<?>... types) {
    for (var type : types) {
      if (typeIn == type) {
        return true;
      }
    }
    return false;
  }

  public String getRegistryKey(String variant) {
    return String.format("%s/%s", variant, this);
  }

  public String getRegistryKey(Variant<?, T> variant) {
    return String.format("%s/%s", variant, this);
  }

  public String getRegistryKey(Block model, String variant) {
    if (model instanceof IRegistryEntry<?, ?> entry) {
      return String.format("%s/%s/%s", entry.getSettings().getRegistryKey(), variant, this);
    }
    return getRegistryKey(variant);
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int compareTo(@NotNull Type<T> type) {
    return this.name.compareTo(type.getName());
  }

}
