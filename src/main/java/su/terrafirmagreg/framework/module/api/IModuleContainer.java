package su.terrafirmagreg.framework.module.api;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;

public interface IModuleContainer {

  default ResourceLocation uids(String path) {
    return ModUtils.resource(this.getID(), path);
  }

  /**
   * The ID of this container. If this is your mod's only container, you should use your mod ID to prevent collisions.
   */
  String getID();

}
