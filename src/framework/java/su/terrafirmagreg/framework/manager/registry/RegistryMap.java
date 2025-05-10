package su.terrafirmagreg.framework.manager.registry;

import su.terrafirmagreg.framework.manager.registry.RegistryMap.RegistryWrapper;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

public class RegistryMap extends Object2ObjectOpenHashMap<Class<? extends IForgeRegistryEntry<?>>, List<RegistryWrapper>> {

  public static RegistryMap of() {
    return new RegistryMap();
  }

  public <T extends IForgeRegistryEntry<T>> List<RegistryWrapper> computeIfAbsent(Class<T> registry) {

    return super.computeIfAbsent(registry, o -> new LinkedList<>());
  }

  public <T extends IForgeRegistryEntry<T>> void computeIfAbsent(Class<T> registry, RegistryWrapper wrapper) {

    computeIfAbsent(registry).add(wrapper);
  }

  public <T extends IForgeRegistryEntry<T>> List<RegistryWrapper> get(IForgeRegistry<T> forgeRegistry) {

    return this.computeIfAbsent(forgeRegistry.getRegistrySuperType());
  }


  @Data(staticConstructor = "of")
  public static class RegistryWrapper {

    private final ResourceLocation identifier;
    private final IForgeRegistryEntry<?> entry;
  }
}
