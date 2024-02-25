package su.terrafirmagreg.api.spi.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This interface is used by blocks to automatically register a TileEntity for a block.
 */
public interface ITEBlock {

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
	default TileEntitySpecialRenderer<?> getTileRenderer() {

		return null;
	}
}
