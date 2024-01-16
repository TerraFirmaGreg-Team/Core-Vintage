package net.sharkbark.cellars.items;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemStack;
import net.sharkbark.cellars.Main;
import org.jetbrains.annotations.NotNull;

public class ItemIceShard extends ItemBase {

	public ItemIceShard(String name) {
		super(name);
		setCreativeTab(Main.creativeTab);
	}

	@Override
	public @NotNull Size getSize(@NotNull ItemStack stack) {
		return Size.SMALL;
	}

	@Override
	public @NotNull Weight getWeight(@NotNull ItemStack stack) {
		return Weight.LIGHT;
	}
}
