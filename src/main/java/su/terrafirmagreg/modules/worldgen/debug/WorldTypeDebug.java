package su.terrafirmagreg.modules.worldgen.debug;

import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.api.util.WorldUtils;
import su.terrafirmagreg.modules.worldgen.ModuleWorld;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraft.world.gen.IChunkGenerator;

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
    final String worldName = WorldUtils.getWorldName(world).toLowerCase();
    final String[] split = worldName.split("_", 2);
    final String modid = split[0];
    final String blockName = split.length > 1 ? split[1] : null;

    // If the mod actually exists, use that generator.
    if (GameUtils.isModLoaded(modid)) {
      return new ChunkGenDebug(world, modid, blockName);
    }

    ModuleWorld.LOGGER.error("No mod found for ID {}.", modid);
    // Use the fallback generator.
    return new ChunkGeneratorDebug(world);
  }
}
