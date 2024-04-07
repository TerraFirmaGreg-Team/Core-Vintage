package su.terrafirmagreg.modules.core.api.capabilities.size;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import org.jetbrains.annotations.Nullable;

public class SizeStorage implements IStorage<ISizeCapability> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ISizeCapability> capability, ISizeCapability instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<ISizeCapability> capability, ISizeCapability instance, EnumFacing side, NBTBase nbt) {}
}
