package su.terrafirmagreg.util.module;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import su.terrafirmagreg.util.integration.IntegrationPluginHandlerRegistry;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final String modId;
    private final List<ModuleBase> moduleList;
    private final ModuleRegistry moduleRegistry;
    private final ModuleEventRouter moduleEventRouter;
    private final IntegrationPluginHandlerRegistry integrationPluginHandlerRegistry;

    public ModuleManager(String modId) {

        this.modId = modId;
        this.moduleList = new ArrayList<>();
        this.moduleRegistry = new ModuleRegistry(this.moduleList, new ModuleConstructor());
        this.moduleEventRouter = new ModuleEventRouter(this.moduleList);
        this.integrationPluginHandlerRegistry = new IntegrationPluginHandlerRegistry(modId, this.moduleRegistry);

        MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);

        this.registerIntegrationHandler("jei", "su.terrafirmagreg.util.integration.jei.IntegrationPluginHandler");

        this.registerIntegrationHandler("crafttweaker", "su.terrafirmagreg.util.integration.crafttweaker.IntegrationPluginHandler");

        this.registerIntegrationHandler("gamestages", "su.terrafirmagreg.util.integration.SimplePluginHandler");

        this.registerIntegrationHandler("dropt", "su.terrafirmagreg.util.integration.SimplePluginHandler");
    }

    @SafeVarargs
    public final void registerModules(Class<? extends ModuleBase>... moduleClassArray) {

        this.moduleRegistry.registerModules(moduleClassArray);
    }

    public void registerIntegrationHandler(String modId, String handler) {

        this.integrationPluginHandlerRegistry.registerIntegrationHandler(modId, handler);
    }

    public void onConstructionEvent() {

        // Initialize integration handlers.
        this.integrationPluginHandlerRegistry.initializeIntegrationHandlers();

        // Initialize modules.
        this.moduleRegistry.initializeModules(this.modId);

        // Register integration plugins using loaded handlers.
        for (ModuleBase module : this.moduleList) {
            this.integrationPluginHandlerRegistry.registerIntegrationPlugins(module.getIntegrationPluginMap());
        }
    }

    public void routeFMLStateEvent(FMLStateEvent event) {

        this.moduleEventRouter.routeFMLStateEvent(event);
    }
}
