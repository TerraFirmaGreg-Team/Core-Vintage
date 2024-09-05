package su.terrafirmagreg.modules.core.capabilities.chunkdata;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public final class CapabilityChunkData {

  public static final ResourceLocation KEY = ModUtils.resource("chunkdata_capability");

  @CapabilityInject(ICapabilityChunkData.class)
  public static final Capability<ICapabilityChunkData> CAPABILITY = ModUtils.getNull();

  public static void register() {
    CapabilityManager.INSTANCE.register(ICapabilityChunkData.class, new StorageChunkData(),
        ProviderChunkData::new);
  }

  /**
   * Возвращает экземпляр ChunkDataTFC для указанного мира и позиции.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Экземпляр ChunkDataTFC для указанного мира и позиции.
   */
  public static ICapabilityChunkData get(World world, BlockPos pos) {
    return get(world.getChunk(pos));
  }

  /**
   * Возвращает экземпляр ChunkDataTFC для указанного чанка.
   *
   * @param chunk Чанк.
   * @return Экземпляр ChunkDataTFC для указанного чанка.
   */
  public static ICapabilityChunkData get(Chunk chunk) {
    var data = chunk.getCapability(CAPABILITY, null);
    return data == null ? ProviderChunkData.EMPTY : data;
  }

  public static boolean has(Chunk chunk) {
    return chunk.hasCapability(CAPABILITY, null);
  }
}
