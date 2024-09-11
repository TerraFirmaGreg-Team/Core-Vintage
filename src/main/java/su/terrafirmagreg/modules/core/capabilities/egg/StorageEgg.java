package su.terrafirmagreg.modules.core.capabilities.egg;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


import org.jetbrains.annotations.Nullable;

public class StorageEgg implements IStorage<ICapabilityEgg> {

  @Nullable
  @Override
  public NBTBase writeNBT(Capability<ICapabilityEgg> capability, ICapabilityEgg instance,
          EnumFacing side) {
    return null;
  }

  @Override
  public void readNBT(Capability<ICapabilityEgg> capability, ICapabilityEgg instance,
          EnumFacing side, NBTBase nbt) {
  }
}
