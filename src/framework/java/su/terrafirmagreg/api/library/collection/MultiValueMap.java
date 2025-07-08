package su.terrafirmagreg.api.library.collection;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.function.BiConsumer;

public class MultiValueMap<K, V> {

  private final Object2ObjectOpenHashMap<K, ObjectArrayList<V>> internalMap;

  public MultiValueMap() {
    this.internalMap = new Object2ObjectOpenHashMap<>();
  }

  public void put(K key, V value) {
    internalMap.computeIfAbsent(key, k -> new ObjectArrayList<>()).add(value);
  }


  public ObjectArrayList<V> get(K key) {
    return internalMap.getOrDefault(key, new ObjectArrayList<>());
  }


  public boolean removeAll(K key) {
    return internalMap.remove(key) != null;
  }

  public boolean remove(K key, V value) {
    ObjectArrayList<V> values = internalMap.get(key);
    if (values == null) {
      return false;
    }

    boolean removed = values.remove(value);
    if (removed && values.isEmpty()) {
      internalMap.remove(key);
    }
    return removed;
  }

  public boolean containsKey(K key) {
    return internalMap.containsKey(key);
  }

  public void forEach(BiConsumer<K, ObjectArrayList<V>> action) {
    internalMap.forEach(action);
  }
}
