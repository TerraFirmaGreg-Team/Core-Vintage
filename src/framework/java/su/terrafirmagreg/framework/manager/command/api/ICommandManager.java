package su.terrafirmagreg.framework.manager.command.api;

import su.terrafirmagreg.framework.manager.api.IBaseManager;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface ICommandManager extends IBaseManager<ICommandEntry> {

  Map<IModuleEntry, ICommandManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  ICommandRegistrar getRegistrar();


}
