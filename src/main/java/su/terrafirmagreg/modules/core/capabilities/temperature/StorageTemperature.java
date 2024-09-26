package su.terrafirmagreg.modules.core.capabilities.temperature;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StorageTemperature implements IStorage<ICapabilityTemperature> {

  @Override
  public NBTBase writeNBT(Capability<ICapabilityTemperature> capability,
                          ICapabilityTemperature instance, EnumFacing side) {
    return null;
  }

  @Override
  public void readNBT(Capability<ICapabilityTemperature> capability,
                      ICapabilityTemperature instance, EnumFacing side, NBTBase nbt) {
  }
}
