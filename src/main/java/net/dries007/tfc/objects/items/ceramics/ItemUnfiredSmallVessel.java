package net.dries007.tfc.objects.items.ceramics;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

public class ItemUnfiredSmallVessel extends ItemPottery {
	public final boolean glazed;

	public ItemUnfiredSmallVessel(boolean glazed) {
		this.glazed = glazed;
		setHasSubtypes(glazed);
	}

	@Nonnull
	@Override
	public String getItemStackDisplayName(@Nonnull ItemStack stack) {
		if (!glazed) {
			return new TextComponentTranslation("item.tfc.ceramics.unfired.vessel.name").getFormattedText();
		} else {
			return new TextComponentTranslation(
					"item.tfc.ceramics.unfired.vessel_glazed.name",
					new TextComponentTranslation("color." + EnumDyeColor.byDyeDamage(stack.getItemDamage()).getName())
			).getFormattedText();
		}
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
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
