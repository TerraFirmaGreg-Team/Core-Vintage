package su.terrafirmagreg.modules.soil;


import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.soil.api.types.type.SoilTypeHandler;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariantHandler;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariantHandler;
import su.terrafirmagreg.modules.soil.data.BlocksSoil;
import su.terrafirmagreg.modules.soil.data.ItemsSoil;

import java.util.Collections;
import java.util.List;

@Module(moduleID = "Soil", name = "TFG Module Soil")
public class ModuleSoil extends ModuleBase {

	public static final Logger LOGGER = LogManager.getLogger("Module Soil");

	public static final CreativeTabs SOIL_TAB = new CreativeTabBase("soil", "soil/grass/humus", false);


	public ModuleSoil() {
		super(3);
		this.enableAutoRegistry(SOIL_TAB);
	}

	@Override
	public void onRegister() {
		SoilTypeHandler.init();
		SoilBlockVariantHandler.init();
		SoilItemVariantHandler.init();

		BlocksSoil.onRegister(registryManager);
		ItemsSoil.onRegister(registryManager);
	}

	@Override
	public @NotNull Logger getLogger() {
		return LOGGER;
	}

	@NotNull
	@Override
	public List<Class<?>> getEventBusSubscribers() {
		return Collections.singletonList(ModuleSoil.class);
	}
}
