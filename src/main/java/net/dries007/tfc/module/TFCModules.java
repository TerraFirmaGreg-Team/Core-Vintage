package net.dries007.tfc.module;

import static net.dries007.tfc.Tags.MOD_ID;

public class TFCModules {

    public static final String MODULE_CORE = "core";
    public static final String MODULE_WOOD = "wood";
    public static final String MODULE_INTEGRATION = "integration";

    // Integration modules
    public static final String MODULE_JEI = "jei_integration";
    public static final String MODULE_TOP = "top_integration";
    public static final String MODULE_BAUBLES = "baubles_integration";

    public String getID() {
        return MOD_ID;
    }
}
