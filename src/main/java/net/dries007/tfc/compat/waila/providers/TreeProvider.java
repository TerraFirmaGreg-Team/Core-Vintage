package net.dries007.tfc.compat.waila.providers;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;


import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.blocks.wood.BlockSaplingTFC;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.calendar.ICalendar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeProvider implements IWailaBlock {

    @NotNull
    @Override
    public List<String> getTooltip(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
        List<String> currentTooltip = new ArrayList<>();
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockSaplingTFC block) {
            Tree wood = block.getWood();
            var tile = TileUtils.getTile(world, pos, TETickCounter.class);
            if (tile != null) {
                long days = tile.getTicksSinceUpdate() / ICalendar.TICKS_IN_DAY;
                float perc = Math.min(0.99F, days / wood.getMinGrowthTime()) * 100;
                String growth = String.format("%d%%", Math.round(perc));
                currentTooltip.add(new TextComponentTranslation("waila.tfc.crop.growth", growth).getFormattedText());
            }

        }
        return currentTooltip;
    }

    @NotNull
    @Override
    public List<Class<?>> getLookupClass() {
        return Collections.singletonList(BlockSaplingTFC.class);
    }
}
