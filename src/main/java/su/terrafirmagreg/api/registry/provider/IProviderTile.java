package su.terrafirmagreg.api.registry.provider;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

/**
 * This interface is used by blocks to automatically register a TileEntity for a block.
 */
public interface IProviderTile
        extends ITileEntityProvider {

  /**
   * Gets the class of the tile entity to associate with the block.
   *
   * @return The TileEntity class to register associated to this block.
   */
  Class<? extends TileEntity> getTileEntityClass();

  /**
   * Gets the TESR for the tile entity. If null is returned nothing will be done.
   *
   * @return The TESR to bind to the tile.
   */
  @SideOnly(Side.CLIENT)
  default @Nullable TileEntitySpecialRenderer<?> getTileRenderer() {
    return null;
  }

  default boolean hasTileEntity(IBlockState state) {
    return true;
  }
}
