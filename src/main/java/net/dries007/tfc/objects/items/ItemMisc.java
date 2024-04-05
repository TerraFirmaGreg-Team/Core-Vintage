package net.dries007.tfc.objects.items;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.NotNull;

public class ItemMisc extends ItemTFC implements IItemSize {
	private final Size size;
	private final Weight weight;

	public ItemMisc(Size size, Weight weight, Object... oreNameParts) {
		this(size, weight);

		for (Object obj : oreNameParts) {
			if (obj instanceof Object[])
				OreDictionaryHelper.register(this, (Object[]) obj);
			else
				OreDictionaryHelper.register(this, obj);
		}
	}

	public ItemMisc(Size size, Weight weight) {
		this.size = size;
		this.weight = weight;
	}


	@Override
	public @NotNull Size getSize(@NotNull ItemStack stack) {
		return size;
	}


	@Override
	public @NotNull Weight getWeight(@NotNull ItemStack stack) {
		return weight;
	}
}
