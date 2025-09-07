package su.terrafirmagreg.framework.manager.content.base.inventory.api;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface IItemHandlerSidedCallback {

  boolean canInsert(int slot, ItemStack stack, EnumFacing side);

  boolean canExtract(int slot, EnumFacing side);
}
