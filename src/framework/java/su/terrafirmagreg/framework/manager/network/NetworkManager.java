package su.terrafirmagreg.framework.manager.network;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.network.api.INetworkManager;
import su.terrafirmagreg.framework.manager.network.api.INetworkRegistrar;
import su.terrafirmagreg.framework.manager.network.api.INetworkService;
import su.terrafirmagreg.framework.manager.network.spi.NetworkThreadedWrapper;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.common.MinecraftForge;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;

@Getter
public class NetworkManager implements INetworkManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(NetworkManager.class);
  public static final Map<IModule, INetworkManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  public static final NetworkMap ALL_NETWORK_MAP = NetworkMap.of();


  private final IModule module;

  private final NetworkMap map;
  private final INetworkRegistrar registrar;
  private final INetworkService service;


  private NetworkManager(IModule module) {

    this.module = module;
    this.map = NetworkMap.of();

    this.registrar = new NetworkRegistrar(this);
    this.service = new NetworkService(this);

    MinecraftForge.EVENT_BUS.register(this.service);
  }

  public static synchronized INetworkManager of(IModule module) {

    return MANAGER_MAP.computeIfAbsent(module, NetworkManager::new);
  }

  public static NetworkThreadedWrapper getChannel(INetworkPacket packet) {
    var packetClass = packet.getClass();
    var wrapper = NetworkManager.ALL_NETWORK_MAP.get(packetClass);
    if (wrapper == null) {
      throw new RuntimeException("Trying to send unregistered network packet: " + packetClass.getCanonicalName());
    }
    return wrapper.getChannel();
  }


}
