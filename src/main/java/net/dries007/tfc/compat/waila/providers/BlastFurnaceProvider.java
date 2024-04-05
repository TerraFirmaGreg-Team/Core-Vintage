package net.dries007.tfc.compat.waila.providers;

import net.dries007.tfc.api.capability.heat.Heat;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import su.terrafirmagreg.modules.device.objects.blocks.BlockBlastFurnace;
import su.terrafirmagreg.modules.device.objects.tiles.TEBlastFurnace;
import net.dries007.tfc.util.Helpers;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlastFurnaceProvider implements IWailaBlock {
	@NotNull
	@Override
	public List<String> getTooltip(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
		List<String> currentTooltip = new ArrayList<>();
		TEBlastFurnace blastFurnace = Helpers.getTE(world, pos, TEBlastFurnace.class);
		if (blastFurnace != null) {
			int chinmey = BlockBlastFurnace.getChimneyLevels(blastFurnace.getWorld(), blastFurnace.getPos());
			if (chinmey > 0) {
				int maxItems = chinmey * 4;
				int oreStacks = blastFurnace.getOreStacks().size();
				int fuelStacks = blastFurnace.getFuelStacks().size();
				float temperature = nbt.getFloat("temperature");
				String heatTooltip = Heat.getTooltip(temperature);
				currentTooltip.add(new TextComponentTranslation("waila.tfc.bloomery.ores", oreStacks, maxItems).getFormattedText());
				currentTooltip.add(new TextComponentTranslation("waila.tfc.bloomery.fuel", fuelStacks, maxItems).getFormattedText());
				if (heatTooltip != null) {
					currentTooltip.add(heatTooltip);
				}
			} else {
				currentTooltip.add(new TextComponentTranslation("waila.tfc.blast_furnace.not_formed").getFormattedText());
			}
		}
		return currentTooltip;
	}

	@NotNull
	@Override
	public List<Class<?>> getLookupClass() {
		return Collections.singletonList(TEBlastFurnace.class);
	}
}
