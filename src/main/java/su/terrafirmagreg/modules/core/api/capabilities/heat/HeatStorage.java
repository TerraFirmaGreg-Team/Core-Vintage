package su.terrafirmagreg.modules.core.api.capabilities.heat;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;


import org.jetbrains.annotations.Nullable;

public class HeatStorage implements IStorage<IHeatCapability> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IHeatCapability> capability, IHeatCapability instance, EnumFacing side) {
        return null;
    }

    @Override
    public void readNBT(Capability<IHeatCapability> capability, IHeatCapability instance, EnumFacing side, NBTBase nbt) {}
}
