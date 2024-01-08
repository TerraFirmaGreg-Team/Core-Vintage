package su.terrafirmagreg;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import su.terrafirmagreg.api.modules.ModuleContainerRegistryEvent;
import su.terrafirmagreg.api.modules.ModuleManager;
import su.terrafirmagreg.modules.TFGModules;

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
		moduleManager = ModuleManager.getInstance();
//        GregTechAPI.moduleManager = moduleManager;
		moduleManager.registerContainer(new TFGModules());
		MinecraftForge.EVENT_BUS.post(new ModuleContainerRegistryEvent());
		moduleManager.setup(event.getASMHarvestedData(), Loader.instance().getConfigDir());
		moduleManager.onConstruction(event);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		moduleManager.onPreInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		moduleManager.onInit(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		moduleManager.onPostInit(event);
		moduleManager.processIMC(FMLInterModComms.fetchRuntimeMessages(INSTANCE));
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {
		moduleManager.onLoadComplete(event);
	}

	@EventHandler
	public void serverAboutToStart(FMLServerAboutToStartEvent event) {
		moduleManager.onServerAboutToStart(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		moduleManager.onServerStarting(event);
	}

	@EventHandler
	public void serverStarted(FMLServerStartedEvent event) {
		moduleManager.onServerStarted(event);
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		moduleManager.onServerStopping(event);
	}

	@EventHandler
	public void serverStopped(FMLServerStoppedEvent event) {
		moduleManager.onServerStopped(event);
	}

	@EventHandler
	public void respondIMC(FMLInterModComms.IMCEvent event) {
		moduleManager.processIMC(event.getMessages());
	}
}
