package net.dries007.tfc.world.classic.chunkdata;

import net.dries007.tfc.Tags;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.core.ModuleCoreOld;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.network.SCPacketChunkData;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.world.ChunkWatchEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


/**
 * Класс CapabilityChunkData содержит методы для работы с возможностью (capability) хранения данных чанка.
 */
@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class CapabilityChunkData {
    /**
     * Ресурсное имя для Capability хранения данных чанка.
     */
    public static final ResourceLocation CHUNK_DATA = Helpers.getID("chunkdata");

    /**
     * Метод preInit() выполняет регистрацию Capability для хранения данных чанка.
     */
    public static void preInit() {
        // Регистрация Capability для хранения данных чанка
        CapabilityManager.INSTANCE.register(ChunkDataTFC.class, new ChunkDataTFC.ChunkDataStorage(), ChunkDataTFC::new);
    }

    /**
     * Метод onAttachCapabilitiesChunk() добавляет возможность (capability) для хранения данных чанка при присоединении Capability к объекту Chunk.
     */
    @SubscribeEvent
    public static void onAttachCapabilitiesChunk(AttachCapabilitiesEvent<Chunk> event) {
        // Проверяем, что мир существует и его тип соответствует TerraFirmaCraft
        // В противном случае, что-то нарушает наши предположения и мы просто завершаемся
        //noinspection ConstantConditions
        if (event.getObject().getWorld() != null && event.getObject().getWorld().getWorldType() == TerraFirmaCraft.WORLD_TYPE_TFC) {
            // Добавляем возможность (capability) для хранения данных чанка
            event.addCapability(CHUNK_DATA, new ChunkDataProvider());
        }
    }

    /**
     * Метод onChunkWatchWatch() обрабатывает событие наблюдения за чанком и обновляет данные на стороне сервера и клиента.
     */
    @SubscribeEvent
    public static void onChunkWatchWatch(ChunkWatchEvent.Watch event) {
        Chunk chunk = event.getChunkInstance();
        if (chunk != null) {
            ChunkDataTFC data = chunk.getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);
            if (data != null && data.isInitialized()) {
                // Обновляем климат на стороне сервера
                ClimateTFC.update(chunk.getPos(), data.getRegionalTemp(), data.getRainfall());

                // Обновляем данные на стороне клиента
                NBTTagCompound nbt = (NBTTagCompound) ChunkDataProvider.CHUNK_DATA_CAPABILITY.writeNBT(data, null);
                ModuleCoreOld.PACKET_SERVICE.sendTo(new SCPacketChunkData(chunk.getPos(), nbt, data.getRegionalTemp(), data.getRainfall()), event.getPlayer());
            }
        }
    }
}
