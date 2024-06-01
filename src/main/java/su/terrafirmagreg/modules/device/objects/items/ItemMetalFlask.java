package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.modules.device.ModuleDeviceConfig;

import su.terrafirmagreg.api.capabilities.size.spi.Size;

import su.terrafirmagreg.api.capabilities.size.spi.Weight;

public class ItemMetalFlask extends ItemFlask {

    protected static int capacity = ModuleDeviceConfig.ITEMS.WATER_FLASKS.ironCap;
    protected static int drink = 100; //matches amount of water in TFC Jug

    public ItemMetalFlask() {
        super("metal", capacity, drink);

        getSettings()
                .weight(Weight.HEAVY)
                .size(Size.NORMAL);
    }

}
