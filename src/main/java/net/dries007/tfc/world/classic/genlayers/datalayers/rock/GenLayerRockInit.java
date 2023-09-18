package net.dries007.tfc.world.classic.genlayers.datalayers.rock;

import net.dries007.tfc.module.core.submodule.rock.api.category.RockCategory;
import net.dries007.tfc.module.core.submodule.rock.api.type.RockType;
import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;
import net.minecraft.world.gen.layer.IntCache;

import javax.annotation.Nonnull;

public class GenLayerRockInit extends GenLayerTFC {
    private final int[] layerRocks;

    public GenLayerRockInit(long par1, final RockCategory.Layer rocks) {
        super(par1);

        layerRocks = RockType.getRockTypes().stream()
                .filter(rocks)
                .mapToInt(RockType::indexOf)
                .sorted().toArray();
    }

    @Override
    @Nonnull
    public int[] getInts(int par1, int par2, int maxX, int maxZ) {
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
