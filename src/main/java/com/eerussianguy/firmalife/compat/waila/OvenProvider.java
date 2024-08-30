package com.eerussianguy.firmalife.compat.waila;

import su.terrafirmagreg.modules.device.objects.tiles.TileOven;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;


import com.eerussianguy.firmalife.recipe.OvenRecipe;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.util.calendar.ICalendar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.data.Properties.LIT;
import static su.terrafirmagreg.modules.device.objects.tiles.TileOven.SLOT_MAIN;

public class OvenProvider implements IWailaBlock {

    @NotNull
    @Override
    public List<String> getTooltip(World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
        List<String> currentTooltip = new ArrayList<>();
        IBlockState state = world.getBlockState(pos);
        var tile = world.getTileEntity(pos);
        if (tile instanceof TileOven oven) {
            ItemStack mainSlot = oven.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).getStackInSlot(SLOT_MAIN);
            OvenRecipe recipe = OvenRecipe.get(mainSlot);
            if (state.getValue(LIT) && recipe != null) {
                long remainingTicks = oven.getTicksRemaining();
                switch (ConfigTFC.Client.TOOLTIP.timeTooltipMode) {
                    case NONE:
                        break;
                    case TICKS:
                        currentTooltip.add(new TextComponentTranslation("waila.tfc.devices.ticks_remaining", remainingTicks).getFormattedText());
                    case MINECRAFT_HOURS:
                        long remainingHours = Math.round(remainingTicks / (float) ICalendar.TICKS_IN_HOUR);
                        currentTooltip.add(new TextComponentTranslation("waila.tfc.devices.hours_remaining", remainingHours).getFormattedText());
                        break;
                    case REAL_MINUTES:
                        long remainingMinutes = Math.round(remainingTicks / 1200.0f);
                        currentTooltip.add(new TextComponentTranslation("waila.tfc.devices.minutes_remaining", remainingMinutes).getFormattedText());
                        break;
                }
                currentTooltip.add(new TextComponentTranslation(recipe.getOutputItem(mainSlot)
                        .getDisplayName()).getFormattedText());
                if (((TileOven) tile).isCuringRecipe()) {
                    currentTooltip.add("Curing");
                }
            }
        }
        return currentTooltip;
    }

    @NotNull
    @Override
    public List<Class<?>> getLookupClass() {
        return Collections.singletonList(TileOven.class);
    }
}
