package su.terrafirmagreg.framework.manager.registry;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class RegistryMap extends Object2ObjectOpenHashMap<Class<? extends IForgeRegistryEntry<?>>, List<IRegistryEntry<?, ?>>> {

  public static RegistryMap of() {
    return new RegistryMap();
  }

  public <T extends IForgeRegistryEntry<T>> List<IRegistryEntry<?, ?>> computeIfAbsent(Class<T> registry) {

    return super.computeIfAbsent(registry, o -> new LinkedList<>());
  }

  public <T extends IForgeRegistryEntry<T>> void computeIfAbsent(Class<T> registry, IRegistryEntry<?, ?> wrapper) {

    computeIfAbsent(registry).add(wrapper);
  }

  public <T extends IForgeRegistryEntry<T>> List<IRegistryEntry<?, ?>> get(IForgeRegistry<T> forgeRegistry) {

    return this.computeIfAbsent(forgeRegistry.getRegistrySuperType());
  }

  @SuppressWarnings("unchecked")
  public <T extends IForgeRegistryEntry<T>> void register(Class<T> registry, final Consumer<T> consumer) {

    this.get(registry).forEach(wrapper -> consumer.accept((T) wrapper));
  }


  @Data(staticConstructor = "of")
  public static class RegistryWrapper {

    private final ResourceLocation identifier;
    private final IForgeRegistryEntry<?> entry;
  }
}
