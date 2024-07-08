package su.terrafirmagreg.modules.world.objects.generator.groundcover;

import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;


import net.dries007.tfc.api.capability.chunkdata.ChunkData;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import tfcflorae.ConfigTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;

import java.util.Random;

public class GeneratorSurfaceBones implements IWorldGenerator {

    private double factor;

    public GeneratorSurfaceBones() {
        factor = 1;
    }

    public void setFactor(double factor) {
        if (factor < 0) factor = 0;
        if (factor > 1) factor = 1;
        this.factor = factor;
    }

    public int getBoneFrequency(World world, BlockPos pos, double groundBoneFrequency) {
        float rainfall = ChunkData.get(world, pos).getRainfall();

        if (rainfall <= 20)
            return (int) (groundBoneFrequency / (1D + Math.pow(0.7D, (double) rainfall - 5.9D)));
        else
            return (int) (groundBoneFrequency / (1D + Math.pow(1.15D, (double) rainfall - 50D)));
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        final BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);
        final ChunkData baseChunkData = ChunkData.get(world, chunkBlockPos);

        if (chunkGenerator instanceof ChunkGenClassic && world.provider.getDimension() == 0) {

            int xoff = chunkX * 16 + 8;
            int zoff = chunkZ * 16 + 8;

            //for (int i = 0; i < ConfigTFCF.General.WORLD.groundcoverBonesFrequency * factor; i++)
            for (int i = 0; i < getBoneFrequency(world, chunkBlockPos, ConfigTFCF.General.WORLD.groundcoverBonesFrequency); i++) {
                BlockPos pos = new BlockPos(
                        xoff + random.nextInt(16),
                        0,
                        zoff + random.nextInt(16)
                );
                generateRock(world, pos.up(world.getTopSolidOrLiquidBlock(pos).getY()));
            }
        }
    }

    private void generateRock(World world, BlockPos pos) {
        ChunkData data = ChunkData.get(world, pos);
        if (pos.getY() > 146 && pos.getY() < 170 && data.getRainfall() <= 75) {
            if (world.isAirBlock(pos) && world.getBlockState(pos.down())
                    .isSideSolid(world, pos.down(), EnumFacing.UP) &&
                    (BlocksTFC.isGround(world.getBlockState(pos.down())) || BlocksTFCF.isGround(world.getBlockState(pos.down())))) {
                world.setBlockState(pos, BlocksTFCF.BONES.getDefaultState());
            }
        }
    }
}
