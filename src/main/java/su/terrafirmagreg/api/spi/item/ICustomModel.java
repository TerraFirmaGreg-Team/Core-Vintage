package su.terrafirmagreg.api.spi.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This interface lets an item register their own models.
 */
public interface ICustomModel {
    
    /**
     * Provides a hook for new models to be baked.
     */
    @SideOnly(Side.CLIENT)
    void registerMeshModels();
}
