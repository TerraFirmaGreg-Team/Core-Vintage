package su.terrafirmagreg.api.capabilities.skill;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


import org.jetbrains.annotations.Nullable;

public class StorageSkill implements IStorage<ICapabilitySkill> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ICapabilitySkill> capability, ICapabilitySkill instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<ICapabilitySkill> capability, ICapabilitySkill instance, EnumFacing side, NBTBase nbt) {}
}
