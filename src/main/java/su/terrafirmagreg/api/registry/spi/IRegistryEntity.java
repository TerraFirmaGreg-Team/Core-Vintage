package su.terrafirmagreg.api.registry.spi;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

public interface IRegistryEntity
        extends IRegistryBase {

    /**
     * Registers any sort of entity. Will not have a spawn egg.
     *
     * @param entClass The entity class.
     * @param name     The string name for the entity.
     * @return The entity that was registered.
     */
    default <T extends Entity> EntityEntry entity(String name, Class<T> entClass) {

        final EntityEntryBuilder<T> builder = EntityEntryBuilder.create();
        builder.entity(entClass);

        return entity(name, builder);
    }

    /**
     * Registers any sort of entity. Will have a spawn egg.
     *
     * @param entClass The entity class.
     * @param name     The string name for the entity.
     * @return The entity that was registered.
     */
    default <T extends Entity> EntityEntry entity(String name, Class<T> entClass, int primary, int seconday) {

        final EntityEntryBuilder<T> builder = EntityEntryBuilder.create();
        builder.entity(entClass);
        builder.tracker(64, 1, true);
        builder.egg(primary, seconday);

        return entity(name, builder);
    }

    default <T extends Entity> EntityEntry entity(String name, EntityEntryBuilder<T> builder) {
        final ResourceLocation entId = new ResourceLocation(this.getModID(), name);

        builder.id(entId, this.getNetworkEntityIdSupplier().getAndIncrement());
        builder.name(this.getModID() + "." + name);

        this.getRegistry().getEntities().add(builder.build());

        return builder.build();
    }
}
