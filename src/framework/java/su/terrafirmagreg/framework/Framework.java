package su.terrafirmagreg.framework;

import su.terrafirmagreg.api.base.client.gui.GuiHandler;
import su.terrafirmagreg.api.util.AnnotationUtils;
import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.framework.module.ModuleManager;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.api.IModuleManager;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;

public abstract class Framework {

  protected final String modId;
  protected final IModuleManager manager;

  protected Framework(String modId) {
    this.modId = modId;

    this.manager = ModuleManager.of(modId);
  }

  protected void routeEvent(FMLStateEvent event) {

    this.manager.routeEvent(event);
  }

  protected <T extends IModule> void addModule(T module) {

    this.manager.addModule(module);
  }


  protected void configure(FMLConstructionEvent event) {
    AnnotationUtils.of(event);
    FluidRegistry.enableUniversalBucket();
    GuiHandler.of(modId);
    DataFixUtils.of();
  }


}
