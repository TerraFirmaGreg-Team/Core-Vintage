package su.terrafirmagreg.modules.core.api.capabilities.metal;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class MetalStorage implements IStorage<IMetalCapability> {
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<IMetalCapability> capability, IMetalCapability instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<IMetalCapability> capability, IMetalCapability instance, EnumFacing side, NBTBase nbt) {}
}
