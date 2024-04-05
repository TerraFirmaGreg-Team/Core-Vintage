package net.dries007.tfc.compat.waila.providers;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.items.metal.ItemSmallOre;
import net.dries007.tfc.objects.items.rock.ItemRock;
import net.dries007.tfc.objects.te.TEPlacedItemFlat;
import net.dries007.tfc.util.Helpers;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlacedItemProvider implements IWailaBlock {
	@NotNull
	@Override
	public List<String> getTooltip(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
		List<String> currentTooltip = new ArrayList<>();
		TEPlacedItemFlat te = Helpers.getTE(world, pos, TEPlacedItemFlat.class);
		if (te != null) {
			ItemStack stack = te.getStack();
			if (stack.getItem() instanceof ItemSmallOre) {
				ItemSmallOre nugget = (ItemSmallOre) stack.getItem();
				Ore ore = nugget.getOre();
				Metal metal = ore.getMetal();
				if (metal != null) {
					currentTooltip.add(new TextComponentTranslation("waila.tfc.ore_drop", new TextComponentTranslation(metal.getTranslationKey()).getFormattedText()).getFormattedText());
				}
			}
			if (stack.getItem() instanceof ItemRock) {
				ItemRock pebble = (ItemRock) stack.getItem();
				Rock rock = pebble.getRock(stack);
				if (rock.isFluxStone()) {
					currentTooltip.add(new TextComponentTranslation("waila.tfc.flux_stone").getFormattedText());
				}

			}
		}
		return currentTooltip;
	}

	@NotNull
	@Override
	public List<Class<?>> getLookupClass() {
		return Collections.singletonList(TEPlacedItemFlat.class);
	}
}
