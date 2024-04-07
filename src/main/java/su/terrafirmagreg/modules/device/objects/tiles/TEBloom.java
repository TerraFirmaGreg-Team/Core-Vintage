package su.terrafirmagreg.modules.device.objects.tiles;

import net.minecraft.item.ItemStack;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.te.TEInventory;

@MethodsReturnNonnullByDefault
public class TEBloom extends TEInventory {

    public TEBloom() {
        super(1);
    }

    public void setBloom(ItemStack stack) {
        inventory.setStackInSlot(0, stack);
    }
}
