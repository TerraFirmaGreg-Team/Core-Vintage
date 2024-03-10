package su.terrafirmagreg.modules.soil.api.types.variant.item;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.data.ItemsSoil;

import java.util.Set;
import java.util.function.BiFunction;

/**
 * Класс CropItemVariant представляет вариант деревянного блока.
 */
public class SoilItemVariant implements Comparable<SoilItemVariant> {

	private static final Set<SoilItemVariant> SOIL_ITEM_VARIANTS = new ObjectOpenHashSet<>();

	@NotNull
	private final String name;

	private SoilItemVariant(Builder builder) {
		this.name = builder.name;

		if (name.isEmpty())
			throw new RuntimeException(String.format("CropItemVariant name must contain any character: [%s]", name));

		if (!SOIL_ITEM_VARIANTS.add(this))
			throw new RuntimeException(String.format("CropItemVariant: [%s] already exists!", name));

		for (var type : SoilType.getTypes()) {
			if (ItemsSoil.SOIL_ITEMS.put(new Pair<>(this, type), builder.factory.apply(this, type)) != null)
				throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
		}
	}

	/**
	 * Возвращает множество всех созданных вариантов деревянных блоков.
	 *
	 * @return множество вариантов деревянных блоков
	 */
	public static Set<SoilItemVariant> getItemVariants() {
		return SOIL_ITEM_VARIANTS;
	}

	public Item get(SoilType type) {
		var item = ItemsSoil.SOIL_ITEMS.get(new Pair<>(this, type));
		if (item != null) return item;
		throw new RuntimeException(String.format("Item soil is null: %s, %s", this, type));
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
	public int compareTo(@NotNull SoilItemVariant itemVariant) {
		return this.name.compareTo(itemVariant.toString());
	}


	public static class Builder {

		private final String name;
		private BiFunction<SoilItemVariant, SoilType, ? extends Item> factory;


		/**
		 * Создает экземпляр Builder с указанным именем.
		 *
		 * @param name Имя породы.
		 */
		public Builder(@NotNull String name) {
			this.name = name;
		}


		public Builder setFactory(BiFunction<SoilItemVariant, SoilType, ? extends Item> factory) {
			this.factory = factory;
			return this;
		}


		public SoilItemVariant build() {
			return new SoilItemVariant(this);
		}
	}
}
