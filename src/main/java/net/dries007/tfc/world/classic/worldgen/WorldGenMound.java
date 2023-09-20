package net.dries007.tfc.world.classic.worldgen;

import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenMound;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.util.SafeChunkBounds;
import com.ferreusveritas.dynamictrees.util.SimpleVoxmap;
import com.ferreusveritas.dynamictrees.worldgen.JoCode;
import net.dries007.tfc.module.soil.common.SoilStorage;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariants.DIRT;

/**
 * Класс WorldGenMound расширяет класс FeatureGenMound и используется для генерации холмов в мире.
 * Он создает круглый холм размером 5x4x5, который находится на один блок выше поверхности земли.
 * Это придает сгенерированным корням на поверхности более реалистичный вид.
 */
public class WorldGenMound extends FeatureGenMound {

    private static final SimpleVoxmap moundMap = new SimpleVoxmap(5, 4, 5, new byte[]{
            0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 2, 2, 2, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0,
            0, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 0,
            0, 1, 1, 1, 0, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 1, 2, 2, 2, 1, 0, 1, 1, 1, 0,
            0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0
    }).setCenter(new BlockPos(2, 3, 2));

    private final int moundCutoffRadius;

    /**
     * Конструктор класса WorldGenMound.
     *
     * @param moundCutoffRadius радиус отсечения холма
     */
    public WorldGenMound(int moundCutoffRadius) {
        super(moundCutoffRadius);
        this.moundCutoffRadius = moundCutoffRadius;
    }

    /**
     * Метод preGeneration используется для создания холма размером 5x4x5, который находится на один блок выше поверхности земли.
     * Это придает сгенерированным корням на поверхности более реалистичный вид.
     *
     * @param world      мир
     * @param rootPos    позиция корневой земли
     * @param species    вид растения
     * @param radius     радиус
     * @param facing     направление
     * @param safeBounds структура безопасных границ для предотвращения каскадной генерации
     * @param joCode     JoCode
     * @return измененная позиция корневой земли, находящаяся на один блок выше
     */
    @Override
    public BlockPos preGeneration(World world, BlockPos rootPos, Species species, int radius, EnumFacing facing, SafeChunkBounds safeBounds, JoCode joCode) {
        if (radius >= moundCutoffRadius && safeBounds != SafeChunkBounds.ANY) {
            // тест генерации мира
            var initialDirtState = world.getBlockState(rootPos);
            var initialUnderState = world.getBlockState(rootPos.down());

            if (initialUnderState.getMaterial() == Material.AIR || (initialUnderState.getMaterial() != Material.GROUND && initialUnderState.getMaterial() != Material.ROCK)) {
                var chunkData = world.getChunk(rootPos).getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);
                initialUnderState = SoilStorage.getSoilBlock(DIRT, chunkData.getSoilHeight(rootPos)).getDefaultState();
            }

            rootPos = rootPos.up();

            for (SimpleVoxmap.Cell cell : moundMap.getAllNonZeroCells()) {
                var placeState = cell.getValue() == 1 ? initialDirtState : initialUnderState;
                world.setBlockState(rootPos.add(cell.getPos()), placeState);
            }
        }

        return rootPos;
    }
}
