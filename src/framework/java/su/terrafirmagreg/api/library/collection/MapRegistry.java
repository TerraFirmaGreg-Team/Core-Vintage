package su.terrafirmagreg.api.library.collection;

import net.minecraft.util.ResourceLocation;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Reference2IntMap;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;

import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class MapRegistry<T> implements IdMap<T> {

  private final String name;

  private final BiMap<ResourceLocation, T> map = HashBiMap.create();
  private final Reference2IntMap<T> tToId;
  private final List<T> idToT;

  public MapRegistry(String name) {
    this.name = name;
    this.idToT = Lists.newArrayListWithExpectedSize(32);
    this.tToId = new Reference2IntOpenHashMap<>(32);
    this.tToId.defaultReturnValue(-1);
  }


  public <B extends T> T register(ResourceLocation name, B value) {
    Objects.requireNonNull(name, "The resource name cannot be null");
    Objects.requireNonNull(value, "The value cannot be null");

    if (map.containsKey(name)) {
      throw new IllegalStateException("Cannot register duplicate value " + name);
    }
    this.map.put(name, value);
    this.recomputeIdMappings();
    return value;
  }

  public <B extends T> T register(String name, B value) {
    this.register(new ResourceLocation(name), value);
    return value;
  }

  public void registerAll(Map<ResourceLocation, ? extends T> entries) {
    entries.forEach((name, value) -> {
      if (map.putIfAbsent(name, value) != null) {
        throw new IllegalStateException("Duplicate name: " + name);
      }
    });
    recomputeIdMappings();
  }

  protected void recomputeIdMappings() {
    this.tToId.clear();
    this.idToT.clear();

    var orderedKeys = this.map.keySet()
      .stream()
      .sorted()
      .collect(Collectors.toList());
    int id = 0;
    for (var k : orderedKeys) {
      T value = this.map.get(k);
      if (value == null) {
        continue; //skip nulls
      }
      this.tToId.put(value, id);
      this.idToT.add(value);
      id++;
    }
  }

  @Nullable
  public T getValue(ResourceLocation name) {
    return this.map.get(name);
  }

  @Nullable
  public T getValue(String name) {
    return this.getValue(new ResourceLocation(name));
  }

  @Nullable
  public ResourceLocation getKey(T value) {
    return this.map.inverse().get(value);
  }

  public Set<ResourceLocation> keySet() {
    return this.map.keySet();
  }

  public Set<T> getValues() {
    return this.map.values();
  }

  public T getValueOrDefault(ResourceLocation parse, T defaultType) {
    return this.map.getOrDefault(parse, defaultType);
  }

  public Set<Map.Entry<ResourceLocation, T>> getEntries() {
    return this.map.entrySet();
  }

  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  public int size() {
    return this.map.size();
  }

  public boolean containsKey(ResourceLocation name) {
    return this.map.containsKey(name);
  }


  public void clear() {
    this.map.clear();
  }


  public int getId(T value) {
    return this.tToId.getInt(value);
  }

  @Nullable
  public final T byId(int id) {
    return id >= 0 && id < this.idToT.size() ? this.idToT.get(id) : null;
  }

  public Iterator<T> iterator() {
    return Iterators.filter(this.idToT.iterator(), Objects::nonNull);
  }

  public boolean contains(int id) {
    return this.byId(id) != null;
  }
}
