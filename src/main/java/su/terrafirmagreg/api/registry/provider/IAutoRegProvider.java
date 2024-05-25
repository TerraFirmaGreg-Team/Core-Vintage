package su.terrafirmagreg.api.registry.provider;

import net.dries007.tfc.api.capability.size.IItemSize;

public interface IAutoRegProvider extends IOreDictProvider, IItemSize {

    default String getRegistryKey() {
        throw new IllegalArgumentException("Must override getName()");
    }

}
