package su.terrafirmagreg.modules.core;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.SidedProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.Tags;
import su.terrafirmagreg.api.modules.ITFGModule;
import su.terrafirmagreg.api.modules.Registry;
import su.terrafirmagreg.api.modules.TFGModule;
import su.terrafirmagreg.api.objects.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.TFGModules;
import su.terrafirmagreg.modules.core.init.CoreBlocks;
import su.terrafirmagreg.modules.core.init.CoreItems;
import su.terrafirmagreg.proxy.IProxy;

import static su.terrafirmagreg.Tags.*;

@TFGModule(
		moduleID = TFGModules.MODULE_CORE,
		containerID = Tags.MOD_ID,
		name = "TFG Core",
		description = "Core TFG content. Disabling this disables the entire mod and all its addons.",
		coreModule = true)
public class CoreModule implements ITFGModule {

	public static final Logger LOGGER = LogManager.getLogger("TFG Core");

	public static final CreativeTabs MISC_TAB = new CreativeTabBase("misc", "wand", false);

	@SidedProxy(modId = MOD_ID, clientSide = CLIENT_PROXY, serverSide = SERVER_PROXY)
	public static IProxy PROXY;

	private final Registry registry;

	public CoreModule() {
		this.registry = new Registry(MISC_TAB);
	}

	@Override
	public void onRegister() {
		CoreBlocks.onRegister(registry);
		CoreItems.onRegister(registry);
	}

	@Override
	public @NotNull Logger getLogger() {
		return LOGGER;
	}
}
