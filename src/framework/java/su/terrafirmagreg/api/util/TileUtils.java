package su.terrafirmagreg.api.util;

import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
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
   * Returns the tile at the specified position, returns null if it is the wrong type or does not exist. Avoids creating new tile entities when using a ChunkCache (off the main thread). see
   * {@link BlockFlowerPot#getActualState(IBlockState, IBlockAccess, BlockPos)}
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

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static void addTile(Block block) {
    if (block instanceof IBlockEntry provider) {
      var registryName = block.getRegistryName();

      var settings = provider.getSettings();
      var tileClass = settings.getTileClass();
      final TileEntitySpecialRenderer tesr = settings.getTileRenderer();
      if (registryName != null && tileClass != null) {
        TileUtils.addTile(tileClass, registryName.getNamespace(), tileClass.getSimpleName().replaceFirst("Tile", ""));
        ModelUtils.tesr(tileClass, tesr);
      }
    }
  }

  public static void addTile(Class<? extends TileEntity> tileClass, ResourceLocation name) {

    TileUtils.addTile(tileClass, name.getNamespace(), name.getPath());
  }


  public static void addTile(Class<? extends TileEntity> tileClass, String namespace, String name) {

    var registryName = ModUtils.resource(namespace, "tile", name);
    if (!TileEntity.REGISTRY.containsKey(registryName)) {
      GameRegistry.registerTileEntity(tileClass, registryName); // tileClass.getSimpleName().replaceFirst("Tile", "")
    }

  }

}
