package su.terrafirmagreg.modules.world.classic;

import su.terrafirmagreg.modules.world.objects.layer.GenLayerBase;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;


import java.util.ArrayList;
import java.util.List;

public class BiomeProviderClassic extends BiomeProvider {

    public static final List<Biome> WORLD_GEN_BIOMES = new ArrayList<>();

    public BiomeProviderClassic(World world) {
        super(world.getWorldInfo());

        if (!(world.getWorldType() instanceof WorldTypeClassic)) {
            throw new RuntimeException("Terrible things have gone wrong here.");
        }
    }

    /**
     * This is where we do the actual override of the generation, we discard the original and insert our own.
     */
    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
        return GenLayerBase.initializeBiomes(seed);
    }
}
