package su.terrafirmagreg.modules.core;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleTFG;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.core.data.BlocksCore;
import su.terrafirmagreg.modules.core.data.ItemsCore;

@ModuleTFG(moduleID = "Core", name = "TFG Module Core",
		description = "Core TFG content. Disabling this disables the entire mod and all its module.")
public class ModuleCore extends ModuleBase {

	public static final Logger LOGGER = LogManager.getLogger(ModuleCore.class.getSimpleName());

	public static final CreativeTabs CORE_TAB = new CreativeTabBase("misc", "core/wand");


	public ModuleCore() {
		super(1);
		this.enableAutoRegistry(CORE_TAB);

	}

	@Override
	public void onRegister() {
		BlocksCore.onRegister(registryManager);
		ItemsCore.onRegister(registryManager);
	}

	@Override
	public void onPreInit(FMLPreInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(TerraFirmaGreg.getInstance(), new GuiHandler());
	}

	@Override
	public @NotNull Logger getLogger() {
		return LOGGER;
	}
}
