package su.terrafirmagreg.framework.manager.network.api;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.framework.manager.network.NetworkMap;
import su.terrafirmagreg.framework.manager.network.NetworkMap.NetworkWrapper;
import su.terrafirmagreg.framework.module.api.IModule;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface INetworkManager {

  Map<IModule, INetworkManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();
  Map<Class<? extends INetworkPacket>, NetworkWrapper> ALL_NETWORK_MAP = new Object2ObjectOpenHashMap<>();

  IModule getModule();

  NetworkMap getMap();

  INetworkRegistrar getRegistrar();

  INetworkService getService();

}
