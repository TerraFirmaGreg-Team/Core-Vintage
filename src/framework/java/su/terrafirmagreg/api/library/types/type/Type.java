package su.terrafirmagreg.api.library.types.type;

import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public abstract class Type<T extends Type<T>> implements Comparable<Type<T>> {

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

  public String getResource(String variant) {
    return String.format("%s/%s", nameType, variant);
  }
  
  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("%s.type.%s.name", nameType, this)).getFormattedText();
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
