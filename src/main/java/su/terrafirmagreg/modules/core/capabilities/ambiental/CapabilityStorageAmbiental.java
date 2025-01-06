package su.terrafirmagreg.modules.core.capabilities.ambiental;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class CapabilityStorageAmbiental implements IStorage<ICapabilityAmbiental> {

  @Override
  public NBTBase writeNBT(Capability<ICapabilityAmbiental> capability,
                          ICapabilityAmbiental instance, EnumFacing side) {
    return null;
  }

  @Override
  public void readNBT(Capability<ICapabilityAmbiental> capability,
                      ICapabilityAmbiental instance, EnumFacing side, NBTBase nbt) {
  }
}
