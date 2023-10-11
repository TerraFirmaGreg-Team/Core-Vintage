package su.terrafirmagreg.tfc;

import su.terrafirmagreg.api.modules.IModuleContainer;

public class TFCModules implements IModuleContainer {

    public static final String CONTAINER = "tfc";


    public static final String MODULE_CORE = "core";
    public static final String MODULE_ROCK = "rock";
    public static final String MODULE_SOIL = "soil";
    public static final String MODULE_WOOD = "wood";
    public static final String MODULE_METAL = "metal";
    public static final String MODULE_FOOD = "food";
    public static final String MODULE_ANIMAL = "animal";
    public static final String MODULE_DEVICE = "device";
    public static final String MODULE_FLORA = "flora";
    public static final String MODULE_AGRICULTURE = "agriculture";


    public static final String MODULE_INTEGRATION = "integration";

    // Integration modules
    public static final String MODULE_JEI = "jei_integration";
    public static final String MODULE_TOP = "top_integration";
    public static final String MODULE_HWYLA = "hwyla_integration";
    public static final String MODULE_BAUBLES = "baubles_integration";

    @Override
    public String getID() {
        return CONTAINER;
    }
}
