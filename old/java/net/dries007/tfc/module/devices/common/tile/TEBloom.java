package net.dries007.tfc.module.devices.common.tile;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.module.core.api.tile.TEInventory;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TEBloom extends TEInventory {
    public TEBloom() {
        super(1);
    }

    public void setBloom(ItemStack stack) {
        inventory.setStackInSlot(0, stack);
    }
}
