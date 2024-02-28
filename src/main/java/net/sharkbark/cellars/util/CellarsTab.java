package net.sharkbark.cellars.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.sharkbark.cellars.init.ModBlocks;

import javax.annotation.Nonnull;

import static su.terrafirmagreg.api.lib.Constants.MODID_CELLARS;

public class CellarsTab extends CreativeTabs {

	public CellarsTab() {
		super(MODID_CELLARS);
	}

	@Nonnull
	@Override
	public ItemStack createIcon() {
		return new ItemStack(ModBlocks.CELLAR_DOOR);
	}

}
