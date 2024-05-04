package su.terrafirmagreg.api.spi.item;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This interface is used by the registry system to automatically detect and register item color handlers appropriately, without using proxies or any other complicated spread out
 * code.
 */
public interface IColorfulItem {

    /**
     * Gets the color handler for the item.
     *
     * @return The color handler for the item.
     */
    @SideOnly(Side.CLIENT)
    IItemColor getColorHandler();
}
