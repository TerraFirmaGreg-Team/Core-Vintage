package su.terrafirmagreg.modules.world.objects.generator;

import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;
import su.terrafirmagreg.modules.world.objects.generator.vein.Vein;
import su.terrafirmagreg.modules.world.objects.generator.vein.VeinRegistry;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;


import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.chunkdata.ChunkData;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static su.terrafirmagreg.data.lib.MathConstants.RNG;

public class GeneratorOreVeins implements IWorldGenerator {

    public static int CHUNK_RADIUS = 1;

    // Used to generate chunk
    public static List<Vein> getNearbyVeins(int chunkX, int chunkZ, long worldSeed, int radius) {
        List<Vein> veins = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                getVeinsAtChunk(veins, chunkX + x, chunkZ + z, worldSeed);
            }
        }
        return veins;
    }

    // Gets veins at a single chunk. Deterministic for a specific chunk x/z and world seed
    private static void getVeinsAtChunk(List<Vein> listToAdd, int chunkX, int chunkZ, long worldSeed) {
        RNG.setSeed(worldSeed + chunkX * 341873128712L + chunkZ * 132897987541L);
        listToAdd.addAll(VeinRegistry.INSTANCE.getVeins().values().stream()
                .filter(veinType -> RNG.nextInt(veinType.getRarity()) == 0)
                .map(veinType -> veinType.createVein(RNG, chunkX, chunkZ))
                .collect(Collectors.toList()));
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (!(chunkGenerator instanceof ChunkGenClassic)) return;
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        ChunkData chunkData = ChunkData.get(world, chunkBlockPos);
        if (!chunkData.isInitialized()) return;
        if (world.provider.getDimension() != 0) return;

        List<Vein> veins = getNearbyVeins(chunkX, chunkZ, world.getSeed(), CHUNK_RADIUS);

        for (Vein vein : veins) {
            boolean generated = false;
            for (int x = chunkBlockPos.getX() + 8; x < chunkBlockPos.getX() + 24; x++) {
                for (int z = chunkBlockPos.getZ() + 8; z < chunkBlockPos.getZ() + 24; z++) {
                    // Do checks here that are specific to the the horizontal position, not the vertical one
                    if (vein.inRange(x, z, 0)) {
                        for (int y = vein.getLowestY(); y <= vein.getHighestY(); y++) {
                            final BlockPos posAt = new BlockPos(x, y, z);
                            final IBlockState stateAt = world.getBlockState(posAt);

                            // Do checks specific to the individual block pos that is getting replaced
                            if (random.nextDouble() < vein.getChanceToGenerate(posAt) && stateAt.getBlock() instanceof BlockRockVariant blockAt) {
                                if (blockAt.getType() == Rock.Type.RAW && vein.canSpawnIn(blockAt.getRock())) {
                                    world.setBlockState(posAt, vein.getOreState(blockAt.getRock()), 2);
                                    generated = true;
                                }
                            }
                        }
                    }
                }
            }
            // Chunk post-processing, if a vein generated
            if (vein.getType() != null) {
                if (generated) {
                    chunkData.markVeinGenerated(vein);
                } else if (ConfigCore.MISC.DEBUG.enable) {
                    // Failed to generate, debug info
                    // This can be by a number of factors, mainly because at each expected replacing position we didn't find a matching raw rock.
                    // Some possible causes: Width / Height / Shape / Density / Y / Rock Layer
                    TerraFirmaCraft.getLog()
                            .debug("Failed to generate vein '{}' in chunk ({}, {}). Vein center pos ({}x, {}y, {}z)", vein.getType()
                                    .getRegistryName(), chunkX, chunkZ, vein.getPos()
                                    .getX(), vein.getPos()
                                    .getY(), vein.getPos()
                                    .getZ());
                }
            }
        }
    }
}
