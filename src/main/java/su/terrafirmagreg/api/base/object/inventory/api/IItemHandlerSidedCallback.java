package su.terrafirmagreg.api.base.object.inventory.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IItemHandlerSidedCallback {

  boolean canInsert(int slot, ItemStack stack, EnumFacing side);

  boolean canExtract(int slot, EnumFacing side);
}
