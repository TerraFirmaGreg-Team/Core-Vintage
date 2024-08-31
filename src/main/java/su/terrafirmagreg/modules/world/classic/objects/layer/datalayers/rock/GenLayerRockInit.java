package su.terrafirmagreg.modules.world.classic.objects.layer.datalayers.rock;

import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategory;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.world.classic.objects.layer.GenLayerBase;

import net.minecraft.world.gen.layer.IntCache;


import net.dries007.tfc.TerraFirmaCraft;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GenLayerRockInit extends GenLayerBase {

    private final int[] layerRocks;

    public GenLayerRockInit(long par1, final RockCategory.Layer rocks) {
        super(par1);
        layerRocks = RockType.getTypes().stream()
                .filter(rocks)
                .mapToInt(RockType::indexOf)
                .sorted().toArray();

        if (ConfigCore.MISC.DEBUG.debugWorldGenSafe) {
            TerraFirmaCraft.getLog().info("Worldgen rock list (ints): {}", layerRocks);
            TerraFirmaCraft.getLog().info("Worldgen rock list (names): {}",
                    Arrays.stream(layerRocks)
                            .mapToObj(RockType::valueOf)
                            .map(RockType::getName)
                            .collect(Collectors.joining(", ")));
        }
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
