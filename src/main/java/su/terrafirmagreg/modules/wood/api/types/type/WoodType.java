package su.terrafirmagreg.modules.wood.api.types.type;

import binnie.extratrees.wood.planks.IPlankType;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Класс Wood представляет тип дерева с определенными характеристиками.
 */
public class WoodType {

	private static final Set<WoodType> WOOD_TYPES = new ObjectLinkedOpenHashSet<>();

	@NotNull
	private final String name;
	@Getter
	private final int color;
	@Getter
	private final float burnTemp;
	@Getter
	private final int burnTicks;
	@Getter
	private final boolean canMakeTannin;

	private WoodType(Builder builder) {
		this.name = builder.name;
		this.color = builder.color;

		this.burnTemp = builder.burnTemp;
		this.burnTicks = builder.burnTicks;
		this.canMakeTannin = builder.canMakeTannin;

		if (name.isEmpty())
			throw new RuntimeException(String.format("WoodType name must contain any character: [%s]", name));

		if (!WOOD_TYPES.add(this)) throw new RuntimeException(String.format("WoodType: [%s] already exists!", name));
	}

	/**
	 * Возвращает список всех доступных типов дерева.
	 *
	 * @return список типов дерева
	 */
	public static Set<WoodType> getTypes() {
		return WOOD_TYPES;
	}

	/**
	 * Возвращает строковое представление древесины.
	 *
	 * @return Строковое представление древесины.
	 */
	@Override
	public String toString() {
		return name;
	}

	public static class Builder {

		private final String name;
		private int color;
		private float burnTemp;
		private int burnTicks;
		private boolean canMakeTannin;
		private IPlankType foestryWoodType;

		public Builder(@NotNull String name) {
			this.name = name;
			this.color = 0xFFFFFF;
			this.burnTemp = 0;
			this.burnTicks = 0;
			this.canMakeTannin = false;
		}

		public Builder setColor(int color) {
			this.color = color;
			return this;
		}

		// Установить температуру и количество тиков горения
		public Builder setBurnInfo(float burnTemp, int burnTicks) {
			this.burnTemp = burnTemp;
			this.burnTicks = burnTicks;
			return this;
		}

		// Установить возможность производить танин
		public Builder setTannin() {
			canMakeTannin = true;
			return this;
		}

		public Builder setForestryWoodType(IPlankType foestryWoodType) {
			this.foestryWoodType = foestryWoodType;
			this.setColor(foestryWoodType.getColour());
			return this;
		}

		// Метод для построения объекта WoodType
		public WoodType build() {
			return new WoodType(this);
		}
	}
}
