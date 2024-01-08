package su.terrafirmagreg.api.util;

import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.Nullable;

/**
 * Интерфейс IItemProvider предоставляет метод для получения объекта ItemBlock.
 */
@FunctionalInterface
public interface IItemProvider {

	/**
	 * Возвращает объект ItemBlock, связанный с данным провайдером.
	 *
	 * @return объект ItemBlock или null, если объект не определен
	 */
	@Nullable
	ItemBlock getItemBlock();
}
