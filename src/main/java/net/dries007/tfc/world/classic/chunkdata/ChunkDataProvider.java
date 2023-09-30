package net.dries007.tfc.world.classic.chunkdata;

import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Класс ChunkDataProvider реализует интерфейс ICapabilitySerializable для хранения данных чанка.
 */
public final class ChunkDataProvider implements ICapabilitySerializable<NBTTagCompound> {
    /**
     * Внедрение возможности (capability) для хранения данных чанка.
     */
    @CapabilityInject(ChunkDataTFC.class)
    public static final Capability<ChunkDataTFC> CHUNK_DATA_CAPABILITY = Helpers.getNull();

    /**
     * Создание экземпляра класса ChunkDataTFC, который будет хранить данные чанка.
     */
    private final ChunkDataTFC instance = CHUNK_DATA_CAPABILITY.getDefaultInstance();

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        // Проверяем, имеет ли объект указанную возможность (capability).
        // В данном случае, проверяем, является ли указанная возможность CHUNK_DATA_CAPABILITY.
        return capability == CHUNK_DATA_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        // Получаем указанную возможность (capability) для объекта.
        // В данном случае, возвращаем экземпляр ChunkDataTFC, если указанная возможность равна CHUNK_DATA_CAPABILITY.
        return capability == CHUNK_DATA_CAPABILITY ? CHUNK_DATA_CAPABILITY.cast(instance) : null;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        // Сериализуем данные объекта в NBTTagCompound.
        // Здесь мы используем возможность (capability) CHUNK_DATA_CAPABILITY для записи данных в NBTTagCompound.
        return (NBTTagCompound) CHUNK_DATA_CAPABILITY.writeNBT(instance, null);
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        // Десериализуем данные из NBTTagCompound и применяем их к объекту.
        // Здесь мы используем возможность (capability) CHUNK_DATA_CAPABILITY для чтения данных из NBTTagCompound.
        CHUNK_DATA_CAPABILITY.readNBT(instance, null, nbt);
    }
}
