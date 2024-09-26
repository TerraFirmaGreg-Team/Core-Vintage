package su.terrafirmagreg.modules.core.capabilities.worldtracker;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import org.jetbrains.annotations.Nullable;

public class StorageWorldTracker
  implements IStorage<ICapabilityWorldTracker> {

  @Nullable
  @Override
  public NBTBase writeNBT(Capability<ICapabilityWorldTracker> capability,
                          ICapabilityWorldTracker instance, EnumFacing side) {
    return null;
  }

  @Override
  public void readNBT(Capability<ICapabilityWorldTracker> capability,
                      ICapabilityWorldTracker instance, EnumFacing side, NBTBase nbt) {
  }
}
