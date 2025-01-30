package su.terrafirmagreg.modules.core.capabilities.playerdata;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import org.jetbrains.annotations.Nullable;

public class StoragePlayerData implements IStorage<ICapabilityPlayerData> {

  @Nullable
  @Override
  public NBTBase writeNBT(Capability<ICapabilityPlayerData> capability, ICapabilityPlayerData instance,
                          EnumFacing side) {
    return null;
  }

  @Override
  public void readNBT(Capability<ICapabilityPlayerData> capability, ICapabilityPlayerData instance,
                      EnumFacing side, NBTBase nbt) {
  }
}
