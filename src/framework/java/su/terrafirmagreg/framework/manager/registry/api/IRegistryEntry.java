package su.terrafirmagreg.framework.manager.registry.api;

import su.terrafirmagreg.api.library.IBaseEntry;
import su.terrafirmagreg.api.library.IBaseEntry.BaseSettings;

import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IRegistryEntry<T extends BaseSettings<T>, V extends IForgeRegistryEntry.Impl<V>> extends IBaseEntry<T, V> {


  default void postRegister() {}

}
