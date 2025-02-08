package su.terrafirmagreg.modules;

import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.api.IModuleContainer;
import su.terrafirmagreg.framework.module.api.ModuleContainer;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.device.ModuleDevice;
import su.terrafirmagreg.modules.integration.ModuleIntegration;
import su.terrafirmagreg.modules.integration.gregtech.SubModuleGregTech;
import su.terrafirmagreg.modules.rock.ModuleRock;

import java.util.HashMap;
import java.util.Map;

import static su.terrafirmagreg.Tags.MOD_ID;

@ModuleContainer(
  containerID = MOD_ID
)
public class ModulesContainer implements IModuleContainer {

  public static final String CORE = "core";
  public static final String ROCK = "rock";
  public static final String SOIL = "soil";
  public static final String WOOD = "wood";
  public static final String METAL = "metal";
  public static final String ANIMAL = "animal";
  public static final String FLORA = "flora";
  public static final String DEVICE = "device";
  public static final String AGRICULTURE = "agriculture";
  public static final String FOOD = "food";
  public static final String WORLD = "world";

  public static final String INTEGRATION = "integration";

  public ModulesContainer() {
    addModule(ModuleCore.class);
    addModule(ModuleRock.class);
    addModule(ModuleDevice.class);
    addModule(ModuleAnimal.class);
    addModule(ModuleIntegration.class);
    addModule(SubModuleGregTech.class);
  }


  @Override
  public Map<String, Class<? extends IModule>> getModules() {
    return new HashMap<>();
  }

  @Override
  public String getID() {
    return MOD_ID;
  }
}
