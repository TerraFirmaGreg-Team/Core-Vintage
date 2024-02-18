package pieman.caffeineaddon.items;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.item.Item;
import pieman.caffeineaddon.CaffeineAddon;
import pieman.caffeineaddon.init.ModItems;
import su.terrafirmagreg.api.registry.IHasStateMapper;

public class ItemCoffee extends Item implements IHasStateMapper {

	public ItemCoffee(String name) {
		super();
		this.setTranslationKey(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabsTFC.CT_FOOD);

		ModItems.ITEMS.add(this);
	}

	@Override
	public void onStateMapperRegister() {
		CaffeineAddon.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
