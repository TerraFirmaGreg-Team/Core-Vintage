package net.dries007.tfc.module.core;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleCore extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleCore.class.getSimpleName());

    public ModuleCore() {
        super(0, MOD_ID);

    }
}
