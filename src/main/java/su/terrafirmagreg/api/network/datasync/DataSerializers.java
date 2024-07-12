package su.terrafirmagreg.api.network.datasync;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;

public class DataSerializers extends net.minecraft.network.datasync.DataSerializers {

    public static final DataSerializer<Long> LONG = new DataSerializer<>() {

        public void write(PacketBuffer buf, Long value) {
            buf.writeLong(value);
        }

        public Long read(PacketBuffer buf) {
            return buf.readLong();
        }

        public DataParameter<Long> createKey(int id) {
            return new DataParameter<>(id, this);
        }
        
        public Long copyValue(Long value) {
            return value;
        }
    };
}
