package su.terrafirmagreg.framework.manager.registry;

import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RegistryMap {

  private final Map<Class<? extends IForgeRegistryEntry<?>>, List<RegistryWrapper>> register_map = new Object2ObjectOpenHashMap<>();

  public static RegistryMap of() {
    return new RegistryMap();
  }

  public <T extends IForgeRegistryEntry<T>> void addEntry(Class<T> aClass, RegistryWrapper wrapper) {

    getEntry(aClass).add(wrapper);
  }

  public <T extends IForgeRegistryEntry<T>> List<RegistryWrapper> getEntry(Class<T> aClass) {

    return register_map.computeIfAbsent(aClass, o -> new LinkedList<>());
  }


  @SuppressWarnings("unchecked")
  public <T extends IForgeRegistryEntry<T>> void forEachEntry(Class<T> aClass, final Consumer<T> consumer) {

    getEntry(aClass).forEach(wrapper -> consumer.accept((T) wrapper));
  }

  @Data(staticConstructor = "of")
  public static class RegistryWrapper {

    private final String name;
    private final IForgeRegistryEntry<?> entry;
  }
}
