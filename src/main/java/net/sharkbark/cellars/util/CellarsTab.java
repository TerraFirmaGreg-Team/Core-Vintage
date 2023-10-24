package net.sharkbark.cellars.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.sharkbark.cellars.init.ModBlocks;

import javax.annotation.Nonnull;

public class CellarsTab extends CreativeTabs {

    public CellarsTab() {
        super(Reference.MOD_ID);
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModBlocks.CELLAR_DOOR);
    }

}
