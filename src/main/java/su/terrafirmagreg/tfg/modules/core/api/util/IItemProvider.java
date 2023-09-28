package su.terrafirmagreg.tfg.modules.core.api.util;

import su.terrafirmagreg.tfg.modules.core.api.block.itemblocks.ItemBlockBase;

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
    ItemBlockBase getItemBlock();
}
