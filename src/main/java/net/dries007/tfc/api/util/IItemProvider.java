package net.dries007.tfc.api.util;

import net.minecraft.item.ItemBlock;

import javax.annotation.Nullable;

/**
 * Интерфейс IItemProvider предоставляет метод для получения объекта ItemBlock.
 */
public interface IItemProvider {

    /**
     * Возвращает объект ItemBlock, связанный с данным провайдером.
     *
     * @return объект ItemBlock или null, если объект не определен
     */
    @Nullable
    ItemBlock getItemBlock();
}
