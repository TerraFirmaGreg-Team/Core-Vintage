package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.modules.device.ModuleDeviceConfig;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

public class ItemMetalFlask extends ItemFlask {

    protected static int capacity = ModuleDeviceConfig.ITEMS.WATER_FLASKS.ironCap;
    protected static int drink = 100; //matches amount of water in TFC Jug

    public ItemMetalFlask() {
        super(capacity, drink);
    }

    @Override
    public String getName() {
        return "device/flask/metal";
    }

    @Override
    public Size getSize(ItemStack stack) {
        return Size.NORMAL;
    }

    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.HEAVY;
    }

}
