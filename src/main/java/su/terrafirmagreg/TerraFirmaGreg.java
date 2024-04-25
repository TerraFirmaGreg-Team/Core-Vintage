package su.terrafirmagreg;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleManager;
import su.terrafirmagreg.api.util.AnnotationUtils;
import su.terrafirmagreg.proxy.IProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;


import lombok.Getter;

import static net.minecraftforge.fml.common.Mod.EventHandler;
import static su.terrafirmagreg.api.lib.Constants.*;

@Mod(modid = MOD_ID, version = VERSION, name = MOD_NAME, dependencies = DEPENDENCIES)
public class TerraFirmaGreg {

    public static final LoggingHelper LOGGER = new LoggingHelper();

    @Getter
    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy proxy;

    @Getter
    @Mod.Instance
    @SuppressWarnings("unused")
    private static TerraFirmaGreg instance;

    private final ModuleManager moduleManager;

    public TerraFirmaGreg() {
        this.moduleManager = ModuleManager.getInstance();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onConstruction(FMLConstructionEvent event) {
        AnnotationUtils.setAnnotationData(event.getASMHarvestedData());

        this.moduleManager.setup();
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void preInit(FMLPreInitializationEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void init(FMLInitializationEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void postInit(FMLPostInitializationEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void loadComplete(FMLLoadCompleteEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void serverStarting(FMLServerStartingEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void serverStarted(FMLServerStartedEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void serverStopping(FMLServerStoppingEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void serverStopped(FMLServerStoppedEvent event) {
        this.moduleManager.routeFMLStateEvent(event);
    }
}
