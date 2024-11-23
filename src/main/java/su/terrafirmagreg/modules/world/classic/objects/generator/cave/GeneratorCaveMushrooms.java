package su.terrafirmagreg.modules.world.classic.objects.generator.cave;

import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import net.dries007.tfc.objects.blocks.groundcover.BlockCaveMushroom;

import java.util.Random;

import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BLUESHROOM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GLOWSHROOM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MAGMA_SHROOM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.POISON_SHROOM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SULPHUR_SHROOM;

public class GeneratorCaveMushrooms extends WorldGenerator {

  @Override
  public boolean generate(World worldIn, Random rng, BlockPos pos) {
    int chance = rng.nextInt(5);
    if (chance == 0) {
      BlockCaveMushroom mushroomBlock = (BlockCaveMushroom) BlocksPlant.PLANT.get(BLUESHROOM);
      IBlockState state = mushroomBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, pos) / 16; ++i) {
        BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4),
                                    rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

        if (worldIn.isAirBlock(blockpos) &&
            pos.getY() < WorldTypeClassic.SEALEVEL - 3 &&
            worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 &&
            !worldIn.canSeeSky(blockpos) &&
            mushroomBlock.canBlockStay(worldIn, blockpos, state)) {
          setBlockAndNotifyAdequately(worldIn, blockpos, state);
        }
      }
    } else if (chance == 1) {
      BlockCaveMushroom mushroomBlock = (BlockCaveMushroom) BlocksPlant.PLANT.get(GLOWSHROOM);
      IBlockState state = mushroomBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, pos) / 16; ++i) {
        BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4),
                                    rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

        if (worldIn.isAirBlock(blockpos) &&
            pos.getY() < WorldTypeClassic.SEALEVEL - 3 &&
            worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 &&
            !worldIn.canSeeSky(blockpos) &&
            mushroomBlock.canBlockStay(worldIn, blockpos, state)) {
          setBlockAndNotifyAdequately(worldIn, blockpos, state);
        }
      }
    } else if (chance == 2) {
      BlockCaveMushroom mushroomBlock = (BlockCaveMushroom) BlocksPlant.PLANT.get(MAGMA_SHROOM);
      IBlockState state = mushroomBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, pos) / 16; ++i) {
        BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4),
                                    rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

        if (worldIn.isAirBlock(blockpos) &&
            pos.getY() < WorldTypeClassic.SEALEVEL - 3 &&
            worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 &&
            !worldIn.canSeeSky(blockpos) &&
            mushroomBlock.canBlockStay(worldIn, blockpos, state)) {
          setBlockAndNotifyAdequately(worldIn, blockpos, state);
        }
      }
    } else if (chance == 3) {
      BlockCaveMushroom mushroomBlock = (BlockCaveMushroom) BlocksPlant.PLANT.get(POISON_SHROOM);
      IBlockState state = mushroomBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, pos) / 16; ++i) {
        BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4),
                                    rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

        if (worldIn.isAirBlock(blockpos) &&
            pos.getY() < WorldTypeClassic.SEALEVEL - 3 &&
            worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 &&
            !worldIn.canSeeSky(blockpos) &&
            mushroomBlock.canBlockStay(worldIn, blockpos, state)) {
          setBlockAndNotifyAdequately(worldIn, blockpos, state);
        }
      }
    } else {
      BlockCaveMushroom mushroomBlock = (BlockCaveMushroom) BlocksPlant.PLANT.get(SULPHUR_SHROOM);
      IBlockState state = mushroomBlock.getDefaultState();

      for (int i = 0; i < ProviderChunkData.getRainfall(worldIn, pos) / 16; ++i) {
        BlockPos blockpos = pos.add(rng.nextInt(4) - rng.nextInt(4),
                                    rng.nextInt(4) - rng.nextInt(4), rng.nextInt(4) - rng.nextInt(4));

        if (worldIn.isAirBlock(blockpos) &&
            pos.getY() < WorldTypeClassic.SEALEVEL - 3 &&
            worldIn.getLightFor(EnumSkyBlock.SKY, blockpos) < 14 &&
            !worldIn.canSeeSky(blockpos) &&
            mushroomBlock.canBlockStay(worldIn, blockpos, state)) {
          setBlockAndNotifyAdequately(worldIn, blockpos, state);
        }
      }
    }
    return true;
  }
}
