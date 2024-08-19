package su.terrafirmagreg.api.registry.spi;

import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.DataSerializerEntry;

public interface IDataSerializerRegistry
        extends IBaseRegistry {

    /**
     * Registers a new dataSerializer. Registration will be handled for you.
     *
     * @param serializer The command to add.
     */
    default DataSerializerEntry dataSerializerEntry(DataSerializer<?> serializer, String name) {

        var dataSerializerEntry = new DataSerializerEntry(serializer);
        dataSerializerEntry.setRegistryName(new ResourceLocation(this.getModID(), name));

        this.getRegistry().getDataSerializerEntries().add(dataSerializerEntry);

        return dataSerializerEntry;
    }
}
