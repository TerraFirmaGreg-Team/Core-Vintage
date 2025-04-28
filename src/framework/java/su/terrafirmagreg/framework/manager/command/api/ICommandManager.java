package su.terrafirmagreg.framework.manager.command.api;

import su.terrafirmagreg.framework.manager.command.CommandMap;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public interface ICommandManager {

  IModule getModule();

  CommandMap getMap();

  ICommandRegistrar getRegistrar();

  ICommandService getService();


  void routeEvent(FMLServerStartingEvent event);

}
