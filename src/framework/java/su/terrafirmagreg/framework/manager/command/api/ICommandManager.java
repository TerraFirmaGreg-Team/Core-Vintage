package su.terrafirmagreg.framework.manager.command.api;

import su.terrafirmagreg.framework.manager.command.CommandMap;
import su.terrafirmagreg.framework.module.api.IModule;

public interface ICommandManager {

  IModule getModule();

  CommandMap getMap();

  ICommandRegistrar getRegistrar();

  ICommandService getService();

}
