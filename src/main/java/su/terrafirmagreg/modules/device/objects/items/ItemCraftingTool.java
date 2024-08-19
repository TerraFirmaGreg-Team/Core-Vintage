package su.terrafirmagreg.modules.device.objects.items;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

public class ItemCraftingTool extends ItemDeviceMisc {

    public ItemCraftingTool(String name, int durability, Size size, Weight weight, Object... oreNameParts) {
        super(name, size, weight, oreNameParts);

        setNoRepair();
        getSettings()
                .maxCount(1)
                .notCanStack()
                .maxDamage(durability);
    }
}
