package su.terrafirmagreg.framework.module.api;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLStateEvent;

public interface IModuleManager {

  default boolean isModuleEnabled(String containerID, String moduleID) {
    return isModuleEnabled(ModUtils.resource(containerID, moduleID));
  }

  boolean isModuleEnabled(ResourceLocation id);

  boolean isModuleEnabled(IModule module);

  default boolean isModuleEnabled(String moduleID) {
    return isModuleEnabled(ModUtils.resource(moduleID));
  }

  void routeEvent(FMLStateEvent event);

  void registerContainer(IModuleContainer container);
}
