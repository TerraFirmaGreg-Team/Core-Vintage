package su.terrafirmagreg.modules.animal.objects.entities;

import su.terrafirmagreg.api.lib.Constants;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.DataSerializerEntry;


import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID)
public class TFCEntities {

    @GameRegistry.ObjectHolder("tfc:long")
    public static final DataSerializerEntry LONG_DATA_SERIALIZER_ENTRY = ModUtils.getNull();

    private static final DataSerializer<Long> LONG_DATA_SERIALIZER = new DataSerializer<Long>() {

        public void write(PacketBuffer buf, @NotNull Long value) {
            buf.writeLong(value);
        }

        public @NotNull Long read(PacketBuffer buf) {
            return buf.readLong();
        }

        public @NotNull DataParameter<Long> createKey(int id) {
            return new DataParameter<>(id, this);
        }

        @NotNull
        public Long copyValue(@NotNull Long value) {
            return value;
        }
    };

    @SuppressWarnings("unchecked")
    public static DataSerializer<Long> getLongDataSerializer() {
        return (DataSerializer<Long>) LONG_DATA_SERIALIZER_ENTRY.getSerializer();
    }

    @SubscribeEvent
    public static void registerDataSerializers(RegistryEvent.Register<DataSerializerEntry> event) {
        event.getRegistry().register(new DataSerializerEntry(LONG_DATA_SERIALIZER).setRegistryName("long"));
    }
}
