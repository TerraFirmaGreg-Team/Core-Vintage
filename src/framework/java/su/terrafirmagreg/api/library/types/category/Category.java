package su.terrafirmagreg.api.library.types.category;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
public abstract class Category<T> implements Comparable<Category<T>> {

  private static final Map<String, Set<Category<?>>> MAP = new Object2ObjectOpenHashMap<>();

  protected final String nameCategory;
  protected final String name;

  protected Category(ResourceLocation name) {
    this(name.getNamespace(), name.getPath());
  }

  protected Category(String nameCategory, String name) {

    this.nameCategory = nameCategory;
    this.name = name;

    if (name.isEmpty()) {
      throw new RuntimeException(String.format("Category name must contain any character: [%s]", name));
    }

    MAP.computeIfAbsent(nameCategory, s -> new ObjectOpenHashSet<>()).add(this);
  }


  public static boolean isCategory(Category<?> categoryIn, Category<?>... categories) {
    for (var category : categories) {
      if (categoryIn == category) {
        return true;
      }
    }
    return false;
  }

  public String getLocalizedName() {
    return ModUtils.localize(ModUtils.localize("category"), nameCategory, name);
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
