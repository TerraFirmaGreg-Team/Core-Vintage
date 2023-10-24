package pieman.caffeineaddon.blocks;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeBranch;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import pieman.caffeineaddon.CaffeineAddon;
import pieman.caffeineaddon.init.ModBlocks;
import pieman.caffeineaddon.init.ModItems;
import pieman.caffeineaddon.util.IHasModel;

public class BlockCoffeeTreeBranch extends BlockFruitTreeBranch {

	public BlockCoffeeTreeBranch(IFruitTree tree) {
		super(tree);
		setTranslationKey(tree.getName() + "_branch");
		setRegistryName(tree.getName() + "_branch");
		//setCreativeTab(CreativeTabs.REDSTONE);

		ModBlocks.BLOCKS.add(this);
		//ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(tree.getName() + "_branch"));
	}
}
