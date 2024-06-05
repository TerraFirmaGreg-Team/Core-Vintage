package net.dries007.tfc.api.capability.chunkdata;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;


import net.dries007.tfc.util.Helpers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ChunkDataProvider implements ICapabilitySerializable<NBTTagCompound> {

    @CapabilityInject(ChunkData.class)
    public static final Capability<ChunkData> CHUNK_DATA_CAPABILITY = Helpers.getNull();

    private final ChunkData instance = CHUNK_DATA_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CHUNK_DATA_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CHUNK_DATA_CAPABILITY ? CHUNK_DATA_CAPABILITY.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return (NBTTagCompound) CHUNK_DATA_CAPABILITY.writeNBT(instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        CHUNK_DATA_CAPABILITY.readNBT(instance, null, nbt);
    }
}
