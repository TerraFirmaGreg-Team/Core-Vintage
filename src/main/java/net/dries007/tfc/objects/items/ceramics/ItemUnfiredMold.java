package net.dries007.tfc.objects.items.ceramics;

import net.dries007.tfc.api.types.Metal;

import java.util.EnumMap;

public class ItemUnfiredMold extends ItemPottery {
	private static final EnumMap<Metal.ItemType, ItemUnfiredMold> MAP = new EnumMap<>(Metal.ItemType.class);
	public final Metal.ItemType type;

	public ItemUnfiredMold(Metal.ItemType type) {
		this.type = type;
		if (MAP.put(type, this) != null) {
			throw new IllegalStateException("There can only be one.");
		}
	}

	public static ItemUnfiredMold get(Metal.ItemType category) {
		return MAP.get(category);
	}
}
