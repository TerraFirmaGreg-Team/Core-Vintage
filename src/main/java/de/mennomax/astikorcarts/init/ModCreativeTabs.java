package de.mennomax.astikorcarts.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import static su.terrafirmagreg.Constants.MODID_ASTIKORCARTS;

public class ModCreativeTabs {
	public static CreativeTabs astikor = new CreativeTabs(MODID_ASTIKORCARTS) {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.WHEEL);
		}
	};
}
