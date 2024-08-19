package su.terrafirmagreg.modules.core.capabilities.heat;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


import org.jetbrains.annotations.Nullable;

public class StorageHeat implements IStorage<ICapabilityHeat> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ICapabilityHeat> capability, ICapabilityHeat instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<ICapabilityHeat> capability, ICapabilityHeat instance, EnumFacing side, NBTBase nbt) {}
}
