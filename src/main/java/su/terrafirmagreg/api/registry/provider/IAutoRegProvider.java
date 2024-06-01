package su.terrafirmagreg.api.registry.provider;

import su.terrafirmagreg.api.capabilities.size.ICapabilitySize;

public interface IAutoRegProvider extends IOreDictProvider, ICapabilitySize {

    default String getRegistryKey() {
        throw new IllegalArgumentException("Must override");
    }

}
