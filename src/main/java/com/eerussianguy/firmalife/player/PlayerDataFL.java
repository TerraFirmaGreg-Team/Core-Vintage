package com.eerussianguy.firmalife.player;

import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerDataFL implements ICapabilitySerializable<NBTTagCompound>, IPlayerDataFL {
	private long nutted;
	private BlockPos nutPosition;

	public PlayerDataFL() {
		this.nutted = 0;
		this.nutPosition = new BlockPos(0, 0, 0);
	}

	@Override
	public void setNuttedTime() {
		nutted = CalendarTFC.CALENDAR_TIME.getTicks();
	}

	@Override
	public long getNuttedTime() {
		return nutted;
	}

	@Override
	public void setNutPosition(BlockPos pos) {
		nutPosition = pos;
	}

	@Override
	public int getNutDistance(BlockPos pos) {
		return (int) Math.sqrt(nutPosition.distanceSq(pos));
	}

	@Override
	public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityPlayerData.CAPABILITY;
	}

	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
		return capability == CapPlayerDataFL.CAPABILITY ? (T) this : null;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setLong("nutted", nutted);
		nbt.setLong("pos", nutPosition.toLong());
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		if (nbt != null) {
			nutted = nbt.getLong("nutted");
			nutPosition = BlockPos.fromLong(nbt.getLong("pos"));
		}
	}
}
