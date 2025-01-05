package su.terrafirmagreg.api.library.types.category;

import net.minecraft.block.state.IBlockState;

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

  public static boolean isCategory(IBlockState blockState, Category<?>... categories) {
    if (blockState.getBlock() instanceof ICategory<?> variantIn) {
      return isCategory(variantIn.getCategory(), categories);
    }
    return false;
  }

  public static boolean isCategory(Category<?> categoryIn, Category<?>... categories) {
    for (var category : categories) {
      if (categoryIn == category) {
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
  public int compareTo(@NotNull Category<T> type) {
    return this.name.compareTo(type.getName());
  }
}
