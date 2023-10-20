/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.te;

import mcp.MethodsReturnNonnullByDefault;
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
