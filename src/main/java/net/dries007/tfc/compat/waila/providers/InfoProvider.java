package net.dries007.tfc.compat.waila.providers;

import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InfoProvider implements IWailaBlock {
	@NotNull
	@Override
	public List<String> getTooltip(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
		List<String> currentTooltip = new ArrayList<>();
		int temperature = Math.round(ClimateTFC.getActualTemp(world, pos, 0));
		int rainfall = Math.round(ClimateTFC.getRainfall(world, pos));
		currentTooltip.add(new TextComponentTranslation("waila.tfc.temperature", temperature).getFormattedText());
		currentTooltip.add(new TextComponentTranslation("waila.tfc.rainfall", rainfall).getFormattedText());
		return currentTooltip;
	}

	@NotNull
	@Override
	public List<Class<?>> getLookupClass() {
		return Collections.singletonList(BlockRockVariant.class);
	}
}
