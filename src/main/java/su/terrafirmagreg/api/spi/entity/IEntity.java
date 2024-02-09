package su.terrafirmagreg.api.spi.entity;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This interface is used by blocks to automatically register a Entity for a block.
 */
public interface IEntity {

    /**
     * Gets the class of the tile entity to associate with the block.
     *
     * @return The TileEntity class to register associated to this block.
     */
    Class<? extends Entity> getEntityClass();

    /**
     * Gets the EntityRenderer for the entity. If null is returned nothing will be done.
     *
     * @return The EntityRenderer to bind to the entity.
     */
    @SideOnly(Side.CLIENT)
    default IRenderFactory<? super Entity> getEntityRenderer() {

        return null;
    }
}
