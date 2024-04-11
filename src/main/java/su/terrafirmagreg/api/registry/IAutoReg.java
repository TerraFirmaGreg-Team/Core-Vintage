package su.terrafirmagreg.api.registry;

import su.terrafirmagreg.api.spi.item.IOreDict;

import net.minecraft.item.ItemBlock;


import net.dries007.tfc.api.capability.size.IItemSize;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IAutoReg extends IOreDict, IItemSize {

    /**
     * Возвращает объект ItemBlock, связанный с данным блоком.
     *
     * @return объект ItemBlock или null, если объект не определен
     */

    default @Nullable ItemBlock getItemBlock() {
        return null;
    }

    @NotNull
    String getName();
}
