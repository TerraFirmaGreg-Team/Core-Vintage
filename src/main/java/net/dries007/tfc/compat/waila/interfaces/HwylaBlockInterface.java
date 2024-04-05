package net.dries007.tfc.compat.waila.interfaces;

import mcp.mobius.waila.api.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Does the direct "translation" from IWailaBlock to Hwyla
 */
public class HwylaBlockInterface implements IWailaDataProvider, IWailaPlugin {
	protected final IWailaBlock internal;

	public HwylaBlockInterface(IWailaBlock internal) {
		this.internal = internal;
	}

	@Override
	public void register(IWailaRegistrar registrar) {
		// Register providers accordingly to each implementation
		for (Class<?> clazz : internal.getLookupClass()) {
			if (TileEntity.class.isAssignableFrom(clazz)) {
				// Register to update NBT data on all tile entities.
				registrar.registerNBTProvider(this, clazz);
			}
			if (internal.appendBody()) {
				registrar.registerBodyProvider(this, clazz);
			}
			if (internal.overrideTitle()) {
				registrar.registerHeadProvider(this, clazz);
			}
			if (internal.overrideIcon()) {
				registrar.registerStackProvider(this, clazz);
			}
		}
	}

	@NotNull
	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return internal.getIcon(accessor.getWorld(), accessor.getPosition(), accessor.getNBTData());
	}

	@NotNull
	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currentTooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		String title = internal.getTitle(accessor.getWorld(), accessor.getPosition(), accessor.getNBTData());
		if (!title.isEmpty()) {
			currentTooltip.clear();
			currentTooltip.add(TextFormatting.WHITE.toString() + title);
		}
		return currentTooltip;
	}

	@NotNull
	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> currentTooltip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		List<String> body = internal.getTooltip(accessor.getWorld(), accessor.getPosition(), accessor.getNBTData());
		if (!body.isEmpty()) {
			currentTooltip.addAll(internal.getTooltip(accessor.getWorld(), accessor.getPosition(), accessor.getNBTData()));
		}
		return currentTooltip;
	}

	@NotNull
	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		return te.writeToNBT(tag);
	}
}
