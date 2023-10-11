package su.terrafirmagreg;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import su.terrafirmagreg.api.TerraFirmaGregAPI;
import su.terrafirmagreg.api.modules.ModuleContainerRegistryEvent;
import su.terrafirmagreg.tfc.TFCModules;
import su.terrafirmagreg.tfc.proxy.IProxy;

import static su.terrafirmagreg.api.Tags.*;


@SuppressWarnings({"unused", "FieldMayBeFinal"})
@Mod.EventBusSubscriber
@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, dependencies = DEPENDENCIES)
public class TerraFirmaGreg {

    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
    private static IProxy PROXY;

    private ModuleManager moduleManager;

    public TerraFirmaGreg() {
        TerraFirmaGregAPI.instance = this;
    }

    @Mod.EventHandler
    public void onConstruction(FMLConstructionEvent event) {
        this.moduleManager = ModuleManager.getInstance();
        TerraFirmaGregAPI.moduleManager = moduleManager;
        moduleManager.registerContainer(new TFCModules());

        MinecraftForge.EVENT_BUS.post(new ModuleContainerRegistryEvent());

        moduleManager.setup(event.getASMHarvestedData(), Loader.instance().getConfigDir());
        moduleManager.onConstruction(event);
    }

    @Mod.EventHandler
    public void onPreInitializationEvent(FMLPreInitializationEvent event) {
        this.moduleManager.onPreInit(event);
    }

    @Mod.EventHandler
    public void onInitializationEvent(FMLInitializationEvent event) {
        this.moduleManager.onInit(event);
    }

    @Mod.EventHandler
    public void onPostInitializationEvent(FMLPostInitializationEvent event) {
        this.moduleManager.onPostInit(event);
    }

    @Mod.EventHandler
    public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {
        this.moduleManager.onLoadComplete(event);
    }

    @Mod.EventHandler
    public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {
        this.moduleManager.onServerAboutToStart(event);
    }

    @Mod.EventHandler
    public void onServerStartingEvent(FMLServerStartingEvent event) {
        this.moduleManager.onServerStarting(event);
    }

    @Mod.EventHandler
    public void onServerStartedEvent(FMLServerStartedEvent event) {
        this.moduleManager.onServerStarted(event);
    }

    @Mod.EventHandler
    public void onServerStoppingEvent(FMLServerStoppingEvent event) {
        this.moduleManager.onServerStopping(event);
    }

    @Mod.EventHandler
    public void onServerStoppedEvent(FMLServerStoppedEvent event) {
        this.moduleManager.onServerStopped(event);
    }
}
