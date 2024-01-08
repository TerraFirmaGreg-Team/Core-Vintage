package pieman.caffeineaddon.blocks;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeTrunk;
import pieman.caffeineaddon.init.ModBlocks;

public class BlockCoffeeTreeTrunk extends BlockFruitTreeTrunk {

	public BlockCoffeeTreeTrunk(IFruitTree tree) {
		super(tree);
		setTranslationKey(tree.getName() + "_trunk");
		setRegistryName(tree.getName() + "_trunk");
		//setCreativeTab(CreativeTabs.REDSTONE);

		ModBlocks.BLOCKS.add(this);
		//ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(tree.getName() + "_trunk"));
	}

}
