package su.tfg.modules.core;


import net.minecraftforge.fml.common.SidedProxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.tfg.Tags;
import su.tfg.api.modules.ITFGModule;
import su.tfg.api.modules.TFGModule;
import su.tfg.modules.TFGModules;
import su.tfg.proxy.IProxy;

import static su.tfg.Tags.*;

@TFGModule(
        moduleID = TFGModules.MODULE_CORE,
        containerID = Tags.MOD_ID,
        name = "TFG Core",
        description = "Core TFG content. Disabling this disables the entire mod and all its addons.",
        coreModule = true)
public class CoreModule implements ITFGModule {

    public static final Logger logger = LogManager.getLogger("GregTech Core");

    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    private static IProxy PROXY;

    public static IProxy getProxy() {
        return PROXY;
    }

    @Override
    public @NotNull Logger getLogger() {
        return logger;
    }
}
