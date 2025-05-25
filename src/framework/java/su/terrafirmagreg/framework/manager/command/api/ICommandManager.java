package su.terrafirmagreg.framework.manager.command.api;

import su.terrafirmagreg.framework.manager.command.CommandMap;
import su.terrafirmagreg.framework.module.api.IModule;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface ICommandManager {

  Map<IModule, ICommandManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();

  IModule getModule();

  CommandMap getMap();

  ICommandRegistrar getRegistrar();

  ICommandService getService();

}
