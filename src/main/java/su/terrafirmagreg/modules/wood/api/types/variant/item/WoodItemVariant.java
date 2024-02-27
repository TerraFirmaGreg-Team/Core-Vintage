package su.terrafirmagreg.modules.wood.api.types.variant.item;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.data.ItemsWood;

import java.util.Set;
import java.util.function.BiFunction;

/**
 * Класс CropItemVariant представляет вариант деревянного блока.
 */
public class WoodItemVariant implements Comparable<WoodItemVariant> {

	private static final Set<WoodItemVariant> WOOD_ITEM_VARIANTS = new ObjectOpenHashSet<>();

	@NotNull
	private final String name;

	private WoodItemVariant(Builder builder) {
		this.name = builder.name;

		if (name.isEmpty())
			throw new RuntimeException(String.format("CropItemVariant name must contain any character: [%s]", name));

		if (!WOOD_ITEM_VARIANTS.add(this))
			throw new RuntimeException(String.format("CropItemVariant: [%s] already exists!", name));

		for (var type : WoodType.getTypes()) {
			if (ItemsWood.WOOD_ITEMS.put(new Pair<>(this, type), builder.factory.apply(this, type)) != null)
				throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
		}
	}

	/**
	 * Возвращает множество всех созданных вариантов деревянных блоков.
	 *
	 * @return множество вариантов деревянных блоков
	 */
	public static Set<WoodItemVariant> getWoodItemVariants() {
		return WOOD_ITEM_VARIANTS;
	}

	/**
	 * Возвращает строковое представление варианта деревянного блока (его имя).
	 *
	 * @return имя варианта деревянного блока
	 */
	@NotNull
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(@NotNull WoodItemVariant itemVariant) {
		return this.name.compareTo(itemVariant.toString());
	}

	public static class Builder {

		private final String name;
		private BiFunction<WoodItemVariant, WoodType, ? extends Item> factory;


		/**
		 * Создает экземпляр Builder с указанным именем.
		 *
		 * @param name Имя породы.
		 */
		public Builder(@NotNull String name) {
			this.name = name;
		}


		public Builder setFactory(BiFunction<WoodItemVariant, WoodType, ? extends Item> factory) {
			this.factory = factory;
			return this;
		}


		public WoodItemVariant build() {
			return new WoodItemVariant(this);
		}
	}
}
