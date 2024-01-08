package pieman.caffeineaddon.jeicompat;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import pieman.caffeineaddon.init.ModBlocks;

public class OreDictionaryCompat {

	public static void register() {

		System.out.println(ModBlocks.LEAVES);
		System.out.println(ModBlocks.SAPLING);
		System.out.println(new ItemStack(ModBlocks.LEAVES, 1));
		System.out.println(new ItemStack(ModBlocks.LEAVES, 1).isEmpty());
		OreDictionary.registerOre("treeLeavesies", new ItemStack(ModBlocks.LEAVES, 1));
		OreDictionary.registerOre("treeLeavesCoffeeies", new ItemStack(ModBlocks.LEAVES, 1));
		OreDictionary.registerOre("treeSaplingies", new ItemStack(ModBlocks.SAPLING, 1));
		OreDictionary.registerOre("treeSaplingCoffeeies", new ItemStack(ModBlocks.SAPLING, 1));
	}

}
