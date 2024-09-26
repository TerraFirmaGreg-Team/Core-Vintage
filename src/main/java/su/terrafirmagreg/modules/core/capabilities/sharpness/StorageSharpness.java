package su.terrafirmagreg.modules.core.capabilities.sharpness;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import org.jetbrains.annotations.Nullable;

public class StorageSharpness implements IStorage<ICapabilitySharpness> {

  @Nullable
  @Override
  public NBTBase writeNBT(Capability<ICapabilitySharpness> capability,
                          ICapabilitySharpness instance, EnumFacing side) {
    return new NBTTagInt(instance.getCharges());
  }

  @Override
  public void readNBT(Capability<ICapabilitySharpness> capability, ICapabilitySharpness instance,
                      EnumFacing side, NBTBase nbt) {
    instance.setCharges(((NBTPrimitive) nbt).getInt());
  }
}
