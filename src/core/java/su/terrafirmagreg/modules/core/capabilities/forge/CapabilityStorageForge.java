package su.terrafirmagreg.modules.core.capabilities.forge;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import org.jetbrains.annotations.Nullable;

public class CapabilityStorageForge implements IStorage<ICapabilityForge> {

  @Nullable
  @Override
  public NBTBase writeNBT(Capability<ICapabilityForge> capability, ICapabilityForge instance, EnumFacing side) {
    return null;
  }

  @Override
  public void readNBT(Capability<ICapabilityForge> capability, ICapabilityForge instance, EnumFacing side, NBTBase nbt) {
  }
}
