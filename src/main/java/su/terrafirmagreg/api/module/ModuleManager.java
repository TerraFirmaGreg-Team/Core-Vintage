package su.terrafirmagreg.api.module;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import lombok.Getter;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.api.util.Helpers;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static su.terrafirmagreg.Tags.MOD_NAME;

public class ModuleManager {

    private static final String MODULE_CFG_FILE_NAME = "modules.cfg";
    private static final String MODULE_CFG_CATEGORY_NAME = "modules";
    private static File configFolder;
    private final Map<ResourceLocation, ModuleBase> sortedModules;
    private final Set<ModuleBase> loadedModules;
    public static final Logger LOGGER = LogManager.getLogger("TFG Module Loader");
    private final ModuleEventRouter moduleEventRouter;
    private Map<String, IModuleContainer> containers = new LinkedHashMap<>();
    @Getter
    private IModuleContainer loadedContainer;
    private Configuration config;


    public ModuleManager() {
        this.sortedModules = new LinkedHashMap<>();
        this.loadedModules = new LinkedHashSet<>();
        this.moduleEventRouter = new ModuleEventRouter(loadedModules);
        MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);
    }

    private static ModuleBase getCoreModule(List<ModuleBase> modules) {
        for (ModuleBase module : modules) {
            var annotation = module.getClass().getAnnotation(ModuleTFG.class);
            if (annotation.coreModule()) return module;
        }
        return null;
    }

    private static String getContainerID(ModuleBase module) {
        var annotation = module.getClass().getAnnotation(ModuleTFG.class);
        return annotation.containerID();
    }

    public boolean isModuleEnabled(String moduleID) {
        return sortedModules.containsKey(Helpers.getID(moduleID));
    }

    public boolean isModuleEnabled(ModuleBase module) {
        var annotation = module.getClass().getAnnotation(ModuleTFG.class);
        var comment = getComment(module);
        var prop = getConfiguration().get(MODULE_CFG_CATEGORY_NAME,
                annotation.containerID() + ":" + annotation.moduleID(), true, comment);
        return prop.getBoolean();
    }

    public void registerContainer(IModuleContainer container) {
        Preconditions.checkNotNull(container);
        containers.put(container.getID(), container);
    }

    public void setup(ASMDataTable asmDataTable, File configDirectory) {
        // find and register all containers registered with the @ModuleContainer annotation, then sort them by container
        // name
        discoverContainers(asmDataTable);
        containers = containers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

        configFolder = new File(configDirectory, MOD_NAME);
        Map<String, List<ModuleBase>> modules = getModules(asmDataTable);
        configureModules(modules);

        for (ModuleBase module : loadedModules) {
            loadedContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Registering event handlers");
            for (Class<?> clazz : module.getEventBusSubscribers()) {
                MinecraftForge.EVENT_BUS.register(clazz);
            }
        }
    }

    public void processIMC(ImmutableList<FMLInterModComms.IMCMessage> messages) {
        for (FMLInterModComms.IMCMessage message : messages) {
            for (ModuleBase module : loadedModules) {
                if (module.processIMC(message)) {
                    break;
                }
            }
        }
    }

    private void configureModules(Map<String, List<ModuleBase>> modules) {
        Locale locale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        Set<ResourceLocation> toLoad = new LinkedHashSet<>();
        Set<ModuleBase> modulesToLoad = new LinkedHashSet<>();
        Configuration config = getConfiguration();
        config.load();
        config.addCustomCategoryComment(MODULE_CFG_CATEGORY_NAME,
                "Module configuration file. Can individually enable/disable modules from TFG and its addons");

        for (IModuleContainer container : containers.values()) {
            String containerID = container.getID();
            List<ModuleBase> containerModules = modules.get(containerID);
            ModuleBase coreModule = getCoreModule(containerModules);
            if (coreModule == null) {
                throw new IllegalStateException("Could not find core module for module container " + containerID);
            } else {
                containerModules.remove(coreModule);
                containerModules.add(0, coreModule);
            }

            // Remove disabled modules and gather potential modules to load
            Iterator<ModuleBase> iterator = containerModules.iterator();
            while (iterator.hasNext()) {
                ModuleBase module = iterator.next();
                if (!isModuleEnabled(module)) {
                    iterator.remove();
                    LOGGER.debug("Module disabled: {}", module);
                    continue;
                }
                ModuleTFG annotation = module.getClass().getAnnotation(ModuleTFG.class);
                toLoad.add(new ResourceLocation(containerID, annotation.moduleID()));
                modulesToLoad.add(module);
            }
        }

        // Check any module dependencies
        Iterator<ModuleBase> iterator;
        boolean changed;
        do {
            changed = false;
            iterator = modulesToLoad.iterator();
            while (iterator.hasNext()) {
                ModuleBase module = iterator.next();

                // Check module dependencies
                Set<ResourceLocation> dependencies = module.getDependencyUids();
                if (!toLoad.containsAll(dependencies)) {
                    iterator.remove();
                    changed = true;
                    ModuleTFG annotation = module.getClass().getAnnotation(ModuleTFG.class);
                    String moduleID = annotation.moduleID();
                    toLoad.remove(new ResourceLocation(moduleID));
                    LOGGER.info("Module {} is missing at least one of module dependencies: {}, skipping loading...",
                            moduleID, dependencies);
                }
            }
        } while (changed);

        // Sort modules by their module dependencies
        do {
            changed = false;
            iterator = modulesToLoad.iterator();
            while (iterator.hasNext()) {
                ModuleBase module = iterator.next();
                if (sortedModules.keySet().containsAll(module.getDependencyUids())) {
                    iterator.remove();
                    ModuleTFG annotation = module.getClass().getAnnotation(ModuleTFG.class);
                    sortedModules.put(new ResourceLocation(annotation.containerID(), annotation.moduleID()), module);
                    changed = true;
                    break;
                }
            }
        } while (changed);

        loadedModules.addAll(sortedModules.values());

        if (config.hasChanged()) config.save();
        Locale.setDefault(locale);
    }

    private Map<String, List<ModuleBase>> getModules(ASMDataTable table) {
        List<ModuleBase> instances = getInstances(table);
        Map<String, List<ModuleBase>> modules = new LinkedHashMap<>();
        for (ModuleBase module : instances) {
            ModuleTFG info = module.getClass().getAnnotation(ModuleTFG.class);
            modules.computeIfAbsent(info.containerID(), k -> new ArrayList<>()).add(module);
        }
        return modules;
    }

    @SuppressWarnings("unchecked")
    private List<ModuleBase> getInstances(ASMDataTable table) {
        Set<ASMDataTable.ASMData> dataSet = table.getAll(ModuleTFG.class.getCanonicalName());
        List<ModuleBase> instances = new ArrayList<>();
        for (ASMDataTable.ASMData data : dataSet) {
            String moduleID = (String) data.getAnnotationInfo().get("moduleID");
            List<String> modDependencies = (ArrayList<String>) data.getAnnotationInfo().get("modDependencies");
            if (modDependencies == null || modDependencies.stream().allMatch(Loader::isModLoaded)) {
                try {
                    Class<?> clazz = Class.forName(data.getClassName());
                    instances.add((ModuleBase) clazz.newInstance());
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    LOGGER.error("Could not initialize module " + moduleID, e);
                }
            } else {
                LOGGER.info("Module {} is missing at least one of mod dependencies: {}, skipping loading...", moduleID,
                        modDependencies);
            }
        }
        return instances.stream().sorted((m1, m2) -> {
            ModuleTFG m1a = m1.getClass().getAnnotation(ModuleTFG.class);
            ModuleTFG m2a = m2.getClass().getAnnotation(ModuleTFG.class);
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
                LOGGER.error("Could not initialize module container " + data.getClassName(), e);
            }
        }
    }

    private String getComment(ModuleBase module) {
        ModuleTFG annotation = module.getClass().getAnnotation(ModuleTFG.class);

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
        if (config == null) config = new Configuration(new File(configFolder, MODULE_CFG_FILE_NAME));
        return config;
    }

    public void routeFMLStateEvent(FMLStateEvent event) {
        System.out.println("Routing event: " + event);
        this.moduleEventRouter.routeFMLStateEvent(event);
    }
}
