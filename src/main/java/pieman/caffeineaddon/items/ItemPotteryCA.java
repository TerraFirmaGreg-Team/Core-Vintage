package pieman.caffeineaddon.items;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemPottery;
import pieman.caffeineaddon.CaffeineAddon;
import pieman.caffeineaddon.init.ModItems;
import pieman.caffeineaddon.util.IHasModel;

public class ItemPotteryCA extends ItemPottery implements IHasModel {

	public ItemPotteryCA(String name) {
		super();
		this.setTranslationKey(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabsTFC.CT_POTTERY);

		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		CaffeineAddon.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
