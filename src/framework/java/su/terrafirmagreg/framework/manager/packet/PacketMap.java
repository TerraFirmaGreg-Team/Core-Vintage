package su.terrafirmagreg.framework.manager.packet;

import su.terrafirmagreg.framework.manager.packet.api.IPacketEntry;
import su.terrafirmagreg.framework.manager.packet.spi.NetworkThreadedWrapper;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

public class PacketMap extends Object2ObjectOpenHashMap<Class<? extends IPacketEntry>, IPacketEntry> {


  public static PacketMap of() {
    return new PacketMap();
  }


  @Data(staticConstructor = "of")
  public static class PacketWrapper {

    private final NetworkThreadedWrapper channel;
    private final IPacketEntry entry;

  }
}
