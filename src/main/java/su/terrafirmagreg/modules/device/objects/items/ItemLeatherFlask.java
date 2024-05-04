package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.modules.device.ModuleDeviceConfig;

public class ItemLeatherFlask extends ItemFlask {

    protected static int capacity = ModuleDeviceConfig.ITEMS.WATER_FLASKS.leatherCap;
    protected static int drink = 100; //matches amount of water in TFC Jug

    public ItemLeatherFlask() {
        super(capacity, drink);
    }

    @Override
    public String getName() {
        return "device/flask/leather";
    }

}
