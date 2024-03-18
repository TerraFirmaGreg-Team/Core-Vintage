package su.terrafirmagreg.modules.core.api.capabilities.heat;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.util.ModUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class HeatCapability implements IHeatCapability {

	/**
	 * Ключ для определения Capability работы с тепловыми свойствами предметов.
	 */
	public static final ResourceLocation KEY = ModUtils.getID("heat_capability");
	/**
	 * Карты, содержащие пользовательские предметы и их тепловые свойства.
	 */
	public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();
	/**
	 * Переменная для работы с возможностью работы с тепловыми свойствами предметов.
	 */
	@CapabilityInject(IHeatCapability.class)
	public static Capability<IHeatCapability> HEAT_CAPABILITY;
	// Это "константы". Некоторые реализации могут захотеть изменить их на основе других факторов. (См. ItemMold)
	protected float heatCapacity;
	protected float meltTemp;

	// Это значения из последнего обновления. Они обновляются при чтении из NBT или при установке температуры вручную.
	// Обратите внимание, что если температура == 0, lastUpdateTick должен установить себя в -1,
	// чтобы сохранить совместимость Capability - т.е. возможность складывания.
	protected float temperature;
	protected long lastUpdateTick;

	/**
	 * Возвращает возможность управления тепловыми свойствами для указанного предмета.
	 *
	 * @param stack предмет, для которого требуется получить данные о температуре
	 * @return возможность управления тепловыми свойствами предмета
	 */
	public static IHeatCapability getHeatData(ItemStack stack) {
		return stack.getCapability(HEAT_CAPABILITY, null);
	}


	/**
	 * Вспомогательный метод для изменения температуры к определенному значению без перегрева и дрожания.
	 *
	 * @param temp   текущая температура
	 * @param target целевая температура
	 * @param delta  положительное изменение температуры
	 * @return новая температура
	 */
	public static float adjustTempTowards(float temp, float target, float delta) {
		return adjustTempTowards(temp, target, delta, delta);
	}

	/**
	 * Вспомогательный метод для изменения температуры к определенному значению без перегрева и дрожания.
	 *
	 * @param temp          текущая температура
	 * @param target        целевая температура
	 * @param deltaPositive положительное изменение температуры
	 * @param deltaNegative отрицательное изменение температуры
	 * @return новая температура
	 */
	public static float adjustTempTowards(float temp, float target, float deltaPositive, float deltaNegative) {
		if (temp < target) {
			return Math.min(temp + deltaPositive, target);
		} else if (temp > target) {
			return Math.max(temp - deltaNegative, target);
		} else {
			return target;
		}
	}

	/**
	 * Метод для изменения температуры на основе теплоемкости и времени обновления.
	 *
	 * @param temp             текущая температура
	 * @param heatCapacity     теплоемкость
	 * @param ticksSinceUpdate время с последнего обновления
	 * @return новая температура
	 */
	public static float adjustTemp(float temp, float heatCapacity, long ticksSinceUpdate) {
		if (ticksSinceUpdate <= 0) return temp;
		final float newTemp = temp - heatCapacity * (float) ticksSinceUpdate * (float) ConfigTFC.Devices.TEMPERATURE.globalModifier;
		return newTemp < 0 ? 0 : newTemp;
	}

	/**
	 * Метод для увеличения температуры предмета.
	 *
	 * @param instance экземпляр предмета
	 */
	public static void addTemp(IHeatCapability instance) {
		// Default modifier = 3 (2x normal cooling)
		addTemp(instance, 3);
	}

	/**
	 * Метод для увеличения температуры предмета с заданным модификатором.
	 *
	 * @param instance экземпляр предмета
	 * @param modifier модификатор увеличения температуры
	 */
	public static void addTemp(IHeatCapability instance, float modifier) {
		final float temp = instance.getTemperature() + modifier * instance.getHeatCapacity() * (float) ConfigTFC.Devices.TEMPERATURE.globalModifier;
		instance.setTemperature(temp);
	}

	/**
	 * Метод для приближения температуры предмета к целевой температуре.
	 *
	 * @param temp         текущая температура
	 * @param burnTemp     температура горения
	 * @param airTicks     количество тиков нахождения предмета на воздухе
	 * @param maxTempBonus максимальное дополнительное значение температуры
	 * @return новая температура
	 */
	public static float adjustToTargetTemperature(float temp, float burnTemp, int airTicks, int maxTempBonus) {
		boolean hasAir = airTicks > 0;
		float targetTemperature = burnTemp + (hasAir ? MathHelper.clamp(burnTemp, 0, maxTempBonus) : 0);

		if (targetTemperature > 1601)
			targetTemperature = 1601;

		if (temp != targetTemperature) {
			float delta = (float) ConfigTFC.Devices.TEMPERATURE.heatingModifier;
			return adjustTempTowards(temp, targetTemperature, delta * (hasAir ? 2 : 1), delta * (hasAir ? 0.5f : 1));
		}
		return temp;
	}

	/**
	 * Метод для получения пользовательских тепловых свойств предмета.
	 *
	 * @param stack предмет
	 * @return тепловые свойства предмета
	 */
	@Nullable
	public static ICapabilityProvider getCustomHeat(ItemStack stack) {
		Set<IIngredient<ItemStack>> itemItemSet = CUSTOM_ITEMS.keySet();
		for (IIngredient<ItemStack> ingredient : itemItemSet) {
			if (ingredient.testIgnoreCount(stack)) {
				return CUSTOM_ITEMS.get(ingredient).get();
			}
		}

		return null;
	}

	/**
	 * Возвращает текущую температуру, отображаемую наружу. Она может отличаться от внутреннего значения температуры или значения, сохраненного в NBT.
	 * Примечание: если вы проверяете температуру внутри, НЕ используйте temperature, используйте этот метод,
	 * так как temperature не представляет текущую температуру.
	 *
	 * @return Текущая температура
	 */
	@Override
	public float getTemperature() {
		return adjustTemp(temperature, heatCapacity, CalendarTFC.PLAYER_TIME.getTicks() - lastUpdateTick);
	}

	/**
	 * Обновляет температуру и сохраняет метку времени последнего обновления
	 *
	 * @param temperature Температура для установки. Между 0 и 1600
	 */
	@Override
	public void setTemperature(float temperature) {
		this.temperature = temperature;
		this.lastUpdateTick = CalendarTFC.PLAYER_TIME.getTicks();
	}

	/**
	 * Возвращает теплоемкость объекта
	 *
	 * @return Теплоемкость
	 */
	@Override
	public float getHeatCapacity() {
		return heatCapacity;
	}

	/**
	 * Возвращает температуру плавления объекта
	 *
	 * @return Температура плавления
	 */
	@Override
	public float getMeltTemp() {
		return meltTemp;
	}

	/**
	 * Проверяет, находится ли объект в расплавленном состоянии
	 *
	 * @return true, если объект находится в расплавленном состоянии; false в противном случае
	 */
	@Override
	public boolean isMolten() {
		return getTemperature() >= meltTemp;
	}
}
