package su.terrafirmagreg.framework.manager.network;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.framework.manager.network.NetworkMap.NetworkWrapper;
import su.terrafirmagreg.framework.manager.network.spi.NetworkThreadedWrapper;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

public class NetworkMap extends Object2ObjectOpenHashMap<Class<? extends INetworkPacket>, NetworkWrapper> {


  public static NetworkMap of() {
    return new NetworkMap();
  }

  @Override
  public NetworkWrapper put(Class<? extends INetworkPacket> packetClass, NetworkWrapper wrapper) {

    NetworkManager.ALL_NETWORK_MAP.put(packetClass, wrapper);
    return super.put(packetClass, wrapper);
  }


  @Data(staticConstructor = "of")
  public static class NetworkWrapper {

    private final NetworkThreadedWrapper channel;
    private final String className;

  }
}
