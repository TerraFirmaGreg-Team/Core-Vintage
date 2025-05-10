package su.terrafirmagreg.framework;

import su.terrafirmagreg.api.base.client.gui.GuiHandler;
import su.terrafirmagreg.api.util.AnnotationUtils;
import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.ModuleManager;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.api.IModuleManager;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;

public abstract class Framework {

  private final IModuleManager manager;

  protected Framework(String modId, String modName) {

    ModUtils.of(modId, modName);
    GuiHandler.of(modId);
    FluidRegistry.enableUniversalBucket();

    this.manager = ModuleManager.of(modId);
  }

  protected void routeEvent(FMLStateEvent event) {

    this.manager.getService().routeEvent(event);
  }

  protected <T extends IModule> void addModule(T module) {

    this.manager.getRegistrar().addModule(module);
  }


  protected void configure(FMLConstructionEvent event) {
    AnnotationUtils.of(event);
    DataFixUtils.of();
  }


}
