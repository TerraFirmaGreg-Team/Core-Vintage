package su.terrafirmagreg.modules.device.objects.tiles;

import su.terrafirmagreg.api.spi.tile.BaseTileInventory;

import net.minecraft.item.ItemStack;


import mcp.MethodsReturnNonnullByDefault;

@MethodsReturnNonnullByDefault
public class TileBloom extends BaseTileInventory {

    public TileBloom() {
        super(1);
    }

    public void setBloom(ItemStack stack) {
        inventory.setStackInSlot(0, stack);
    }
}
