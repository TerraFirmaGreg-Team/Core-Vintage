package pieman.caffeineaddon.items;

import su.terrafirmagreg.api.registry.provider.IModelProvider;

import net.minecraft.item.Item;


import net.dries007.tfc.objects.CreativeTabsTFC;
import pieman.caffeineaddon.CaffeineAddon;
import pieman.caffeineaddon.init.ModItems;

public class ItemCoffee extends Item implements IModelProvider {

    public ItemCoffee(String name) {
        super();
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(CreativeTabsTFC.CT_FOOD);

        ModItems.ITEMS.add(this);
    }

    @Override
    public void onModelRegister() {
        CaffeineAddon.proxy.registerItemRenderer(this, 0, "inventory");
    }

}
