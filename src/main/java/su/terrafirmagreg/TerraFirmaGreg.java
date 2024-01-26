package su.terrafirmagreg;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;

import su.terrafirmagreg.api.modules.ModuleContainerRegistryEvent;
import su.terrafirmagreg.api.modules.ModuleManager;
import su.terrafirmagreg.api.modules.ModuleContainerTFG;

import static net.minecraftforge.fml.common.Mod.EventHandler;
import static su.terrafirmagreg.Tags.*;

@Mod(modid = MOD_ID, version = VERSION, name = MOD_NAME, dependencies = DEPENDENCIES)
public class TerraFirmaGreg {

    private static TerraFirmaGreg INSTANCE;

    // Hold this so that we can reference non-interface methods without
    // letting the GregTechAPI object see them as immediately.
    private ModuleManager moduleManager;

    public TerraFirmaGreg() {
        INSTANCE = this;
    }

    @EventHandler
    public void onConstruction(FMLConstructionEvent event) {
        this.moduleManager = new ModuleManager();
        this.moduleManager.registerContainer(new ModuleContainerTFG());
        MinecraftForge.EVENT_BUS.post(new ModuleContainerRegistryEvent());
        this.moduleManager.setup(event.getASMHarvestedData(), Loader.instance().getConfigDir());
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
        this.moduleManager.processIMC(FMLInterModComms.fetchRuntimeMessages(INSTANCE));
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    public void respondIMC(FMLInterModComms.IMCEvent event) {
        this.moduleManager.processIMC(event.getMessages());
    }
}
