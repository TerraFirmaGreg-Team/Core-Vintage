package net.dries007.tfc.world.classic.worldgen;

import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.agriculture.StorageAgriculture;
import net.dries007.tfc.module.agriculture.api.bush.type.BushType;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.ChunkGenTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WorldGenBerryBushes implements IWorldGenerator {
    private static final List<BushType> BUSHES = new ArrayList<>(BushType.getBushTypes());


    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (chunkGenerator instanceof ChunkGenTFC && world.provider.getDimension() == 0 && !BUSHES.isEmpty() && ConfigTFC.General.FOOD.berryBushRarity > 0) {
            if (random.nextInt(ConfigTFC.General.FOOD.berryBushRarity) == 0) {
                // Guarantees bush generation if possible (easier to balance by config file while also making it random)
                Collections.shuffle(BUSHES);
                BlockPos chunkBlockPos = new BlockPos(chunkX << 4, 0, chunkZ << 4);

                float temperature = ClimateTFC.getAvgTemp(world, chunkBlockPos);
                float rainfall = ChunkDataTFC.getRainfall(world, chunkBlockPos);
                var bush = BUSHES.stream().filter(x -> x.isValidConditions(temperature, rainfall)).findFirst().orElse(null);

                if (bush != null) {
                    final int x = (chunkX << 4) + random.nextInt(16) + 8;
                    final int z = (chunkZ << 4) + random.nextInt(16) + 8;
                    final BlockPos pos = world.getTopSolidOrLiquidBlock(new BlockPos(x, 0, z));

                    if (world.getBlockState(pos).getMaterial().isLiquid() || !world.getBlockState(pos).getMaterial().isReplaceable()) {
                        return;
                    }
                    var block = StorageAgriculture.getBushBlock(bush);
                    if (block.canPlaceBlockAt(world, pos)) {
                        world.setBlockState(pos, block.getDefaultState());
                    }
                }
            }
        }
    }
}
