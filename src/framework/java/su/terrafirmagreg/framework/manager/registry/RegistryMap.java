package su.terrafirmagreg.framework.manager.registry;

import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RegistryMap {

  private final Map<Class<? extends IForgeRegistryEntry<?>>, List<IForgeRegistryEntry<?>>> register_map = new Object2ObjectOpenHashMap<>();

  public static RegistryMap of() {
    return new RegistryMap();
  }

  public <T extends IForgeRegistryEntry<T>> void addEntry(Class<T> aClass, IForgeRegistryEntry<?> wrapper) {

    getEntry(aClass).add(wrapper);
  }

  public <T extends IForgeRegistryEntry<T>> List<IForgeRegistryEntry<?>> getEntry(Class<T> aClass) {

    return register_map.computeIfAbsent(aClass, o -> new LinkedList<>());
  }


  @Data(staticConstructor = "of")
  public static class RegistryWrapper {

    private final String name;
    private final IForgeRegistryEntry<?> entry;
  }
}
