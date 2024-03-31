package su.terrafirmagreg.api.util;

import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.Nullable;

public class TileUtils {

	private TileUtils() {
		throw new IllegalAccessError("Utility class");
	}


	/**
	 * Возвращает экземпляр типизированного объекта TileEntity по его классу.
	 *
	 * @param world  игровой мир
	 * @param pos    позиция блока
	 * @param aClass класс типизированного объекта TileEntity
	 * @param <T>    тип типизированного объекта TileEntity
	 * @return экземпляр типизированного объекта TileEntity
	 */
	@SuppressWarnings("unchecked")
	public static <T extends TileEntity> T getTile(IBlockAccess world, BlockPos pos, Class<T> aClass) {
		TileEntity te = world.getTileEntity(pos);
		if (!aClass.isInstance(te)) return null;
		return (T) te;
	}


	/**
	 * Returns the tile at the specified position, returns null if it is the wrong type or does not exist.
	 * Avoids creating new tile entities when using a ChunkCache (off the main thread).
	 * see {@link BlockFlowerPot#getActualState(IBlockState, IBlockAccess, BlockPos)}
	 */
	@Nullable
	public static TileEntity getTile(IBlockAccess world, BlockPos pos) {
		if (world instanceof ChunkCache chunkCache) {
			return chunkCache.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		} else {
			return world.getTileEntity(pos);
		}
	}


	public static boolean isUsableByPlayer(EntityPlayer player, TileEntity tile) {
		BlockPos pos = tile.getPos();
		World world = tile.getWorld();

		return !tile.isInvalid() &&
				getTile(world, pos) == tile &&
				player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

}
