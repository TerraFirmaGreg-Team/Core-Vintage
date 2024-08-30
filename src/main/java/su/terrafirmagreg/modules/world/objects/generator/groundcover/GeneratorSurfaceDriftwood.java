package su.terrafirmagreg.modules.world.objects.generator.groundcover;

import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;
import su.terrafirmagreg.modules.world.init.BiomesWorld;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;


import net.dries007.tfc.api.capability.chunkdata.ChunkData;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.BlocksTFCF;
import tfcflorae.ConfigTFCF;

import java.util.Random;

public class GeneratorSurfaceDriftwood implements IWorldGenerator {

    private double factor;

    public GeneratorSurfaceDriftwood() {
        factor = 1;
    }

    public void setFactor(double factor) {
        if (factor < 0) factor = 0;
        if (factor > 1) factor = 1;
        this.factor = factor;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (chunkGenerator instanceof ChunkGenClassic && world.provider.getDimension() == 0) {
            int xoff = chunkX * 16 + 8;
            int zoff = chunkZ * 16 + 8;

            final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
            final ChunkData baseChunkData = ChunkData.get(world, chunkBlockPos);

            for (int i = 0; i < ConfigTFCF.General.WORLD.groundcoverDriftwoodFrequency * factor; i++) {
                BlockPos pos = new BlockPos(
                        xoff + random.nextInt(16),
                        0,
                        zoff + random.nextInt(16)
                );
                generateRock(world, random, pos.up(world.getTopSolidOrLiquidBlock(pos).getY()));
            }
        }
    }

    private void generateRock(World world, Random rand, BlockPos pos) {
        if (pos.getY() >= WorldTypeClassic.SEALEVEL && pos.getY() < WorldTypeClassic.SEALEVEL + 2) {
            final Biome b = world.getBiome(pos);
            if (b == BiomesWorld.OCEAN || b == BiomesWorld.DEEP_OCEAN || b == BiomesWorld.BEACH || b == BiomesWorld.GRAVEL_BEACH) {
                if (world.isAirBlock(pos) && world.getBlockState(pos.down())
                        .isSideSolid(world, pos.down(), EnumFacing.UP) && BlocksTFC.isGround(world.getBlockState(pos.down()))) {
                    world.setBlockState(pos, BlocksTFCF.DRIFTWOOD.getDefaultState());
                }
            }
        }
    }
}
