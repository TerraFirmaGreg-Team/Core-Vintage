package su.terrafirmagreg.modules.worldgen.debugworld;

import su.terrafirmagreg.api.util.WorldUtils;
import su.terrafirmagreg.modules.worldgen.ModuleWorldGen;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGeneratorDebug;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.Loader;


import org.jetbrains.annotations.NotNull;

public class WorldTypeDebugMod extends WorldType {

    /**
     * The ID that is automatically assigned to this world type. If it is 0 it hasn't been set yet.
     */
    private int ourId = 0;

    public WorldTypeDebugMod() {
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
    public @NotNull IChunkGenerator getChunkGenerator(@NotNull World world, @NotNull String generatorOptions) {

        // Try to get the modid from the world name.
        final String modid = WorldUtils.getWorldName(world).toLowerCase();

        // If the mod actually exists, use that generator.
        if (Loader.isModLoaded(modid)) {
            return new ChunkGeneratorDebugMod(world, modid);
        } else {
            ModuleWorldGen.LOGGER.error("No mod found for ID {}, falling back to default worldgen.", modid);
        }

        // Use the fallback generator.
        return new ChunkGeneratorDebug(world);
    }
}
