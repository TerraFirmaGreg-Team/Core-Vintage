package net.dries007.tfc.compat.waila.providers;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.items.metal.ItemIngot;
import net.dries007.tfc.objects.te.TEIngotPile;
import net.dries007.tfc.util.Helpers;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class IngotPileProvider implements IWailaBlock {

	@NotNull
	@Override
	public ItemStack getIcon(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
		TEIngotPile ingotPile = Helpers.getTE(world, pos, TEIngotPile.class);
		if (ingotPile != null) {
			return new ItemStack(ItemIngot.get(ingotPile.getMetal(), Metal.ItemType.INGOT), ingotPile.getCount());
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
