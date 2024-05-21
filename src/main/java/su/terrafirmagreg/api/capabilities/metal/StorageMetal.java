package su.terrafirmagreg.api.capabilities.metal;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


import org.jetbrains.annotations.Nullable;

public class StorageMetal implements IStorage<ICapabilityMetal> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ICapabilityMetal> capability, ICapabilityMetal instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<ICapabilityMetal> capability, ICapabilityMetal instance, EnumFacing side, NBTBase nbt) {}
}
