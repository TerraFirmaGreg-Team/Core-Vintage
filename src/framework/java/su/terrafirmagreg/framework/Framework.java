package su.terrafirmagreg.framework;

import su.terrafirmagreg.api.base.client.gui.GuiHandler;
import su.terrafirmagreg.framework.module.ModuleManager;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.api.IModuleManager;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLStateEvent;

import lombok.Getter;


@Getter
public abstract class Framework {

  public static String modId;
  public static String modName;
  public static ASMDataTable asmData;

  private final IModuleManager manager;


  protected Framework(String modId, String modName) {
    Framework.modId = modId != null ? modId : "UnknownModId";
    Framework.modName = modName != null ? modName : "UnknownModName";

    this.manager = ModuleManager.of(modId);
  }

  protected void setup(FMLConstructionEvent event) {
    Framework.asmData = event.getASMHarvestedData();
    FluidRegistry.enableUniversalBucket();
    GuiHandler.enableGui();
  }

  protected void routeEvent(FMLStateEvent event) {

    this.manager.getService().routeEvent(event);
  }

  protected <T extends IModule> void addModule(T module) {

    this.manager.getRegistrar().addModule(module);
  }


}
