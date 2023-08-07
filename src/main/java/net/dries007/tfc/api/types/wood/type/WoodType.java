package net.dries007.tfc.api.types.wood.type;

import net.dries007.tfc.api.types.tree.util.ITreeGenerator;
import net.dries007.tfc.types.DefaultTrees;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static net.dries007.tfc.types.DefaultTrees.GEN_NORMAL;

/**
 * Класс WoodType представляет тип дерева с определенными характеристиками.
 */
public class WoodType {

	private static final HashSet<WoodType> woodTypes = new HashSet<>();

	private final int color;
	private final int maxGrowthRadius;
	private final float dominance;
	private final int maxHeight;
	private final int maxDecayDistance;
	private final boolean isConifer;
	private final ITreeGenerator bushGenerator;
	private final boolean canMakeTannin;
	private final float minGrowthTime;
	private final float minTemp;
	private final float maxTemp;
	private final float minRain;
	private final float maxRain;
	private final float minDensity;
	private final float maxDensity;
	private final float burnTemp;
	private final int burnTicks;

	/* This is open to be replaced, i.e. for dynamic trees */
	private final ITreeGenerator generator;

	/**
	 * Создает объект WoodType с заданными характеристиками.
	 *
	 * @param color            цвет дерева
	 * @param generator        генератор, который будет вызываться для создания этого дерева
	 * @param minTemp          минимальная температура
	 * @param maxTemp          максимальная температура
	 * @param minRain          минимальное количество осадков
	 * @param maxRain          максимальное количество осадков
	 * @param minDensity       минимальная плотность. Используйте -1, чтобы получить все значения плотности. 0.1 - значение по умолчанию, чтобы создать области с очень низкой плотностью без деревьев
	 * @param maxDensity       максимальная плотность. Используйте 2, чтобы получить все значения плотности
	 * @param dominance        насколько это дерево выбирается по сравнению с другими деревьями. Диапазон 0 <> 10, где 10 - наиболее распространенное значение
	 * @param maxGrowthRadius  используется для проверки условий роста
	 * @param maxHeight        используется для проверки условий роста
	 * @param maxDecayDistance максимальное расстояние распада для листьев
	 * @param isConifer        todo: сделать так, чтобы это что-то делало
	 * @param bushGenerator    генератор для создания маленьких кустов, null означает, что дерево не будет создавать кусты
	 * @param canMakeTannin    может ли дерево производить дубильные вещества
	 * @param minGrowthTime    количество времени (в игровых днях), необходимое для роста этого дерева
	 * @param burnTemp         температура, при которой дерево будет гореть в костре или подобном устройстве
	 * @param burnTicks        количество тиков, в течение которых дерево будет гореть в костре или подобном устройстве
	 */
	public WoodType(int color, @Nonnull ITreeGenerator generator, float minTemp, float maxTemp,
		float minRain, float maxRain, float minDensity, float maxDensity,
		float dominance, int maxGrowthRadius, int maxHeight, int maxDecayDistance,
		boolean isConifer, @Nullable ITreeGenerator bushGenerator,
		boolean canMakeTannin, float minGrowthTime, float burnTemp, int burnTicks) {
		this.color = color;
		this.minTemp = minTemp;
		this.maxTemp = maxTemp;
		this.minRain = minRain;
		this.maxRain = maxRain;
		this.minDensity = minDensity;
		this.maxDensity = maxDensity;
		this.dominance = dominance;
		this.maxGrowthRadius = maxGrowthRadius;
		this.maxHeight = maxHeight;
		this.maxDecayDistance = maxDecayDistance;
		this.isConifer = isConifer;
		this.minGrowthTime = minGrowthTime;
		this.burnTemp = burnTemp;
		this.burnTicks = burnTicks;
		this.generator = generator;
		this.bushGenerator = bushGenerator;
		this.canMakeTannin = canMakeTannin;

		woodTypes.add(this);
	}

	/**
	 * Возвращает цвет дерева.
	 *
	 * @return цвет дерева
	 */
	public int getColor() {
		return color;
	}

	/**
	 * Возвращает минимальную температуру, при которой дерево может расти.
	 *
	 * @return минимальная температура
	 */
	public float getMinTemp() {
		return minTemp;
	}

	/**
	 * Возвращает максимальную температуру, при которой дерево может расти.
	 *
	 * @return максимальная температура
	 */
	public float getMaxTemp() {
		return maxTemp;
	}

	/**
	 * Возвращает минимальное количество осадков, необходимое для роста дерева.
	 *
	 * @return минимальное количество осадков
	 */
	public float getMinRain() {
		return minRain;
	}

	/**
	 * Возвращает максимальное количество осадков, необходимое для роста дерева.
	 *
	 * @return максимальное количество осадков
	 */
	public float getMaxRain() {
		return maxRain;
	}

	/**
	 * Возвращает минимальную плотность дерева.
	 *
	 * @return минимальная плотность
	 */
	public float getMinDensity() {
		return minDensity;
	}

	/**
	 * Возвращает максимальную плотность дерева.
	 *
	 * @return максимальная плотность
	 */
	public float getMaxDensity() {
		return maxDensity;
	}

	/**
	 * Возвращает насколько это дерево выбирается по сравнению с другими деревьями.
	 *
	 * @return значение доминирования
	 */
	public float getDominance() {
		return dominance;
	}

	/**
	 * Возвращает максимальный радиус роста дерева.
	 *
	 * @return максимальный радиус роста
	 */
	public int getMaxGrowthRadius() {
		return maxGrowthRadius;
	}

	/**
	 * Возвращает максимальную высоту дерева.
	 *
	 * @return максимальная высота
	 */
	public int getMaxHeight() {
		return maxHeight;
	}

	/**
	 * Возвращает максимальное расстояние распада для листьев дерева.
	 *
	 * @return максимальное расстояние распада
	 */
	public int getMaxDecayDistance() {
		return maxDecayDistance;
	}

	/**
	 * Проверяет, является ли данное дерево хвойным.
	 *
	 * @return true, если дерево хвойное, иначе false
	 */
	public boolean isConifer() {
		return isConifer;
	}

	/**
	 * Возвращает генератор для создания дерева.
	 *
	 * @return генератор дерева
	 */
	public ITreeGenerator getGenerator() {
		return generator;
	}

	/**
	 * Возвращает генератор для создания маленьких кустов дерева.
	 *
	 * @return генератор кустов дерева
	 */
	public ITreeGenerator getBushGenerator() {
		return bushGenerator;
	}

	/**
	 * Проверяет, может ли дерево производить дубильные вещества.
	 *
	 * @return true, если дерево может производить дубильные вещества, иначе false
	 */
	public boolean canMakeTannin() {
		return canMakeTannin;
	}

	/**
	 * Возвращает минимальное время роста дерева.
	 *
	 * @return минимальное время роста
	 */
	public float getMinGrowthTime() {
		return minGrowthTime;
	}

	/**
	 * Возвращает температуру, при которой дерево будет гореть в костре или подобном устройстве.
	 *
	 * @return температура горения
	 */
	public float getBurnTemp() {
		return burnTemp;
	}

	/**
	 * Возвращает количество тиков, в течение которых дерево будет гореть в костре или подобном устройстве.
	 *
	 * @return количество тиков горения
	 */
	public int getBurnTicks() {
		return burnTicks;
	}

	/**
	 * Возвращает список всех доступных типов дерева.
	 *
	 * @return список типов дерева
	 */
	public static List<WoodType> getAllWoodTypes() {
		return new ArrayList<>(woodTypes);
	}

	public boolean isValidLocation(float temp, float rain, float density) {
		return minTemp <= temp && maxTemp >= temp && minRain <= rain && maxRain >= rain && minDensity <= density && maxDensity >= density;
	}

	@SideOnly(Side.CLIENT)
	public void addInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (GuiScreen.isShiftKeyDown()) {
			tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.climate_info"));
			tooltip.add(TextFormatting.BLUE + I18n.format("tfc.tooltip.climate_info_rainfall", (int) minRain, (int) maxRain));
			tooltip.add(TextFormatting.GOLD + I18n.format("tfc.tooltip.climate_info_temperature", String.format("%.1f", minTemp), String.format("%.1f", maxTemp)));
		}
		else {
			tooltip.add(TextFormatting.GRAY + I18n.format("tfc.tooltip.hold_shift_for_climate_info"));
		}
	}

	public static class Builder {
		// Поля
		private final int color; // цвет
		private final float minTemp; // минимальная температура
		private final float maxTemp; // максимальная температура
		private final float minRain; // минимальное количество осадков
		private final float maxRain; // максимальное количество осадков
		private ITreeGenerator gen; // генератор деревьев
		private float minDensity; // минимальная плотность
		private float maxDensity; // максимальная плотность
		private float dominance; // доминирование
		private int maxHeight; // максимальная высота
		private int maxGrowthRadius; // максимальный радиус роста
		private int maxDecayDistance; // максимальное расстояние разложения
		private boolean isConifer; // является ли хвойным
		private ITreeGenerator bushGenerator; // генератор кустов
		private boolean canMakeTannin; // может ли производить танин
		private float minGrowthTime; // минимальное время роста
		private float burnTemp; // температура горения
		private int burnTicks; // количество тиков горения

		// Конструктор
		public Builder(int color, float minTemp, float maxTemp, float minRain, float maxRain) {
			this.color = color; // обязательные значения
			this.minTemp = minTemp;
			this.maxTemp = maxTemp;
			this.minRain = minRain;
			this.maxRain = maxRain;
			this.gen = GEN_NORMAL; // Заменить на ген DT по умолчанию, и удалить setGenerator(), так как для кустов вызывается setBushes()
			this.maxGrowthRadius = 1; // значения по умолчанию
			this.dominance = 0.001f * (maxTemp - minTemp) * (maxRain - minRain);
			this.maxHeight = 6;
			this.maxDecayDistance = 4;
			this.isConifer = false;
			this.bushGenerator = null;
			this.canMakeTannin = false;
			this.minGrowthTime = 7;
			this.minDensity = 0.1f;
			this.maxDensity = 2f;
			this.burnTemp = 675;
			this.burnTicks = 1500;
		}

		// Методы для установки значений полей

		// Установить генератор деревьев
		public Builder setGenerator(@Nonnull ITreeGenerator gen) {
			this.gen = gen;
			return this;
		}

		// Установить радиус роста
		public Builder setRadius(int maxGrowthRadius) {
			this.maxGrowthRadius = maxGrowthRadius;
			return this;
		}

		// Установить доминирование
		public Builder setDominance(float dominance) {
			this.dominance = dominance;
			return this;
		}

		// Установить максимальную высоту
		public Builder setHeight(int maxHeight) {
			this.maxHeight = maxHeight;
			return this;
		}

		// Установить максимальное расстояние распада для листьев дерева.
		public Builder setDecayDist(int maxDecayDistance) {
			this.maxDecayDistance = maxDecayDistance;
			return this;
		}

		// Установить хвойное дерево
		public Builder setConifer() {
			isConifer = true;
			return this;
		}

		// Установить генератор кустов по умолчанию
		public Builder setBushes() {
			bushGenerator = DefaultTrees.GEN_BUSHES;
			return this;
		}

		// Установить генератор кустов
		public Builder setBushes(ITreeGenerator bushGenerator) {
			this.bushGenerator = bushGenerator;
			return this;
		}

		// Установить возможность производить танин
		public Builder setTannin() {
			canMakeTannin = true;
			return this;
		}

		// Установить время роста
		public Builder setGrowthTime(float minGrowthTime) {
			this.minGrowthTime = minGrowthTime;
			return this;
		}

		// Установить плотность
		public Builder setDensity(float min, float max) {
			this.minDensity = min;
			this.maxDensity = max;
			return this;
		}

		// Установить температуру и количество тиков горения
		public Builder setBurnInfo(float burnTemp, int burnTicks) {
			this.burnTemp = burnTemp;
			this.burnTicks = burnTicks;
			return this;
		}

		// Метод для построения объекта WoodType
		public WoodType build() {
			return new WoodType(
				color, gen, minTemp, maxTemp, minRain,
				maxRain, minDensity, maxDensity, dominance,
				maxGrowthRadius, maxHeight, maxDecayDistance, isConifer,
				bushGenerator, canMakeTannin, minGrowthTime, burnTemp, burnTicks);
		}
	}
}
