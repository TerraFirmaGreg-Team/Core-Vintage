package su.terrafirmagreg.modules.rock.api.types.category;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Getter;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Класс, представляющий категорию породы.
 */
public class RockCategory implements Comparable<RockCategory> {

	private static final Set<RockCategory> ROCK_CATEGORIES = new ObjectOpenHashSet<>();

	private final String name;
	private final boolean layer1;
	private final boolean layer2;
	private final boolean layer3;
	@Getter
	private final float caveGenMod;
	@Getter
	private final float caveFreqMod;
	@Getter
	private final float hardnessModifier;
	@Getter
	private final TextFormatting textFormatting;
	@Getter
	private final boolean hasAnvil;

	private RockCategory(Builder builder) {
		this.name = builder.name;
		this.layer1 = builder.layer1;
		this.layer2 = builder.layer2;
		this.layer3 = builder.layer3;
		this.caveGenMod = builder.caveGenMod;
		this.caveFreqMod = builder.caveFreqMod;
		this.hardnessModifier = builder.hardnessModifier;
		this.textFormatting = builder.textFormatting;
		this.hasAnvil = builder.hasAnvil;

		if (name.isEmpty())
			throw new RuntimeException(String.format("RockCategory name must contain any character: [%s]", name));

		if (!ROCK_CATEGORIES.add(this))
			throw new RuntimeException(String.format("RockCategory: [%s] already exists!", name));

	}

	/**
	 * Возвращает набор всех категорий пород.
	 *
	 * @return Набор всех категорий пород.
	 */
	public static Set<RockCategory> getRockCategories() {
		return ROCK_CATEGORIES;
	}

	/**
	 * Возвращает строковое представление категории породы.
	 *
	 * @return Строковое представление категории породы.
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Возвращает локализованное имя категории породы.
	 *
	 * @return Локализованное имя категории породы.
	 */
	public String getLocalizedName() {
		return textFormatting + new TextComponentTranslation(
				String.format("rock.category.%s.name", this)).getFormattedText();
	}

	@Override
	public int compareTo(@NotNull RockCategory category) {
		return this.name.compareTo(category.toString());
	}

	/**
	 * Представляет слой породы и определяет, принадлежит ли порода к данному слою.
	 */
	public enum Layer implements Predicate<RockType> {
		BOTTOM(3, x -> x.getRockCategory().layer3),
		MIDDLE(2, x -> x.getRockCategory().layer2),
		TOP(1, x -> x.getRockCategory().layer1);

		public final int layer;
		private final Predicate<RockType> filter;

		/**
		 * Создает экземпляр слоя породы с указанными параметрами.
		 *
		 * @param layer  Номер слоя породы.
		 * @param filter Фильтр, определяющий, принадлежит ли порода к данному слою.
		 */
		Layer(int layer, Predicate<RockType> filter) {
			this.layer = layer;
			this.filter = filter;
		}

		/**
		 * Проверяет, принадлежит ли указанная порода к данному слою.
		 *
		 * @param type Порода для проверки.
		 * @return true, если порода принадлежит к данному слою, в противном случае - false.
		 */
		@Override
		public boolean test(RockType type) {
			return filter.test(type);
		}
	}

	/**
	 * Класс Builder используется для создания объекта RockCategory с указанными параметрами.
	 */
	public static class Builder {

		private final String name;
		private boolean layer1 = false;
		private boolean layer2 = false;
		private boolean layer3 = false;
		private float caveGenMod = 0f;
		private float caveFreqMod = 0f;
		private float hardnessModifier = 0f;
		private TextFormatting textFormatting = TextFormatting.RESET;
		private boolean hasAnvil = false;

		/**
		 * Конструктор класса Builder.
		 *
		 * @param name Название категории породы.
		 */
		public Builder(@NotNull String name) {
			this.name = name;
		}

		/**
		 * Устанавливает значения флагов для слоев породы.
		 *
		 * @param layer1 Флаг, указывающий, присутствует ли категория в первом слое пород.
		 * @param layer2 Флаг, указывающий, присутствует ли категория во втором слое пород.
		 * @param layer3 Флаг, указывающий, присутствует ли категория в третьем слое пород.
		 * @return Возвращает объект Builder для цепочки вызовов.
		 */
		public Builder setLayer(boolean layer1, boolean layer2, boolean layer3) {
			this.layer1 = layer1;
			this.layer2 = layer2;
			this.layer3 = layer3;
			return this;
		}

		/**
		 * Устанавливает значения модификаторов генерации породы в пещерах.
		 *
		 * @param caveGenMod  Модификатор генерации породы в пещерах.
		 * @param caveFreqMod Модификатор частоты генерации породы в пещерах.
		 * @return Возвращает объект Builder для цепочки вызовов.
		 */
		public Builder setCaveMod(float caveGenMod, float caveFreqMod) {
			this.caveGenMod = caveGenMod;
			this.caveFreqMod = caveFreqMod;
			return this;
		}

		/**
		 * Устанавливает значение модификатора прочности породы.
		 *
		 * @param hardnessModifier Модификатор прочности породы.
		 * @return Возвращает объект Builder для цепочки вызовов.
		 */
		public Builder setHardnessModifier(float hardnessModifier) {
			this.hardnessModifier = hardnessModifier;
			return this;
		}

		/**
		 * Устанавливает значение форматирования текста для отображения категории.
		 *
		 * @param textFormatting Форматирование текста для отображения категории.
		 * @return Возвращает объект Builder для цепочки вызовов.
		 */
		public Builder setTextFormatting(@NotNull TextFormatting textFormatting) {
			this.textFormatting = textFormatting;
			return this;
		}

		/**
		 * Устанавливает флаг, указывающий, имеет ли категория наковальню.
		 *
		 * @return Возвращает объект Builder для цепочки вызовов.
		 */
		public Builder setAnvil() {
			this.hasAnvil = true;
			return this;
		}

		public RockCategory build() {
			return new RockCategory(this);
		}
	}
}
