package su.terrafirmagreg.modules.soil.api.types.type;

import org.jetbrains.annotations.NotNull;

public interface ISoilType {

	/**
	 * Возвращает тип почвы.
	 *
	 * @return Тип почвы.
	 */
	@NotNull
	SoilType getType();
}
