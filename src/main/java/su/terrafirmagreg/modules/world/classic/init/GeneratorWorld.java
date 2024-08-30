package su.terrafirmagreg.modules.world.classic.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorLargeRocks;
import su.terrafirmagreg.modules.world.classic.objects.generator.GeneratorMossyRaw;

import net.minecraftforge.fml.common.IWorldGenerator;

public final class GeneratorWorld {

    public static IWorldGenerator MOSSY_RAW;
    public static IWorldGenerator LARGE_ROCKS;

    public static void onRegister(RegistryManager registry) {

        MOSSY_RAW = registry.worldGenerator(new GeneratorMossyRaw());
        LARGE_ROCKS = registry.worldGenerator(new GeneratorLargeRocks());
    }
}
