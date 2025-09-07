package su.terrafirmagreg.api.library.types.type;

import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.api.IContentEntry;
import su.terrafirmagreg.modules.soil.feature.soiltype.types.type.SoilType;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
public abstract class Type<T extends Type<T>> implements Comparable<Type<T>> {

  private static final Map<String, Set<Type<?>>> MAP = new Object2ObjectOpenHashMap<>();

  protected final String nameType;
  protected final String name;

  protected Type(ResourceLocation name) {
    this(name.getNamespace(), name.getPath());
  }

  protected Type(String nameType, String name) {
    this.nameType = nameType;
    this.name = name;

    if (name.isEmpty()) {
      throw new RuntimeException(String.format("Type name must contain any character: [%s]", name));
    }
    MAP.computeIfAbsent(nameType, s -> new HashSet<>()).add(this);
  }

  @NotNull
  public static Type<? extends Type<?>> valueOf(String nameType, int i) {
    var types = MAP.get(nameType);
    var values = new Type<?>[types.size()];
    values = types.toArray(values);

    return i >= 0 && i < values.length ? values[i] : values[i % values.length];
  }

  public static int indexOf(String nameType, SoilType type) {
    var types = MAP.get(nameType);
    return new ArrayList<>(types).indexOf(type);
  }

  @Nullable
  public static Type<? extends Type<?>> getByName(String nameType, String name) {
    var types = MAP.get(nameType);
    return types
      .stream()
      .filter(s -> s.getName().equals(name))
      .findFirst()
      .orElse(null);
  }

  public static boolean isType(Type<?> typeIn, Type<?>... types) {
    for (var type : types) {
      if (typeIn == type) {
        return true;
      }
    }
    return false;
  }

  public ResourceLocation getTexture(Variant<?, T> variant) {
    return ModUtils.resource(String.format("textures/blocks/%s/%s/%s.png", nameType, variant, this));
  }

  public ResourceLocation getTexture(String variant) {
    return ModUtils.resource(String.format("textures/blocks/%s/%s/%s.png", nameType, variant, this));
  }

  public String getResource(String variant) {
    return String.format("%s/%s", nameType, variant);
  }

  public String getLocalizedName() {
    return ModUtils.localize(ModUtils.localize("type"), nameType, name);
  }

  public String getRegistryKey(String variant) {
    return String.format("%s/%s", variant, this);
  }

  public String getRegistryKey(Variant<?, T> variant) {
    return String.format("%s/%s", variant, this);
  }

  public String getRegistryKey(Block model, String variant) {
    if (model instanceof IContentEntry<?, ?> entry) {
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
