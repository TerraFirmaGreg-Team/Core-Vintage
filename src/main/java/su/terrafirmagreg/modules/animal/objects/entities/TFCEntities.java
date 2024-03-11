package su.terrafirmagreg.modules.animal.objects.entities;

import net.dries007.tfc.util.Helpers;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.DataSerializerEntry;
import su.terrafirmagreg.Tags;

import javax.annotation.Nonnull;


@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class TFCEntities {
	@GameRegistry.ObjectHolder("tfc:long")
	public static final DataSerializerEntry LONG_DATA_SERIALIZER_ENTRY = Helpers.getNull();

	private static final DataSerializer<Long> LONG_DATA_SERIALIZER = new DataSerializer<Long>() {
		public void write(PacketBuffer buf, @Nonnull Long value) {
			buf.writeLong(value);
		}

		public Long read(PacketBuffer buf) {
			return buf.readLong();
		}

		public DataParameter<Long> createKey(int id) {
			return new DataParameter<>(id, this);
		}

		@Nonnull
		public Long copyValue(@Nonnull Long value) {
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
