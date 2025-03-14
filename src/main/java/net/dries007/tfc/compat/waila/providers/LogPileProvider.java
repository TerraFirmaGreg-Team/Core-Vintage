package net.dries007.tfc.compat.waila.providers;

import su.terrafirmagreg.modules.device.object.tile.TileLogPile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.util.Helpers;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class LogPileProvider implements IWailaBlock {

  @Nonnull
  @Override
  public ItemStack getIcon(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull NBTTagCompound nbt) {
    TileLogPile logPile = Helpers.getTE(world, pos, TileLogPile.class);
    if (logPile != null) {
      IItemHandler inventory = logPile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
      ItemStack icon = ItemStack.EMPTY;
      for (int i = 0; i < inventory.getSlots(); i++) {
        ItemStack slotStack = inventory.getStackInSlot(i);
        if (!slotStack.isEmpty()) {
          if (icon.isEmpty()) {
            icon = slotStack.copy();
          } else if (slotStack.isItemEqual(icon)) {
            icon.grow(slotStack.getCount());
          }
        }
      }

      return icon;
    }
    return ItemStack.EMPTY;
  }

  @Nonnull
  @Override
  public List<Class<?>> getLookupClass() {
    return Collections.singletonList(TileLogPile.class);
  }

  @Override
  public boolean appendBody() {
    return false;
  }

  @Override
  public boolean overrideIcon() {
    return true;
  }
}
