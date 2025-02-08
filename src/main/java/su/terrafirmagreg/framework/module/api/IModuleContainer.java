package su.terrafirmagreg.framework.module.api;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;

import java.util.Map;

public interface IModuleContainer {

  Map<String, Class<? extends IModule>> getModules();

  /**
   * The ID of this container. If this is your mod's only container, you should use your mod ID to prevent collisions.
   */
  String getID();


  default ResourceLocation uids(String path) {

    return ModUtils.resource(this.getID(), path);
  }


  default <T extends IModule> void addModule(Class<T> module) {

    this.getModules().put(getID(), module);
  }

}
