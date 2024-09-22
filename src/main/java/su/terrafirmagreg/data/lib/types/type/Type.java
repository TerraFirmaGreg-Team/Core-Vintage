package su.terrafirmagreg.data.lib.types.type;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public abstract class Type<T> implements Comparable<Type<T>> {

  protected final String name;

  protected Type(String name) {
    this.name = name;

    if (name.isEmpty()) {
      throw new RuntimeException(String.format("Type name must contain any character: [%s]", name));
    }
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
