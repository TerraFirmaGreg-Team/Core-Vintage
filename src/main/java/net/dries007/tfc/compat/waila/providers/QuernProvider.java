package net.dries007.tfc.compat.waila.providers;

import su.terrafirmagreg.modules.device.objects.blocks.BlockQuern;
import su.terrafirmagreg.modules.device.objects.tiles.TEQuern;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.util.Helpers;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.modules.device.objects.tiles.TEQuern.SLOT_HANDSTONE;

public class QuernProvider implements IWailaBlock {

    @NotNull
    @Override
    public List<String> getTooltip(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
        List<String> currentTooltip = new ArrayList<>(1);
        TEQuern quern = Helpers.getTE(world, pos, TEQuern.class);
        IItemHandler handler;
        ItemStack handstone;
        if (quern != null && quern.hasHandstone() && (handler = quern.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) != null &&
                !(handstone = handler.getStackInSlot(SLOT_HANDSTONE)).isEmpty()) {
            currentTooltip.add(new TextComponentTranslation("waila.tfc.quern.handstone_durability", handstone.getItemDamage(),
                    handstone.getMaxDamage()).getFormattedText());
        }
        return currentTooltip;
    }

    @NotNull
    @Override
    public List<Class<?>> getLookupClass() {
        return Collections.singletonList(BlockQuern.class);
    }

}
