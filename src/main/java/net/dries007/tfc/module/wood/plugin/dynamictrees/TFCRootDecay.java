package net.dries007.tfc.module.wood.plugin.dynamictrees;

import com.ferreusveritas.dynamictrees.api.ICustomRootDecay;
import com.ferreusveritas.dynamictrees.trees.Species;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.soil.StorageSoil;
import net.dries007.tfc.module.soil.api.types.type.SoilTypes;
import net.dries007.tfc.module.soil.api.types.variant.block.SoilBlockVariants;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Класс TFCRootDecay реализует интерфейс ICustomRootDecay и используется для определения процесса разложения корней.
 */
public class TFCRootDecay implements ICustomRootDecay {
    public static TFCRootDecay INSTANCE = new TFCRootDecay();

    /**
     * Метод doDecay выполняет процесс разложения корней.
     *
     * @param world   мир
     * @param pos     позиция блока
     * @param state   состояние блока
     * @param species вид растения
     * @return true, если процесс разложения выполнен успешно, иначе false
     */
    @Override
    public boolean doDecay(World world, BlockPos pos, IBlockState state, Species species) {
        ChunkDataTFC chunkData = world.getChunk(pos).getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);
        if (chunkData != null) {
            // Установить состояние блока земли на основе типа почвы и высоты почвы в данной позиции
            world.setBlockState(pos, StorageSoil.getSoilBlock(SoilBlockVariants.DIRT, chunkData.getSoilHeight(pos)).getDefaultState(), 3);
            return true;
        } else if (world.getWorldType() == TerraFirmaCraft.WORLD_TYPE_TFC) {
            // Не удалось получить данные чанка, но тип мира tfc все еще применяется, примените тип земли по умолчанию
            world.setBlockState(pos, StorageSoil.getSoilBlock(SoilBlockVariants.DIRT, SoilTypes.LOAM).getDefaultState(), 3);
            return true;
        }
        return false;
    }
}
