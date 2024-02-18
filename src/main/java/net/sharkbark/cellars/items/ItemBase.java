package net.sharkbark.cellars.items;

import net.dries007.tfc.objects.items.ItemTFC;
import net.minecraft.creativetab.CreativeTabs;
import net.sharkbark.cellars.Main;
import net.sharkbark.cellars.init.ModItems;
import su.terrafirmagreg.api.registry.IHasStateMapper;

public abstract class ItemBase extends ItemTFC implements IHasStateMapper {

	public ItemBase(String name) {
		setTranslationKey(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);

		ModItems.ITEMS.add(this);
	}

	@Override
	public void onStateMapperRegister() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
