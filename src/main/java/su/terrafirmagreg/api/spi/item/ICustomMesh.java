package su.terrafirmagreg.api.spi.item;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This interface is used by registry to automatically detect an item that has custom mesh definitions. This can be used to register your own model
 * stuff.
 */
public interface ICustomMesh {

    /**
     * Gets a custom ItemMeshDefinition for the item. Keep in mind that the models still have to be baked.
     */
    @SideOnly(Side.CLIENT)
    ItemMeshDefinition getCustomMesh();
}
