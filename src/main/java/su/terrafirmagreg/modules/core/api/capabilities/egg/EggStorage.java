package su.terrafirmagreg.modules.core.api.capabilities.egg;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


import org.jetbrains.annotations.Nullable;

public class EggStorage implements IStorage<IEggCapability> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IEggCapability> capability, IEggCapability instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<IEggCapability> capability, IEggCapability instance, EnumFacing side, NBTBase nbt) {}
}
