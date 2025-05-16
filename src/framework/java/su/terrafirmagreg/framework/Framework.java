package su.terrafirmagreg.framework;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.ModuleManager;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.api.IModuleManager;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLStateEvent;

public abstract class Framework {

  private final IModuleManager manager;

  protected Framework(String modId, String modName) {

    FluidRegistry.enableUniversalBucket();
    ModUtils.of(modId, modName);

    this.manager = ModuleManager.of(modId);
  }

  protected void routeEvent(FMLStateEvent event) {

    this.manager.getService().routeEvent(event);
  }

  protected <T extends IModule> void addModule(T module) {

    this.manager.getRegistrar().addModule(module);
  }


}
