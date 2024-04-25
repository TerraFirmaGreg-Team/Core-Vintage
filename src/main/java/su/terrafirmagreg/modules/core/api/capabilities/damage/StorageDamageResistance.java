package su.terrafirmagreg.modules.core.api.capabilities.damage;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


import org.jetbrains.annotations.Nullable;

public class StorageDamageResistance implements IStorage<ICapabilityDamageResistance> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<ICapabilityDamageResistance> capability, ICapabilityDamageResistance instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<ICapabilityDamageResistance> capability, ICapabilityDamageResistance instance, EnumFacing side, NBTBase nbt) {}
}
