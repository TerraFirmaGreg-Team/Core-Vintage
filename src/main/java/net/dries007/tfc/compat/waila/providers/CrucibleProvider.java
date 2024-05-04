package net.dries007.tfc.compat.waila.providers;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.objects.tiles.TileCrucible;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;


import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrucibleProvider implements IWailaBlock {

    @NotNull
    @Override
    public List<String> getTooltip(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
        List<String> currentTooltip = new ArrayList<>();
        TileCrucible crucible = TileUtils.getTile(world, pos, TileCrucible.class);
        if (crucible != null) {
            if (crucible.getAlloy().getAmount() > 0) {
                Metal metal = crucible.getAlloyResult();
                currentTooltip.add(new TextComponentTranslation("waila.tfc.metal.output", crucible.getAlloy()
                        .getAmount(), new TextComponentTranslation(metal.getTranslationKey()).getFormattedText()).getFormattedText());
            }
            float temperature = nbt.getFloat("temp");
            String heatTooltip = Heat.getTooltip(temperature);
            if (heatTooltip != null) {
                currentTooltip.add(heatTooltip);
            }
        }
        return currentTooltip;
    }

    @NotNull
    @Override
    public List<Class<?>> getLookupClass() {
        return Collections.singletonList(TileCrucible.class);
    }
}
