package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.modules.device.ModuleDeviceConfig;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

public class ItemLeatherFlask extends ItemFlask {

    protected static int capacity = ModuleDeviceConfig.ITEMS.WATER_FLASKS.leatherCap;
    protected static int drink = 100; //matches amount of water in TFC Jug

    public ItemLeatherFlask() {
        super("leather", capacity, drink);

        getSettings()
                .size(Size.SMALL)
                .weight(Weight.MEDIUM);
    }

}
