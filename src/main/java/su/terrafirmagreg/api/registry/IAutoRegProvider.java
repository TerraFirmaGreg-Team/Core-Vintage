package su.terrafirmagreg.api.registry;

import su.terrafirmagreg.api.spi.item.IOreDictProvider;


import net.dries007.tfc.api.capability.size.IItemSize;

public interface IAutoRegProvider extends IOreDictProvider, IItemSize {

    default String getName() {
        throw new IllegalArgumentException("Must override getName()");
    }

}
