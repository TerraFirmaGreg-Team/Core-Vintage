package su.terrafirmagreg.old.api.util;

import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.GameRegistry;

import lombok.experimental.UtilityClass;

import java.util.Optional;


@UtilityClass
@SuppressWarnings("unused")
public final class TileUtils {

  /**
   * Возвращает экземпляр типизированного объекта TileEntity по его классу.
   *
   * @param world  игровой мир
   * @param pos    позиция блока
   * @param aClass класс типизированного объекта TileEntity
   * @param <T>    тип типизированного объекта TileEntity
   * @return экземпляр типизированного объекта TileEntity
   */
  public static <T extends TileEntity> Optional<T> getTile(IBlockAccess world, BlockPos pos, Class<T> aClass) {
    return getTile(world, pos).filter(aClass::isInstance).map(aClass::cast);
  }

  /**
   * Returns the tile at the specified position, returns null if it is the wrong type or does not exist. Avoids creating new tile entities when using a
   * ChunkCache (off the main thread). see {@link BlockFlowerPot#getActualState(IBlockState, IBlockAccess, BlockPos)}
   */
  public static Optional<TileEntity> getTile(IBlockAccess world, BlockPos pos) {
    return world instanceof ChunkCache chunkCache
           ? Optional.ofNullable(chunkCache.getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK))
           : Optional.ofNullable(world.getTileEntity(pos));
  }

  public static boolean isUsableByPlayer(EntityPlayer player, TileEntity tile) {
    BlockPos pos = tile.getPos();
    World world = tile.getWorld();

    return !tile.isInvalid()
           && getTile(world, pos).map(t -> t == tile).orElse(false)
           && player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
  }


  public static void registerTileEntity(Class<? extends TileEntity> tileClass, String name) {
    GameRegistry.registerTileEntity(tileClass, ModUtils.resource("tile." + name));
  }

}
