package su.terrafirmagreg;

import su.terrafirmagreg.api.module.ModuleManager;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraftforge.fml.common.Mod.EventHandler;
import static su.terrafirmagreg.Tags.*;

@Mod(modid = MOD_ID, version = VERSION, name = MOD_NAME, dependencies = DEPENDENCIES)
public class TerraFirmaGreg {

    public static final Logger LOGGER = LogManager.getLogger("TFG");

    @Getter
    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    public static IProxy proxy;

    @SuppressWarnings("unused")
    @Mod.Instance
    @Getter
    private static TerraFirmaGreg instance;

    private final ModuleManager moduleManager;

    public TerraFirmaGreg() {
        this.moduleManager = new ModuleManager(MOD_ID);
    }

    @EventHandler
    public void onConstruction(FMLConstructionEvent event) {
        this.moduleManager.setup(event.getASMHarvestedData());
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
