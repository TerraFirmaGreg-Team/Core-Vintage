package su.terrafirmagreg.api.lib.collection;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;


import java.util.ArrayList;

public class RegistryList<T extends IForgeRegistryEntry<T>> extends ArrayList<T> {

    public static <T extends IForgeRegistryEntry<T>> RegistryList<T> create() {
        return new RegistryList<>();
    }

    public void register(IForgeRegistry<T> registry) {
        this.forEach(registry::register);
    }

    public void register(RegistryEvent.Register<T> registry) {
        this.forEach(registry.getRegistry()::register);
    }

}
