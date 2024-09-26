package su.terrafirmagreg.modules.core.capabilities.food;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import org.jetbrains.annotations.Nullable;

public class StorageFood implements IStorage<ICapabilityFood> {

  @Nullable
  @Override
  public NBTBase writeNBT(Capability<ICapabilityFood> capability, ICapabilityFood instance,
                          EnumFacing side) {
    return null;
  }

  @Override
  public void readNBT(Capability<ICapabilityFood> capability, ICapabilityFood instance,
                      EnumFacing side, NBTBase nbt) {
  }
}
