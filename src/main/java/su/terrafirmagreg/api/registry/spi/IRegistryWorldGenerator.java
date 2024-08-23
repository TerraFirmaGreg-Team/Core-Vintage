package su.terrafirmagreg.api.registry.spi;

import su.terrafirmagreg.api.util.WorldUtils;

import net.minecraftforge.fml.common.IWorldGenerator;

public interface IRegistryWorldGenerator
        extends IRegistryBase {

    default IWorldGenerator worldGenerator(IWorldGenerator generator) {

        return worldGenerator(generator, 0);
    }

    default IWorldGenerator worldGenerator(IWorldGenerator generator, int modGenerationWeight) {
        WorldUtils.registerWorldGenerator(generator, modGenerationWeight);

        return generator;
    }

}
