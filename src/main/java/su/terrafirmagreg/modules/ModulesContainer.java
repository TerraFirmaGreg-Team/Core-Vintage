package su.terrafirmagreg.modules;

import su.terrafirmagreg.framework.module.api.IModuleContainer;
import su.terrafirmagreg.framework.module.api.ModuleContainer;

import static su.terrafirmagreg.Tags.MOD_ID;

@ModuleContainer
public class ModulesContainer implements IModuleContainer {

  public static final String CORE = "core";
  public static final String METAL = "metal";
  public static final String ROCK = "rock";
  public static final String SOIL = "soil";
  public static final String DEVICE = "device";
  public static final String INTEGRATION = "integration";
  

  @Override
  public String getID() {
    return MOD_ID;
  }
}
