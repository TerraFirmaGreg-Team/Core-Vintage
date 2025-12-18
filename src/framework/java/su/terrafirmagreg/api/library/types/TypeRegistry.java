package su.terrafirmagreg.api.library.types;

import su.terrafirmagreg.api.library.collection.IdMap;
import su.terrafirmagreg.api.library.collection.MapRegistry;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public abstract class TypeRegistry<T extends Type> implements IdMap<T> {

  private static final Map<Class<? extends Type>, TypeRegistry<?>> REGISTRIES_BY_CLASS = new LinkedHashMap<>();


  protected boolean frozen = false;

  private final Class<T> typeClass;
  private final String name;
  private final MapRegistry<T> valuesReg;

  protected TypeRegistry(Class<T> typeClass, String name) {
    this.typeClass = typeClass;
    this.name = name;
    this.valuesReg = new MapRegistry<>(name);

  }

  @Override
  public @NotNull Iterator<T> iterator() {
    return valuesReg.iterator();
  }

  @Override
  public int size() {
    return valuesReg.size();
  }

  @Override
  public @Nullable T byId(int id) {
    return valuesReg.byId(id);
  }

  @Override
  public int getId(T value) {
    return valuesReg.getId(value);
  }

  public boolean isFrozen() {
    return frozen;
  }

  public Class<T> getType() {
    return typeClass;
  }

  @Nullable
  public T get(ResourceLocation res) {
    if (!frozen) {
      throw new AssertionError("Tried to get an object from block set registry before the registry was finalized.");
    }
    return valuesReg.getValue(res);
  }

  public T getOrDefault(ResourceLocation res) {
    if (!frozen) {
      throw new AssertionError("Tried to get an object from block set registry before the registry was finalized.");
    }
    return valuesReg.getValueOrDefault(res, this.getDefaultType());
  }

  public ResourceLocation getKey(T input) {
    return valuesReg.getKey(input);
  }

  public Collection<T> getValues() {
    return valuesReg.getValues();
  }

  public String typeName() {
    return name;
  }

  protected T register(T newType) {
    if (frozen) {
      throw new UnsupportedOperationException("Tried to register a block types after registry events");
    }
    //ignore duplicates
    if (!valuesReg.containsKey(newType.id)) {
      valuesReg.register(newType.id, newType);
    }
    return newType;
  }

  public abstract T getDefaultType();

  protected abstract Optional<T> detectTypeFromBlock(Block block, ResourceLocation blockId);
}
