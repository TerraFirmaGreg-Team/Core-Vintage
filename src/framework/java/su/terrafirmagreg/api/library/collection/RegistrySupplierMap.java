package su.terrafirmagreg.api.library.collection;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.ContentManager;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RegistrySupplierMap<T extends IForgeRegistryEntry<T>> extends Object2ObjectLinkedOpenHashMap<String, Supplier<T>> {

  public static <E extends IForgeRegistryEntry<E>> RegistrySupplierMap<E> create() {
    return new RegistrySupplierMap<>();
  }

  public void register(RegistryEvent.Register<T> event) {
    this.forEach((identifier, supplier) -> {
      T object = supplier.get();
      var regName = ModUtils.resource(object.getRegistryType().getSimpleName(), identifier);
      if (!regName.equals(object.getRegistryName())) {
        object.setRegistryName(regName);
      }

      ContentManager.LOGGER.debug("Registry {}: {}", object.getRegistryType().getSimpleName(), regName);
      event.getRegistry().register(object);
    });
  }

  public void register(final Consumer<T> consumer) {
    this.values().forEach(supplier -> consumer.accept(supplier.get()));
  }

  public interface SupplierMap<T extends IForgeRegistryEntry<T>> extends Map<String, Supplier<T>> {

  }


}
