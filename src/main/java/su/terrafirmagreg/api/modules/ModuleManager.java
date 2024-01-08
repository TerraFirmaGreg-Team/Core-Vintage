package su.terrafirmagreg.api.modules;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static su.terrafirmagreg.Tags.MOD_ID;

public class ModuleManager implements IModuleManager {

	private static final ModuleManager INSTANCE = new ModuleManager();
	private static final String MODULE_CFG_FILE_NAME = "modules.cfg";
	private static final String MODULE_CFG_CATEGORY_NAME = "modules";
	private static File configFolder;
	private final Map<ResourceLocation, ITFGModule> sortedModules = new LinkedHashMap<>();
	private final Set<ITFGModule> loadedModules = new LinkedHashSet<>();
	private final Logger logger = LogManager.getLogger("TFG Module Loader");
	private Map<String, IModuleContainer> containers = new LinkedHashMap<>();
	private IModuleContainer currentContainer;
	private ModuleStage currentStage = ModuleStage.C_SETUP;
	private Configuration config;

	private ModuleManager() {}

	public static ModuleManager getInstance() {
		return INSTANCE;
	}

	private static ITFGModule getCoreModule(List<ITFGModule> modules) {
		for (ITFGModule module : modules) {
			TFGModule annotation = module.getClass().getAnnotation(TFGModule.class);
			if (annotation.coreModule()) {
				return module;
			}
		}
		return null;
	}

	private static String getContainerID(ITFGModule module) {
		TFGModule annotation = module.getClass().getAnnotation(TFGModule.class);
		return annotation.containerID();
	}

	@Override
	public boolean isModuleEnabled(ResourceLocation id) {
		return sortedModules.containsKey(id);
	}

	public boolean isModuleEnabled(ITFGModule module) {
		TFGModule annotation = module.getClass().getAnnotation(TFGModule.class);
		String comment = getComment(module);
		Property prop = getConfiguration().get(MODULE_CFG_CATEGORY_NAME,
				annotation.containerID() + ":" + annotation.moduleID(), true, comment);
		return prop.getBoolean();
	}

	@Override
	public IModuleContainer getLoadedContainer() {
		return currentContainer;
	}

	@Override
	public ModuleStage getStage() {
		return currentStage;
	}

	@Override
	public boolean hasPassedStage(ModuleStage stage) {
		return currentStage.ordinal() > stage.ordinal();
	}

	@Override
	public void registerContainer(IModuleContainer container) {
		if (currentStage != ModuleStage.C_SETUP) {
			logger.error("Failed to register module container {}, as module loading has already begun", container);
			return;
		}
		Preconditions.checkNotNull(container);
		containers.put(container.getID(), container);
	}

	public void setup(ASMDataTable asmDataTable, File configDirectory) {
		// find and register all containers registered with the @ModuleContainer annotation, then sort them by container
		// name
		discoverContainers(asmDataTable);
		containers = containers.entrySet().stream()
		                       .sorted(Map.Entry.comparingByKey())
		                       .collect(Collectors.toMap(
				                       Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

		currentStage = ModuleStage.M_SETUP;
		configFolder = new File(configDirectory, MOD_ID);
		Map<String, List<ITFGModule>> modules = getModules(asmDataTable);
		configureModules(modules);

		for (ITFGModule module : loadedModules) {
			currentContainer = containers.get(getContainerID(module));
			module.getLogger().debug("Registering event handlers");
			for (Class<?> clazz : module.getEventBusSubscribers()) {
				MinecraftForge.EVENT_BUS.register(clazz);
			}
		}
	}

	public void onConstruction(FMLConstructionEvent event) {
		currentStage = ModuleStage.CONSTRUCTION;
		this.fireEvent(module -> {
			module.getLogger().debug("Construction start");
			module.construction(event);
			module.getLogger().debug("Construction complete");
		});
	}

	public void onPreInit(FMLPreInitializationEvent event) {
		currentStage = ModuleStage.PRE_INIT;
		this.fireEvent(module -> {
			module.getLogger().debug("Registering packets");
			module.onNetworkRegister();

			module.getLogger().debug("Registering");
			module.onRegister();

			module.getLogger().debug("Pre-init start");
			module.preInit(event);
			module.getLogger().debug("Pre-init complete");

			if (event.getSide() == Side.CLIENT) {
				module.getLogger().debug("Client Registering");
				module.onClientRegister();

				module.getLogger().debug("Client Pre-init start");
				module.clientPreInit(event);
				module.getLogger().debug("Client Pre-init complete");
			}
		});
	}

	public void onInit(FMLInitializationEvent event) {
		currentStage = ModuleStage.INIT;
		this.fireEvent(module -> {
			module.getLogger().debug("Init start");
			module.init(event);
			module.getLogger().debug("Init complete");

			if (event.getSide() == Side.CLIENT) {
				module.getLogger().debug("Client Init start");
				module.clientInit(event);
				module.getLogger().debug("Client Init complete");
			}
		});

	}

	public void onPostInit(FMLPostInitializationEvent event) {
		currentStage = ModuleStage.POST_INIT;
		this.fireEvent(module -> {
			module.getLogger().debug("Post-init start");
			module.postInit(event);
			module.getLogger().debug("Post-init complete");

			if (event.getSide() == Side.CLIENT) {
				module.getLogger().debug("Client Post-init start");
				module.clientPostInit(event);
				module.getLogger().debug("Client Post-init complete");
			}
		});
	}

	public void onLoadComplete(FMLLoadCompleteEvent event) {
		currentStage = ModuleStage.FINISHED;
		this.fireEvent(module -> {
			module.getLogger().debug("Load-complete start");
			module.loadComplete(event);
			module.getLogger().debug("Load-complete complete");
		});
	}

	public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
		currentStage = ModuleStage.SERVER_ABOUT_TO_START;
		this.fireEvent(module -> {
			module.getLogger().debug("Server-about-to-start start");
			module.serverAboutToStart(event);
			module.getLogger().debug("Server-about-to-start complete");
		});
	}

	public void onServerStarting(FMLServerStartingEvent event) {
		currentStage = ModuleStage.SERVER_STARTING;
		this.fireEvent(module -> {
			module.getLogger().debug("Server-starting start");
			module.serverStarting(event);
			module.getLogger().debug("Server-starting complete");
		});
	}

	public void onServerStarted(FMLServerStartedEvent event) {
		currentStage = ModuleStage.SERVER_STARTED;
		this.fireEvent(module -> {
			module.getLogger().debug("Server-started start");
			module.serverStarted(event);
			module.getLogger().debug("Server-started complete");
		});
	}

	public void onServerStopping(FMLServerStoppingEvent event) {
		this.fireEvent(module -> {
			module.getLogger().debug("Server-stopping start");
			module.serverStopping(event);
			module.getLogger().debug("Server-stopping complete");
		});
	}

	public void onServerStopped(FMLServerStoppedEvent event) {
		this.fireEvent(module -> {
			module.getLogger().debug("Server-topped start");
			module.serverStopped(event);
			module.getLogger().debug("Server-topped complete");
		});
	}

	public void processIMC(ImmutableList<FMLInterModComms.IMCMessage> messages) {
		for (FMLInterModComms.IMCMessage message : messages) {
			for (ITFGModule module : loadedModules) {
				if (module.processIMC(message)) {
					break;
				}
			}
		}
	}

	private void configureModules(Map<String, List<ITFGModule>> modules) {
		Locale locale = Locale.getDefault();
		Locale.setDefault(Locale.ENGLISH);
		Set<ResourceLocation> toLoad = new LinkedHashSet<>();
		Set<ITFGModule> modulesToLoad = new LinkedHashSet<>();
		Configuration config = getConfiguration();
		config.load();
		config.addCustomCategoryComment(MODULE_CFG_CATEGORY_NAME,
				"Module configuration file. Can individually enable/disable modules from TFG and its addons");

		for (IModuleContainer container : containers.values()) {
			String containerID = container.getID();
			List<ITFGModule> containerModules = modules.get(containerID);
			ITFGModule coreModule = getCoreModule(containerModules);
			if (coreModule == null) {
				throw new IllegalStateException("Could not find core module for module container " + containerID);
			} else {
				containerModules.remove(coreModule);
				containerModules.add(0, coreModule);
			}

			// Remove disabled modules and gather potential modules to load
			Iterator<ITFGModule> iterator = containerModules.iterator();
			while (iterator.hasNext()) {
				ITFGModule module = iterator.next();
				if (!isModuleEnabled(module)) {
					iterator.remove();
					logger.debug("Module disabled: {}", module);
					continue;
				}
				TFGModule annotation = module.getClass().getAnnotation(TFGModule.class);
				toLoad.add(new ResourceLocation(containerID, annotation.moduleID()));
				modulesToLoad.add(module);
			}
		}

		// Check any module dependencies
		Iterator<ITFGModule> iterator;
		boolean changed;
		do {
			changed = false;
			iterator = modulesToLoad.iterator();
			while (iterator.hasNext()) {
				ITFGModule module = iterator.next();

				// Check module dependencies
				Set<ResourceLocation> dependencies = module.getDependencyUids();
				if (!toLoad.containsAll(dependencies)) {
					iterator.remove();
					changed = true;
					TFGModule annotation = module.getClass().getAnnotation(TFGModule.class);
					String moduleID = annotation.moduleID();
					toLoad.remove(new ResourceLocation(moduleID));
					logger.info("Module {} is missing at least one of module dependencies: {}, skipping loading...",
							moduleID, dependencies);
				}
			}
		} while (changed);

		// Sort modules by their module dependencies
		do {
			changed = false;
			iterator = modulesToLoad.iterator();
			while (iterator.hasNext()) {
				ITFGModule module = iterator.next();
				if (sortedModules.keySet().containsAll(module.getDependencyUids())) {
					iterator.remove();
					TFGModule annotation = module.getClass().getAnnotation(TFGModule.class);
					sortedModules.put(new ResourceLocation(annotation.containerID(), annotation.moduleID()), module);
					changed = true;
					break;
				}
			}
		} while (changed);

		loadedModules.addAll(sortedModules.values());

		if (config.hasChanged()) {
			config.save();
		}
		Locale.setDefault(locale);
	}

	private Map<String, List<ITFGModule>> getModules(ASMDataTable table) {
		List<ITFGModule> instances = getInstances(table);
		Map<String, List<ITFGModule>> modules = new LinkedHashMap<>();
		for (ITFGModule module : instances) {
			TFGModule info = module.getClass().getAnnotation(TFGModule.class);
			modules.computeIfAbsent(info.containerID(), k -> new ArrayList<>()).add(module);
		}
		return modules;
	}

	@SuppressWarnings("unchecked")
	private List<ITFGModule> getInstances(ASMDataTable table) {
		Set<ASMDataTable.ASMData> dataSet = table.getAll(TFGModule.class.getCanonicalName());
		List<ITFGModule> instances = new ArrayList<>();
		for (ASMDataTable.ASMData data : dataSet) {
			String moduleID = (String) data.getAnnotationInfo().get("moduleID");
			List<String> modDependencies = (ArrayList<String>) data.getAnnotationInfo().get("modDependencies");
			if (modDependencies == null || modDependencies.stream().allMatch(Loader::isModLoaded)) {
				try {
					Class<?> clazz = Class.forName(data.getClassName());
					instances.add((ITFGModule) clazz.newInstance());
				} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
					logger.error("Could not initialize module " + moduleID, e);
				}
			} else {
				logger.info("Module {} is missing at least one of mod dependencies: {}, skipping loading...", moduleID,
						modDependencies);
			}
		}
		return instances.stream().sorted((m1, m2) -> {
			TFGModule m1a = m1.getClass().getAnnotation(TFGModule.class);
			TFGModule m2a = m2.getClass().getAnnotation(TFGModule.class);
			return (m1a.containerID() + ":" + m1a.moduleID()).compareTo(m2a.containerID() + ":" + m2a.moduleID());
		}).collect(Collectors.toCollection(ArrayList::new));
	}

	private void discoverContainers(ASMDataTable table) {
		Set<ASMDataTable.ASMData> dataSet = table.getAll(ModuleContainer.class.getCanonicalName());
		for (ASMDataTable.ASMData data : dataSet) {
			try {
				Class<?> clazz = Class.forName(data.getClassName());
				registerContainer((IModuleContainer) clazz.newInstance());
			} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
				logger.error("Could not initialize module container " + data.getClassName(), e);
			}
		}
	}

	private String getComment(ITFGModule module) {
		TFGModule annotation = module.getClass().getAnnotation(TFGModule.class);

		String comment = annotation.description();
		Set<ResourceLocation> dependencies = module.getDependencyUids();
		if (!dependencies.isEmpty()) {
			Iterator<ResourceLocation> iterator = dependencies.iterator();
			StringBuilder builder = new StringBuilder(comment);
			builder.append("\n");
			builder.append("Module Dependencies: [ ");
			builder.append(iterator.next());
			while (iterator.hasNext()) {
				builder.append(", ").append(iterator.next());
			}
			builder.append(" ]");
			comment = builder.toString();
		}
		String[] modDependencies = annotation.modDependencies();
		if (modDependencies != null && modDependencies.length > 0) {
			Iterator<String> iterator = Arrays.stream(modDependencies).iterator();
			StringBuilder builder = new StringBuilder(comment);
			builder.append("\n");
			builder.append("Mod Dependencies: [ ");
			builder.append(iterator.next());
			while (iterator.hasNext()) {
				builder.append(", ").append(iterator.next());
			}
			builder.append(" ]");
			comment = builder.toString();
		}
		return comment;
	}

	private Configuration getConfiguration() {
		if (config == null) {
			config = new Configuration(new File(configFolder, MODULE_CFG_FILE_NAME));
		}
		return config;
	}

	private void fireEvent(Consumer<ITFGModule> moduleConsumer) {
		for (ITFGModule module : loadedModules) {
			currentContainer = containers.get(getContainerID(module));
			moduleConsumer.accept(module);
		}
	}
}
