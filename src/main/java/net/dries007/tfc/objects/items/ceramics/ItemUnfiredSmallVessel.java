package net.dries007.tfc.objects.items.ceramics;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.jetbrains.annotations.NotNull;

public class ItemUnfiredSmallVessel extends ItemPottery {
	public final boolean glazed;

	public ItemUnfiredSmallVessel(boolean glazed) {
		this.glazed = glazed;
		setHasSubtypes(glazed);
	}

	@Override
	@NotNull
	public String getTranslationKey(ItemStack stack) {
		if (!glazed) {
			return super.getTranslationKey(stack);
		}
		return super.getTranslationKey(stack) + "." + EnumDyeColor.byDyeDamage(stack.getItemDamage()).getName();
	}

	@Override
	public void getSubItems(@NotNull CreativeTabs tab, @NotNull NonNullList<ItemStack> items) {
		if (isInCreativeTab(tab)) {
			if (!glazed) {
				items.add(new ItemStack(this));
			} else {
				for (EnumDyeColor color : EnumDyeColor.values()) {
					items.add(new ItemStack(this, 1, color.getDyeDamage()));
				}
			}
		}
	}
}
