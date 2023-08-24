package net.dries007.tfc.compat.dynamictrees;

import com.ferreusveritas.dynamictrees.api.ICustomRootDecay;
import com.ferreusveritas.dynamictrees.trees.Species;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.dries007.tfc.api.types.soil.type.SoilTypes.LOAM;
import static net.dries007.tfc.api.types.soil.variant.SoilBlockVariants.DIRT;

public class TFCRootDecay implements ICustomRootDecay {
	public static TFCRootDecay INSTANCE = new TFCRootDecay();

	@Override
	public boolean doDecay(World world, BlockPos pos, IBlockState state, Species species) {
		ChunkDataTFC chunkData = world.getChunk(pos).getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);
		if (chunkData != null) {
			world.setBlockState(pos, TFCBlocks.getSoilBlock(DIRT, chunkData.getSoilHeight(pos)).getDefaultState(), 3);
			return true;
		} else if (world.getWorldType() == TerraFirmaCraft.WORLD_TYPE_TFC) { //failed to get chunkdata, but tfc worldtype still, apply a default rocktype
			world.setBlockState(pos, TFCBlocks.getSoilBlock(DIRT, LOAM).getDefaultState(), 3);
			return true;
		}
		return false;
	}
}
