package su.terrafirmagreg.modules.core.capabilities.size;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


import org.jetbrains.annotations.Nullable;

public class StorageSize implements IStorage<ICapabilitySize> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ICapabilitySize> capability, ICapabilitySize instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<ICapabilitySize> capability, ICapabilitySize instance, EnumFacing side, NBTBase nbt) {}
}
