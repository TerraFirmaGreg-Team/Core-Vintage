package su.terrafirmagreg.modules.core.capabilities.pull;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StoragePull implements IStorage<ICapabilityPull> {

  @Override
  public NBTBase writeNBT(Capability<ICapabilityPull> capability, ICapabilityPull instance,
          EnumFacing side) {
    return null;
  }

  @Override
  public void readNBT(Capability<ICapabilityPull> capability, ICapabilityPull instance,
          EnumFacing side, NBTBase nbt) {
  }
}
