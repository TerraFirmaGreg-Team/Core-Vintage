package pieman.caffeineaddon.items;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ceramics.ItemPottery;
import pieman.caffeineaddon.CaffeineAddon;
import pieman.caffeineaddon.init.ModItems;
import su.terrafirmagreg.api.registry.IHasStateMapper;

public class ItemPotteryCA extends ItemPottery implements IHasStateMapper {

	public ItemPotteryCA(String name) {
		super();
		this.setTranslationKey(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabsTFC.CT_POTTERY);

		ModItems.ITEMS.add(this);
	}

	@Override
	public void onStateMapperRegister() {
		CaffeineAddon.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
