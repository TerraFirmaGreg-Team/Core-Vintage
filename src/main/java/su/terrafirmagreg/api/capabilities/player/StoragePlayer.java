package su.terrafirmagreg.api.capabilities.player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


import org.jetbrains.annotations.Nullable;

public class StoragePlayer implements IStorage<ICapabilityPlayer> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ICapabilityPlayer> capability, ICapabilityPlayer instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<ICapabilityPlayer> capability, ICapabilityPlayer instance, EnumFacing side, NBTBase nbt) {}
}
