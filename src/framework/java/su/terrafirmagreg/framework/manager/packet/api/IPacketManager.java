package su.terrafirmagreg.framework.manager.packet.api;

import su.terrafirmagreg.framework.manager.packet.PacketMap;
import su.terrafirmagreg.framework.manager.packet.PacketMap.PacketWrapper;
import su.terrafirmagreg.framework.module.api.IModule;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface IPacketManager {

  Map<IModule, IPacketManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  Map<Class<? extends IPacket>, PacketWrapper> ALL_PACKET_MAP = new Object2ObjectOpenHashMap<>();

  IModule getModule();

  PacketMap getMap();

  IPacketRegistrar getRegistrar();

  IPacketService getService();

}
