package su.terrafirmagreg.modules.world.debug;

import su.terrafirmagreg.api.util.WorldUtils;
import su.terrafirmagreg.modules.world.ModuleWorld;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.Loader;

public class WorldTypeDebug extends WorldType {

  /**
   * The ID that is automatically assigned to this world type. If it is 0 it hasn't been set yet.
   */
  private int ourId = 0;

  public WorldTypeDebug() {
    super("debug_all_block_states");

    // Sets the ID field to the one that was assigned to us.
    this.ourId = this.getId();

    // Set our registry slot to null, to free up the ID space.
    WorldType.WORLD_TYPES[this.ourId] = null;

    // Overrides the debug world ID with this world type.
    WorldType.WORLD_TYPES[5] = this;
  }

  @Override
  public int getId() {

    // If ourId has not been set yet return the super id, otherwise hardcode for the debug
    // world type id.
    return this.ourId == 0 ? super.getId() : 5;
  }

  @Override
  public IChunkGenerator getChunkGenerator(World world, String generatorOptions) {

    // Try to get the modid from the world name.
    final String modid = WorldUtils.getWorldName(world).toLowerCase();

    // If the mod actually exists, use that generator.
    if (Loader.isModLoaded(modid)) {
      return new ChunkGenDebug(world, modid);
    }

    ModuleWorld.LOGGER.error("No mod found for ID {}, falling back to default worldgen.", modid);
    // Use the fallback generator.
    return new ChunkGeneratorDebug(world);
  }
}
