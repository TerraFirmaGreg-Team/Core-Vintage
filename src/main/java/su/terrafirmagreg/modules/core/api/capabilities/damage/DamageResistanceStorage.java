package su.terrafirmagreg.modules.core.api.capabilities.damage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class DamageResistanceStorage implements IStorage<IDamageResistanceCapability> {
	@Nullable
	@Override
	public NBTBase writeNBT(Capability<IDamageResistanceCapability> capability, IDamageResistanceCapability instance, EnumFacing side) {
		return null;
	}

	@Override
	public void readNBT(Capability<IDamageResistanceCapability> capability, IDamageResistanceCapability instance, EnumFacing side, NBTBase nbt) {}
}
