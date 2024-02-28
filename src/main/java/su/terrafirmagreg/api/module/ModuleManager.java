package su.terrafirmagreg.api.module;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLStateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModuleManager {

	public static final Logger LOGGER = LogManager.getLogger("Module Manager");

	private final String modId;
	private final List<ModuleBase> moduleList;
	private final ModuleRegistry moduleRegistry;
	private final ModuleEventRouter moduleEventRouter;

	public ModuleManager(String modId) {

		this.modId = modId;
		this.moduleList = new ArrayList<>();
		this.moduleRegistry = new ModuleRegistry(this.moduleList, new ModuleConstructor());
		this.moduleEventRouter = new ModuleEventRouter(this.moduleList);

		MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);
	}

	public void setup(ASMDataTable table) {

		Set<ASMDataTable.ASMData> dataSet = table.getAll(Module.class.getCanonicalName());
		for (ASMDataTable.ASMData data : dataSet) {
			String moduleID = (String) data.getAnnotationInfo().get("moduleID");
			List<String> modDependencies = (List<String>) data.getAnnotationInfo().get("modDependencies");
			if (modDependencies == null || modDependencies.stream().allMatch(Loader::isModLoaded)) {
				try {
					this.moduleRegistry.registerModules((Class<? extends ModuleBase>) Class.forName(data.getClassName()));
				} catch (ClassNotFoundException e) {
					LOGGER.error("Could not initialize module: {} {}", moduleID, e);
				}
			} else {
				LOGGER.info("Module {} is missing at least one of mod dependencies: {}, skipping loading...", moduleID, modDependencies);
			}
		}

		for (ModuleBase module : moduleList) {
			module.getLogger().debug("Registering event handlers");
			for (Class<?> clazz : module.getEventBusSubscribers()) {
				MinecraftForge.EVENT_BUS.register(clazz);
			}
		}
		// Initialize modules.
		this.moduleRegistry.initializeModules(this.modId);

	}

	public void routeFMLStateEvent(FMLStateEvent event) {
		// Маршрутизируем событие FML
		this.moduleEventRouter.routeFMLStateEvent(event);
	}
}
