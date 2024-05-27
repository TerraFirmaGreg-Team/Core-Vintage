package net.dries007.tfc.compat.waila.providers;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.items.metal.ItemIngot;
import net.dries007.tfc.objects.te.TEIngotPile;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class IngotPileProvider implements IWailaBlock {

    @NotNull
    @Override
    public ItemStack getIcon(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
        var tile = TileUtils.getTile(world, pos, TEIngotPile.class);
        if (tile != null) {
            return new ItemStack(ItemIngot.get(tile.getMetal(), Metal.ItemType.INGOT), tile.getCount());
        }
        return ItemStack.EMPTY;
    }

    @NotNull
    @Override
    public List<Class<?>> getLookupClass() {
        return Collections.singletonList(TEIngotPile.class);
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
