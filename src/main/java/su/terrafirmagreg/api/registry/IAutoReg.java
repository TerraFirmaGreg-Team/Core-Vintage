package su.terrafirmagreg.api.registry;

import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface IAutoReg extends IOreDict {

	/**
	 * Возвращает объект ItemBlock, связанный с данным блоком.
	 *
	 * @return объект ItemBlock или null, если объект не определен
	 */
	@Nullable
	default ItemBlock getItemBlock() {
		return null;
	}

	@NotNull
	String getName();
}
