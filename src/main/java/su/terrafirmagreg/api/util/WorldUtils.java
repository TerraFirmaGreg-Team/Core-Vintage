package su.terrafirmagreg.api.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;


import lombok.experimental.UtilityClass;

import static com.google.common.math.Stats.meanOf;

@UtilityClass
@SuppressWarnings("unused")
public final class WorldUtils {

  /**
   * Gets the display name of a world.
   *
   * @param world The world to get the name of.
   * @return The name of the world.
   */
  public static String getWorldName(World world) {

    String result = "Unknown";
    if (world instanceof WorldServer) {
      result = world.getSaveHandler().getWorldDirectory().getName();
    }

    return result;
  }

  /**
   * Gets the amount of loaded chunks.
   *
   * @param world The world to get the chunk count of.
   * @return The amount of chunks. -1 means it was unable to get the amount.
   */
  public static int getLoadedChunks(WorldServer world) {

    return world.getChunkProvider() != null ? world.getChunkProvider().getLoadedChunkCount() : -1;
  }

  public static String getDimensionName(World world) {

    String result = "Unknown";

    // TODO add more fallback options
    if (world.provider != null) {
      result = world.provider.getDimensionType().getName();
    }

    return result;
  }

  /**
   * Gets the dimension id of a world.
   *
   * @param world The world to get the id of.
   * @return The id of the world. 0 (surface) is used if none is found.
   */
  public static int getDimId(WorldServer world) {

    return world.provider != null ? world.provider.getDimension() : 0;
  }

  /**
   * Checks if two block positions are in the same chunk in a given world.
   *
   * @param first  The first position.
   * @param second The second position.
   * @return Whether or not the two positions are in the same chunk.
   */
  public static boolean areSameChunk(BlockPos first, BlockPos second) {

    return new ChunkPos(first).equals(new ChunkPos(second));
  }

  /**
   * Checks if the dimension id of a world matches the provided dimension id.
   *
   * @param world The world to check.
   * @param id    The dimension id you are looking for.
   * @return Whether or not they are the same.
   */
  public static boolean isDimension(World world, int id) {

    return getDimensionId(world) == id;
  }

  /**
   * Checks if the dimension type of a world matches the provided dimension type.
   *
   * @param world The world to check.
   * @param type  The dimension type you are looking for.
   * @return Whether or not they are the same.
   */
  public static boolean isDimension(World world, DimensionType type) {

    return getDimensionType(world) == type;
  }

  /**
   * Gets the dimension id of a world.
   *
   * @param world The world you are looking into.
   * @return The id of the world.
   */
  public static int getDimensionId(World world) {

    return getDimensionType(world).getId();
  }

  /**
   * Gets the dimension type of a world.
   *
   * @param world The world you are looking into.
   * @return The type of the world.
   */
  public static DimensionType getDimensionType(World world) {

    return world.provider.getDimensionType();
  }

  public static double getTPS(World world, int dimId) {
    if (world == null || world.getMinecraftServer() == null) {
      return -1D;
    }
    double worldTickTime = meanOf(world.getMinecraftServer().worldTickTimes.get(dimId)) * 1.0E-6D;
    return Math.min(1000.0D / worldTickTime, 20.0D);
  }

  public static void registerWorldGenerator(IWorldGenerator generator, int modGenerationWeight) {
    GameRegistry.registerWorldGenerator(generator, modGenerationWeight);
  }
}
