package su.terrafirmagreg.modules.core.api.capabilities.heat;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HeatProvider implements ICapabilitySerializable<NBTBase> {

	final Capability<IHeatCapability> capability;
	final IHeatCapability instance;

	public HeatProvider(Capability<IHeatCapability> newCapability) {
		this.capability = newCapability;
		this.instance = capability.getDefaultInstance();
	}

	@Override
	public boolean hasCapability(@Nonnull Capability<?> requestedCapability, @Nullable EnumFacing facing) {

		return (requestedCapability == this.capability);
	}

	@Nullable
	@Override
	public <T> T getCapability(@Nonnull Capability<T> requestedCapability, @Nullable EnumFacing facing) {
		if (requestedCapability == this.capability) {
			return capability.cast(this.instance);
		}

		return null;
	}

	@Override
	public NBTBase serializeNBT() {
		return this.capability.writeNBT(this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		this.capability.readNBT(this.instance, null, nbt);
	}
}
