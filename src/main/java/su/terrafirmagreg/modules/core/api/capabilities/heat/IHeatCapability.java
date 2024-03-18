package su.terrafirmagreg.modules.core.api.capabilities.heat;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Рекомендуется наследоваться от {@link HeatCapability}, а не реализовывать это напрямую.
 * Если вы все же реализуете это, посмотрите на ItemHeatHandler, чтобы увидеть, как тепло распадается со временем.
 */
public interface IHeatCapability {
	/**
	 * Получить текущую температуру. Должен вызывать {@link HeatCapability#adjustTemp(float, float, long)} внутри себя.
	 *
	 * @return текущая температура
	 */
	float getTemperature();

	/**
	 * Установить температуру. Используется для изменения температуры.
	 *
	 * @param temperature температура для установки
	 */
	void setTemperature(float temperature);

	/**
	 * Получить теплоемкость. (Мера того, насколько быстро нагревается или охлаждается предмет)
	 * Реализация остается на усмотрение объекта нагревания. (См. TEFirePit, например)
	 *
	 * @return теплоемкость. Обычно от 0 до 1, может быть за пределами этого диапазона, должна быть неотрицательной
	 */
	float getHeatCapacity();

	/**
	 * Получить температуру плавления предмета.
	 * В зависимости от предмета это может не иметь смысла.
	 *
	 * @return температура, при которой предмет должен плавиться
	 */
	float getMeltTemp();

	/**
	 * Если объект может плавиться / превращаться, вернуть, превращен ли он
	 * Это может иметь различные значения в зависимости от объекта
	 *
	 * @return превращен ли объект
	 */
	default boolean isMolten() {
		return getTemperature() > getMeltTemp();
	}

	/**
	 * Добавляет всплывающую подсказку с информацией о тепле при наведении.
	 * При переопределении этого метода для отображения дополнительной информации, используйте IHeatCapability.super.addHeatInfo()
	 *
	 * @param stack стек, к которому добавляется информация
	 * @param text  список подсказок
	 */
	@SideOnly(Side.CLIENT)
	default void addHeatInfo(@Nonnull ItemStack stack, @Nonnull List<String> text) {
		String tooltip = Heat.getTooltip(getTemperature());
		if (tooltip != null) {
			text.add("");
			text.add(I18n.format("tfg.tooltip.temperature", tooltip));
		}
	}
}
