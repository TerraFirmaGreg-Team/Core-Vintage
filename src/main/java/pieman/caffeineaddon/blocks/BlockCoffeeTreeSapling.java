package pieman.caffeineaddon.blocks;

import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.blocks.agriculture.BlockFruitTreeSapling;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import pieman.caffeineaddon.CaffeineAddon;
import pieman.caffeineaddon.init.ModBlocks;
import pieman.caffeineaddon.init.ModItems;
import su.terrafirmagreg.api.model.ICustomModel;

public class BlockCoffeeTreeSapling extends BlockFruitTreeSapling implements ICustomModel {

	public BlockCoffeeTreeSapling(IFruitTree tree) {
		super(tree);
		setTranslationKey(tree.getName() + "_sapling");
		setRegistryName(tree.getName() + "_sapling");
		setCreativeTab(CreativeTabsTFC.CT_WOOD);

		//the super does this but passes itself so forge doesn't register it
		//OreDictionaryHelper.register(this, "tree", "sapling");
		//OreDictionaryHelper.register(this, "tree", "sapling", tree.getName());

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(tree.getName() + "_sapling"));
	}

	@Override
	public void onModelRegister() {
		CaffeineAddon.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

}
