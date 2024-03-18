package su.terrafirmagreg.modules.core.api.capabilities.heat;

import lombok.Getter;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.config.OreTooltipMode;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;

public enum Heat {
	/**
	 * Теплый диапазон
	 */
	WARMING(1f, 80f, TextFormatting.GRAY, TextFormatting.DARK_GRAY, 5592405),

	/**
	 * Горячий диапазон
	 */
	HOT(80f, 210f, TextFormatting.GRAY, TextFormatting.DARK_GRAY, 5592405),

	/**
	 * Очень горячий диапазон
	 */
	VERY_HOT(210f, 480f, TextFormatting.GRAY, TextFormatting.DARK_GRAY, 5592405),

	/**
	 * Слабый красный диапазон
	 */
	FAINT_RED(480f, 580f, TextFormatting.DARK_RED, 11141120),

	/**
	 * Темно-красный диапазон
	 */
	DARK_RED(580f, 730f, TextFormatting.DARK_RED, 11141120),

	/**
	 * Ярко-красный диапазон
	 */
	BRIGHT_RED(730f, 930f, TextFormatting.RED, 16733525),

	/**
	 * Оранжевый диапазон
	 */
	ORANGE(930f, 1100f, TextFormatting.GOLD, 16755200),

	/**
	 * Желтый диапазон
	 */
	YELLOW(1100f, 1300f, TextFormatting.YELLOW, 16777045),

	/**
	 * Диапазон светло-желтого цвета
	 */
	YELLOW_WHITE(1300f, 1400f, TextFormatting.YELLOW, 16777045),

	/**
	 * Белый диапазон
	 */
	WHITE(1400f, 1500f, TextFormatting.WHITE, 16777215),

	/**
	 * Блестяще белый диапазон
	 */
	BRILLIANT_WHITE(1500f, 1601f, TextFormatting.WHITE, 16777215);

	private static final Heat[] VALUES = values();

	/**
	 * Форматирование текста
	 */
	final TextFormatting format;

	/**
	 * Альтернативное форматирование текста
	 */
	final TextFormatting alternate;

	/**
	 * Минимальная температура диапазона
	 */
	@Getter
	private final float min;

	/**
	 * Максимальная температура диапазона
	 */
	@Getter
	private final float max;

	/**
	 * Цвет в формате RGBA
	 */
	@Getter
	private final int intColor;

	Heat(float min, float max, TextFormatting format, TextFormatting alternate, int intColor) {
		this.min = min;
		this.max = max;
		this.format = format;
		this.alternate = alternate;
		this.intColor = intColor;
	}

	Heat(float min, float max, TextFormatting format, int intColor) {
		this(min, max, format, format, intColor);
	}


	/**
	 * Возвращает максимально видимую температуру
	 *
	 * @return Максимально видимая температура
	 */
	public static float maxVisibleTemperature() {
		return BRILLIANT_WHITE.getMax();
	}

	/**
	 * Получить диапазон температуры на основе значения
	 *
	 * @param temperature температура
	 * @return объект класса Heat, соответствующий заданной температуре
	 */
	@Nullable
	public static Heat getHeat(float temperature) {
		for (Heat heat : VALUES) {
			if (heat.min <= temperature && temperature < heat.max) {
				return heat;
			}
		}
		if (temperature > BRILLIANT_WHITE.max) {
			// По умолчанию берем "горячее, чем блестяще белый"
			return BRILLIANT_WHITE;
		}
		return null;
	}

	/**
	 * Получить цвет в формате RGBA на основе температуры
	 *
	 * @param temperature температура
	 * @return цвет в формате RGBA
	 */
	public static int getColor(float temperature) {
		var heat = getHeat(temperature);

		if (heat == null) return 0;

		return heat.getIntColor();
	}

	/**
	 * Получить подсказку без цвета на основе температуры
	 *
	 * @param temperature температура
	 * @return подсказка без цвета
	 */
	@Nullable
	public static String getTooltipColorless(float temperature) {
		Heat heat = Heat.getHeat(temperature);
		if (heat != null) {
			StringBuilder b = new StringBuilder();
			b.append(I18n.format(Helpers.getEnumName(heat)));
			if (heat != Heat.BRILLIANT_WHITE) {
				for (int i = 1; i <= 4; i++) {
					if (temperature <= heat.getMin() + ((float) i * 0.2f) * (heat.getMax() - heat.getMin()))
						continue;
					b.append("\u2605");
				}
			}
			return b.toString();
		}
		return null;
	}

	/**
	 * Получить подсказку на основе температуры
	 *
	 * @param temperature температура
	 * @return подсказка
	 */
	@Nullable
	public static String getTooltip(float temperature) {
		Heat heat = Heat.getHeat(temperature);
		String tooltip = getTooltipColorless(temperature);
		if (tooltip != null && heat != null) {
			tooltip = heat.format + tooltip;
			if (ConfigTFC.Client.TOOLTIP.oreTooltipMode == OreTooltipMode.ADVANCED) {
				tooltip = tooltip + " : " + I18n.format("tfg.tooltip.melttemp", Math.round(temperature));
			}

		}
		return tooltip;
	}

	/**
	 * Получить альтернативную подсказку на основе температуры
	 *
	 * @param temperature температура
	 * @return альтернативная подсказка
	 */
	@Nullable
	public static String getTooltipAlternate(float temperature) {
		Heat heat = Heat.getHeat(temperature);
		String tooltip = getTooltipColorless(temperature);
		if (tooltip != null && heat != null) {
			tooltip = heat.alternate + tooltip;
			if (ConfigTFC.Client.TOOLTIP.oreTooltipMode == OreTooltipMode.ADVANCED) {
				tooltip = tooltip + " : " + I18n.format("tfg.tooltip.melttemp", Math.round(temperature));
			}
		}
		return tooltip;
	}

}
