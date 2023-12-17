package pieman.caffeineaddon.blocks;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeBranch;
import pieman.caffeineaddon.init.ModBlocks;

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
