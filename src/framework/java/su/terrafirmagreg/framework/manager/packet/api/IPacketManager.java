package su.terrafirmagreg.framework.manager.packet.api;

import su.terrafirmagreg.framework.manager.api.IBaseManager;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface IPacketManager extends IBaseManager<IPacketEntry> {

  Map<IModuleEntry, IPacketManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  IPacketRegistrar getRegistrar();


}
