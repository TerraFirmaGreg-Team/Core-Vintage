package su.terrafirmagreg.modules.world.classic.objects.generator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.world.classic.ChunkGenClassic;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.MOSSY;
import static su.terrafirmagreg.modules.rock.init.BlocksRock.RAW;

public class GeneratorMossyRaw implements IWorldGenerator {

  public static final float RAINFALL_SAND = 75;
  public static final float RAINFALL_SAND_SANDY_MIX = 125;

  @Override
  public void generate(Random random, int chunkX, int chunkZ, World world,
                       IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    if (!(chunkGenerator instanceof ChunkGenClassic)) {
      return;
    }

    int y = random.nextInt(200 - WorldTypeClassic.ROCKLAYER2) + WorldTypeClassic.ROCKLAYER2;
    BlockPos chunkBlockPos = new BlockPos(chunkX << 4, y, chunkZ << 4);

    int rarity = random.nextInt(20) + 1;

    for (float r = rarity; r < (5 + rarity); r++) {
      var data = CapabilityChunkData.get(world, chunkBlockPos);
      final float floraDensity = data.getFloraDensity();
      final float floraDiversity = data.getFloraDiversity();

      if (data.isInitialized() && data.getRainfall() >= RAINFALL_SAND_SANDY_MIX) {
        int mossyCount = (random.nextInt(20) + 1);
        for (int i = random.nextInt(Math.round(1 + floraDiversity));
             i < (mossyCount + floraDensity) * 10; i++) {
          BlockPos blockPos = chunkBlockPos.add(random.nextInt(16) + 8, random.nextInt(16),
                                                random.nextInt(16) + 8);
          if (Variant.isVariant(world.getBlockState(blockPos), RAW) && BlockUtils.isBlockSurroundedByAir(world, blockPos) &&
              world.getLightFor(EnumSkyBlock.SKY, blockPos) < 14 && !world.canSeeSky(blockPos)) {
            world.setBlockState(blockPos, BlocksRock.RAW.get(ProviderChunkData.getRockHeight(world, blockPos)).getDefaultState()
                                                        .withProperty(MOSSY, Boolean.TRUE), 2);
          }
        }
      }
    }
  }
}
