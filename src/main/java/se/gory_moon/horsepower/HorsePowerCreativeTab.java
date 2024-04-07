package se.gory_moon.horsepower;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import se.gory_moon.horsepower.blocks.ModBlocks;

import static su.terrafirmagreg.api.lib.Constants.MODID_HORSEPOWER;

public class HorsePowerCreativeTab extends CreativeTabs {

    public HorsePowerCreativeTab() {
        super(MODID_HORSEPOWER);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModBlocks.BLOCK_HAND_GRINDSTONE);
    }
}
