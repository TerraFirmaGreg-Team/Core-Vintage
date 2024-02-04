package su.terrafirmagreg;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;

import su.terrafirmagreg.api.module.ModuleManager;
import su.terrafirmagreg.proxy.IProxy;

import static net.minecraftforge.fml.common.Mod.EventHandler;
import static su.terrafirmagreg.Tags.*;

@Mod(modid = MOD_ID, version = VERSION, name = MOD_NAME, dependencies = DEPENDENCIES)
public class TerraFirmaGreg {

    @SuppressWarnings("unused")
    @Mod.Instance
    private static TerraFirmaGreg INSTANCE;

    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy PROXY;

    // Hold this so that we can reference non-interface methods without
    // letting the GregTechAPI object see them as immediately.
    private final ModuleManager moduleManager;

    public TerraFirmaGreg() {

        this.moduleManager = new ModuleManager(MOD_ID);
    }

    @EventHandler
    public void onConstruction(FMLConstructionEvent event) {

        this.moduleManager.setup(event.getASMHarvestedData());
        this.moduleManager.onConstructionEvent();
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
}
