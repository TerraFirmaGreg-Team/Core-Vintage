package su.terrafirmagreg.modules.world.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.world.objects.generator.GeneratorMossyRaw;

public final class GeneratorWorld {

    public static void onRegister(RegistryManager registry) {

        registry.worldGenerator(new GeneratorMossyRaw());
    }
}
