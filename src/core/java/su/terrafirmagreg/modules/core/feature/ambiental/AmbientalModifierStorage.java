package su.terrafirmagreg.modules.core.feature.ambiental;

import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierBase;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AmbientalModifierStorage implements Iterable<ModifierBase> {

  private Map<String, ModifierBase> map = new Object2ObjectOpenHashMap<>();

  public void keepOnlyNEach(int n) {

    Map<String, List<ModifierBase>> grouped = map.values().stream()
      .collect(Collectors.groupingBy(ModifierBase::getName));
    this.map = grouped
      .entrySet()
      .stream()
      .flatMap(entry ->
        entry.getValue()
          .stream()
          .sorted(Comparator.reverseOrder())
          .limit(n)
      ).collect(Collectors.toMap(ModifierBase::getName, v -> v));
  }

  private ModifierBase put(String key, ModifierBase value) {
    if ((value.getChange() == 0f && value.getPotency() == 0f)) {
      return null;
    }
    ModifierBase modifier = map.get(key);
    if (modifier != null) {
      modifier.absorb(value);
      return modifier;
    } else {
      return map.put(key, value);
    }
  }

  public ModifierBase add(ModifierBase value) {
    if (value == null) {
      return null;
    }
    return this.put(value.getName(), value);
  }

  public <T extends ModifierBase> void add(Optional<T> modifier) {
    modifier.ifPresent(this::add);
  }

  public boolean contains(String key) {
    return map.containsKey(key);
  }

  public boolean contains(ModifierBase value) {
    return map.containsValue(value);
  }

  public ModifierBase get(String key) {
    return map.get(key);
  }

  public float getTotalPotency() {
    float potency = 1f;
    for (var entry : map.values()) {
      potency += entry.getPotency();
    }
    return potency;
  }

  public float getTargetTemperature() {
    float change = 0f;
    for (var entry : map.values()) {
      change += entry.getChange();
    }
    return change;
  }

  public void forEach(Consumer<ModifierBase> func) {
    map.forEach((k, v) -> {
      func.accept(v);
    });
  }

  @Override
  public @NotNull Iterator<ModifierBase> iterator() {
    Map<String, ModifierBase> map1 = map;
    return new Iterator<>() {

      private final Iterator<Map.Entry<String, ModifierBase>> mapIterator = map1.entrySet().iterator();

      @Override
      public boolean hasNext() {
        return mapIterator.hasNext();
      }

      @Override
      public ModifierBase next() {
        return mapIterator.next().getValue();
      }
    };
  }
}
