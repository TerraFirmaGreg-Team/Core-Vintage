package su.terrafirmagreg.modules.device.objects.tiles;

import su.terrafirmagreg.api.spi.tile.TEBaseInventory;

import net.minecraft.item.ItemStack;


import mcp.MethodsReturnNonnullByDefault;

@MethodsReturnNonnullByDefault
public class TEBloom extends TEBaseInventory {

    public TEBloom() {
        super(1);
    }

    public void setBloom(ItemStack stack) {
        inventory.setStackInSlot(0, stack);
    }
}
