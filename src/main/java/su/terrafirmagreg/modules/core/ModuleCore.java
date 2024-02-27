package su.terrafirmagreg.modules.core;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.core.data.BlocksCore;
import su.terrafirmagreg.modules.core.data.ItemsCore;

@Module(moduleID = "Core",
		name = "TFG Module Core",
		description = "Core TFG content. Disabling this disables the entire mod and all its module.")
public class ModuleCore extends ModuleBase {

	public static final Logger LOGGER = LogManager.getLogger("ModuleCore");

	public static final CreativeTabs MISC_TAB = new CreativeTabBase("misc", "wand", false);


	public ModuleCore() {
		super(1);
		this.enableAutoRegistry(MISC_TAB);

		MinecraftForge.EVENT_BUS.register(this);

	}

	@Override
	public void onRegister() {
		BlocksCore.onRegister(registryManager);
		ItemsCore.onRegister(registryManager);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onClientRegister() {
		BlocksCore.onClientRegister(registryManager);
		ItemsCore.onClientRegister(registryManager);
	}

	@Override
	public @NotNull Logger getLogger() {
		return LOGGER;
	}
}
