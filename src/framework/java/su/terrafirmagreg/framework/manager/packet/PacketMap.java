package su.terrafirmagreg.framework.manager.packet;

import su.terrafirmagreg.framework.manager.packet.PacketMap.PacketWrapper;
import su.terrafirmagreg.framework.manager.packet.api.IPacket;
import su.terrafirmagreg.framework.manager.packet.spi.NetworkThreadedWrapper;

import net.minecraftforge.fml.relauncher.Side;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

public class PacketMap extends Object2ObjectOpenHashMap<Class<? extends IPacket>, PacketWrapper> {


  public static PacketMap of() {
    return new PacketMap();
  }


  @Data(staticConstructor = "of")
  public static class PacketWrapper {

    private final NetworkThreadedWrapper channel;
    private final Side side;

  }
}
