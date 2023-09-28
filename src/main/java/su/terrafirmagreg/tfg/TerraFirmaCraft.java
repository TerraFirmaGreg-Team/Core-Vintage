package su.terrafirmagreg.tfg;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.module.ModuleManager;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import su.terrafirmagreg.tfg.modules.core.ModuleCore;
import su.terrafirmagreg.tfg.proxy.CommonProxy;

import java.util.HashSet;
import java.util.Set;

import static su.terrafirmagreg.tfg.Tags.*;

@SuppressWarnings({"unused", "FieldMayBeFinal"})
@Mod.EventBusSubscriber
@Mod(modid = MOD_ID, name = MOD_NAME, version = VERSION, useMetadata = true, dependencies = DEPENDENCIES)
public final class TerraFirmaCraft {

    @SuppressWarnings("unused")
    @Mod.Instance
    public static TerraFirmaCraft INSTANCE;
    @SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_SIDE)
    public static CommonProxy PROXY;

    private final ModuleManager moduleManager;
    private Set<Class<? extends ModuleBase>> registeredModules = new HashSet<>();

    public TerraFirmaCraft() {
        INSTANCE = this;
        FluidRegistry.enableUniversalBucket();
        this.moduleManager = new ModuleManager(MOD_ID);
    }


    @Mod.EventHandler
    public void onConstructionEvent(FMLConstructionEvent event) {
        ModuleCore.LOGGER.info("Started Construction Phase!");
        this.moduleManager.registerModules(
                ModuleCore.class
        );
        this.moduleManager.onConstructionEvent();
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCore.LOGGER.info("Finished Construction Phase!");
    }

    private void registerModule(Class<? extends ModuleBase> moduleClass) {

        this.moduleManager.registerModules(moduleClass);
        this.registeredModules.add(moduleClass);
    }

    public boolean isModuleEnabled(Class<? extends ModuleBase> moduleClass) {
        return this.registeredModules.contains(moduleClass);
    }

    @Mod.EventHandler
    public void onPreInitializationEvent(FMLPreInitializationEvent event) {
        ModuleCore.LOGGER.info("Started PreInitialization Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCore.LOGGER.info("Finished PreInitialization Phase!");
    }

    @Mod.EventHandler
    public void onInitializationEvent(FMLInitializationEvent event) {
        ModuleCore.LOGGER.info("Started Initialization Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCore.LOGGER.info("Finished Initialization Phase!");
    }

    @Mod.EventHandler
    public void onPostInitializationEvent(FMLPostInitializationEvent event) {
        ModuleCore.LOGGER.info("Started PostInitialization Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCore.LOGGER.info("Finished PostInitialization Phase!");
    }

    @Mod.EventHandler
    public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {
        ModuleCore.LOGGER.info("Started LoadComplete Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCore.LOGGER.info("Finished LoadComplete Phase!");
    }

    @Mod.EventHandler
    public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {
        ModuleCore.LOGGER.info("Started ServerAboutToStart Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCore.LOGGER.info("Finished ServerAboutToStart Phase!");
    }

    @Mod.EventHandler
    public void onServerStartingEvent(FMLServerStartingEvent event) {
        ModuleCore.LOGGER.info("Started ServerStarting Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCore.LOGGER.info("Finished ServerStarting Phase!");
    }

    @Mod.EventHandler
    public void onServerStartedEvent(FMLServerStartedEvent event) {
        ModuleCore.LOGGER.info("Started ServerStarted Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCore.LOGGER.info("Finished ServerStarted Phase!");
    }

    @Mod.EventHandler
    public void onServerStoppingEvent(FMLServerStoppingEvent event) {
        ModuleCore.LOGGER.info("Started ServerStopping Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCore.LOGGER.info("Finished ServerStopping Phase!");
    }

    @Mod.EventHandler
    public void onServerStoppedEvent(FMLServerStoppedEvent event) {
        ModuleCore.LOGGER.info("Started ServerStopped Phase!");
        this.moduleManager.routeFMLStateEvent(event);
        ModuleCore.LOGGER.info("Finished ServerStopped Phase!");
    }
}
