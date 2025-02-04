package su.terrafirmagreg.modules;

import su.terrafirmagreg.framework.module.api.IModuleContainer;
import su.terrafirmagreg.framework.module.api.ModuleContainer;

import static su.terrafirmagreg.Tags.MOD_ID;

@ModuleContainer
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


  @Override
  public String getID() {
    return MOD_ID;
  }
}
