package net.dries007.tfc.compat.dynamictrees.proxy;

import net.dries007.tfc.compat.dynamictrees.ModBlocks;
import net.dries007.tfc.compat.dynamictrees.ModTrees;

public class CommonProxy {
	public void preInit() {
		ModBlocks.preInit();
		ModTrees.preInit();
	}
}
