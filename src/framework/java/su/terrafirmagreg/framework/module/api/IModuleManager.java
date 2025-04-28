package su.terrafirmagreg.framework.module.api;

import su.terrafirmagreg.framework.module.ModuleMap;

import net.minecraftforge.fml.common.event.FMLStateEvent;

public interface IModuleManager {

  String getModId();

  ModuleMap getMap();

  <T extends IModule> void addModule(T module);

  // Service

  void routeEvent(FMLStateEvent event);


}
