package su.terrafirmagreg.modules.core.feature.climate;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * This stores climate data for when the world context is not available
 */
public final class ClimateCache {

  private final Map<ChunkPos, ClimateData> backingMap = new HashMap<>();

  @NotNull
  public ClimateData get(BlockPos pos) {
    return get(new ChunkPos(pos));
  }

  @NotNull
  public ClimateData get(ChunkPos pos) {
    return backingMap.getOrDefault(pos, ClimateData.DEFAULT);
  }

  public void update(ChunkPos pos, float temperature, float rainfall) {
    backingMap.put(pos, new ClimateData(temperature, rainfall));
  }
}
