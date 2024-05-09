package su.terrafirmagreg.api.registry;

import su.terrafirmagreg.api.spi.item.IOreDictProvider;

import net.minecraft.item.Item;


import net.dries007.tfc.api.capability.size.IItemSize;

import org.jetbrains.annotations.Nullable;

public interface IAutoReg extends IOreDictProvider, IItemSize {

    /**
     * Возвращает объект ItemBlock, связанный с данным блоком.
     *
     * @return объект ItemBlock или null, если объект не определен
     */

    default @Nullable Item getItemBlock() {
        return null;
    }

    String getName();
}
