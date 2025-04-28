package su.terrafirmagreg.framework.module;

import su.terrafirmagreg.framework.module.ModuleMap.ModuleWrapper;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraft.util.ResourceLocation;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

public class ModuleMap extends Object2ObjectOpenHashMap<Class<? extends IModule>, ModuleWrapper> {


  public static ModuleMap of() {
    return new ModuleMap();
  }

  @Data(staticConstructor = "of")
  public static class ModuleWrapper {

    private final ResourceLocation identifier;
    private final IModule module;


  }
}
