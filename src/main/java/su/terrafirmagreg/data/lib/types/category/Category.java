package su.terrafirmagreg.data.lib.types.category;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public abstract class Category<T> implements Comparable<Category<T>> {

  protected final String name;

  protected Category(String name) {
    this.name = name;

    if (name.isEmpty()) {
      throw new RuntimeException(String.format("Category name must contain any character: [%s]", name));
    }
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public int compareTo(@NotNull Category<T> type) {
    return this.name.compareTo(type.getName());
  }
}
