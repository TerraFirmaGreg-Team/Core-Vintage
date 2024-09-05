package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;

import net.minecraftforge.registries.DataSerializerEntry;


import static su.terrafirmagreg.api.network.datasync.DataSerializers.LONG;

public final class DataSerializersCore {

  public static DataSerializerEntry LONG_DATA_SERIALIZER_ENTRY;

  public static void onRegister(RegistryManager registry) {

    LONG_DATA_SERIALIZER_ENTRY = registry.dataSerializerEntry(LONG, "long");

  }
}
