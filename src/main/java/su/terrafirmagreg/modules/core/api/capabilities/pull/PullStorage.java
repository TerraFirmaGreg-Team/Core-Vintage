package su.terrafirmagreg.modules.core.api.capabilities.pull;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PullStorage implements IStorage<IPullCapability> {
	@Override
	public NBTBase writeNBT(Capability<IPullCapability> capability, IPullCapability instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<IPullCapability> capability, IPullCapability instance, EnumFacing side, NBTBase nbt) {

	}
}
