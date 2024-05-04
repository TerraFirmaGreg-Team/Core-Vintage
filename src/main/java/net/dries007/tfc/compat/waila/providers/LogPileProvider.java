package net.dries007.tfc.compat.waila.providers;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.objects.tiles.TileLogPile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class LogPileProvider implements IWailaBlock {

    @NotNull
    @Override
    public ItemStack getIcon(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
        TileLogPile logPile = TileUtils.getTile(world, pos, TileLogPile.class);
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

    @NotNull
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
