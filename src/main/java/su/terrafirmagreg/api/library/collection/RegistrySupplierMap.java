package su.terrafirmagreg.api.library.collection;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import su.terrafirmagreg.framework.registry.RegistryManager;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class RegistrySupplierMap<T extends IForgeRegistryEntry<T>> extends Object2ObjectLinkedOpenHashMap<ResourceLocation, Supplier<T>> {

  public static <E extends IForgeRegistryEntry<E>> RegistrySupplierMap<E> create() {
    return new RegistrySupplierMap<>();
  }

  public void register(RegistryEvent.Register<T> event) {
    this.forEach((identifier, supplier) -> {
      T object = supplier.get();
      if (!identifier.equals(object.getRegistryName())) {
        object.setRegistryName(identifier);
      }

      RegistryManager.LOGGER.debug("Registry {}: {}", object.getRegistryType().getSimpleName(), identifier);
      event.getRegistry().register(object);
    });
  }

  public void register(final Consumer<T> consumer) {
    this.values().forEach(supplier -> consumer.accept(supplier.get()));
  }


}
