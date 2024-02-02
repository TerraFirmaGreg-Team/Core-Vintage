package su.terrafirmagreg.api.module;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import su.terrafirmagreg.api.util.Helpers;

import java.util.*;
import java.util.stream.Collectors;

public class ModuleRegistry {


    private List<Class<? extends ModuleBase>> moduleClassList;
    private Set<ModuleBase> loadedModules;
    private ModuleConstructor moduleConstructor;

    public ModuleRegistry(Set<ModuleBase> loadedModules, ModuleConstructor moduleConstructor) {
        this.moduleConstructor = moduleConstructor;

        this.moduleClassList = new ArrayList<>();
        this.loadedModules = loadedModules;
    }

    public final void registerModules(ASMDataTable asmDataTable) {

        Map<String, List<ModuleBase>> modules = getModules(asmDataTable);
        configureModules(modules);
    }

    public void initializeModules(String modId) {

        for (ModuleBase module : loadedModules) {
            //loadedContainer = containers.get(getContainerID(module));
            module.getLogger().debug("Registering event handlers");
            for (Class<?> clazz : module.getEventBusSubscribers()) {
                MinecraftForge.EVENT_BUS.register(clazz);
            }
        }

        for (Class<? extends ModuleBase> moduleClass : this.moduleClassList) {
            ModuleBase module = this.moduleConstructor.constructModule(modId, moduleClass);

            if (module != null) {
                this.loadedModules.add(module);
            }
        }

        // Don't really need to keep this around.
        this.moduleClassList = null;

        // Sort the module list by module priority.
        Collections.sort(this.loadedModules);
    }

    private Map<String, List<ModuleBase>> getModules(ASMDataTable table) {
        List<ModuleBase> instances = getInstances(table);
        Map<String, List<ModuleBase>> modules = new LinkedHashMap<>();
        for (ModuleBase module : instances) {
            Module info = module.getClass().getAnnotation(Module.class);
            modules.computeIfAbsent(info.containerID(), k -> new ArrayList<>()).add(module);
        }
        return modules;
    }

    @SuppressWarnings("unchecked")
    public List<ModuleBase> getInstances(ASMDataTable table) {
        Set<ASMDataTable.ASMData> dataSet = table.getAll(Module.class.getCanonicalName());
        List<ModuleBase> instances = new ArrayList<>();
        for (ASMDataTable.ASMData data : dataSet) {
            String moduleID = (String) data.getAnnotationInfo().get("moduleID");
            List<String> modDependencies = (ArrayList<String>) data.getAnnotationInfo().get("modDependencies");
            if (modDependencies == null || modDependencies.stream().allMatch(Loader::isModLoaded)) {
                try {
                    Class<?> clazz = Class.forName(data.getClassName());
                    instances.add((ModuleBase) clazz.newInstance());
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    ModuleManager2.LOGGER.error("Could not initialize module " + moduleID, e);
                }
            } else {
                ModuleManager2.LOGGER.info("Module {} is missing at least one of mod dependencies: {}, skipping loading...", moduleID,
                        modDependencies);
            }
        }
        return instances.stream().sorted((m1, m2) -> {
            Module m1a = m1.getClass().getAnnotation(Module.class);
            Module m2a = m2.getClass().getAnnotation(Module.class);
            return (m1a.containerID() + ":" + m1a.moduleID()).compareTo(m2a.containerID() + ":" + m2a.moduleID());
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    private void configureModules(Map<String, List<ModuleBase>> modules) {
        Map<ResourceLocation, ModuleBase> sortedModules = new LinkedHashMap<>();;
        Locale locale = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        Set<ResourceLocation> toLoad = new LinkedHashSet<>();
        Set<ModuleBase> modulesToLoad = new LinkedHashSet<>();
//        Configuration config = getConfiguration();
//        config.load();
//        config.addCustomCategoryComment(MODULE_CFG_CATEGORY_NAME,
//                "Module configuration file. Can individually enable/disable modules from TFG and its addons");

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
                    ModuleManager2.LOGGER.debug("Module disabled: {}", module);
                    continue;
                }
                Module annotation = module.getClass().getAnnotation(Module.class);
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
                    Module annotation = module.getClass().getAnnotation(Module.class);
                    String moduleID = annotation.moduleID();
                    toLoad.remove(new ResourceLocation(moduleID));
                    ModuleManager2.LOGGER.info("Module {} is missing at least one of module dependencies: {}, skipping loading...",
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
                    Module annotation = module.getClass().getAnnotation(Module.class);
                    sortedModules.put(new ResourceLocation(annotation.containerID(), annotation.moduleID()), module);
                    changed = true;
                    break;
                }
            }
        } while (changed);

        loadedModules.addAll(sortedModules.values());

//        if (config.hasChanged()) config.save();
        Locale.setDefault(locale);
    }

        public boolean isModuleEnabled(String moduleID) {
        return loadedModules.containsKey(Helpers.getID(moduleID));
    }

    public boolean isModuleEnabled(ModuleBase module) {
        var annotation = module.getClass().getAnnotation(Module.class);
        var comment = getComment(module);
        var prop = getConfiguration().get(MODULE_CFG_CATEGORY_NAME,
                annotation.containerID() + ":" + annotation.moduleID(), true, comment);
        return prop.getBoolean();
    }

}
