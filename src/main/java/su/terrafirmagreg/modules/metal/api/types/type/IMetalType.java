package su.terrafirmagreg.modules.metal.api.types.type;

import org.jetbrains.annotations.NotNull;

public interface IMetalType {

	/**
	 * Возвращает тип металла.
	 *
	 * @return Тип металла.
	 */
	@NotNull
	MetalType getType();
}
