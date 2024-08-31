package su.terrafirmagreg.modules.world.classic.objects.layer.datalayers.soil;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerBase;

import net.minecraft.world.gen.layer.IntCache;


import org.jetbrains.annotations.NotNull;

public class GenLayerSoilInit
        extends GenLayerBase {

    private final int[] layerRocks;

    public GenLayerSoilInit(long par1) {
        super(par1);
        layerRocks = SoilType.getTypes().stream()
                .mapToInt(SoilType::indexOf)
                .sorted().toArray();

    }

    @Override
    public int @NotNull [] getInts(int par1, int par2, int maxX, int maxZ) {
        int[] cache = IntCache.getIntCache(maxX * maxZ);

        for (int z = 0; z < maxZ; ++z) {
            for (int x = 0; x < maxX; ++x) {
                this.initChunkSeed(par1 + x, par2 + z);
                cache[x + z * maxX] = layerRocks[this.nextInt(layerRocks.length)];
            }
        }

        return cache;
    }
}
