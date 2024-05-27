package su.terrafirmagreg.api.module;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.util.AnnotationUtils;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLStateEvent;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModuleManager {

    private static final ModuleManager INSTANCE = new ModuleManager();
    private static final LoggingHelper LOGGER = new LoggingHelper(ModuleManager.class.getSimpleName());

    private final Set<ModuleBase> loadedModules = new LinkedHashSet<>();
    private final Map<ResourceLocation, ModuleBase> sortedModules = new LinkedHashMap<>();

    private final ModuleEventRouter moduleEventRouter;

    private ModuleManager() {

        this.moduleEventRouter = new ModuleEventRouter(this.loadedModules);
        MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);
    }

    public static ModuleManager getInstance() {
        return INSTANCE;
    }

    private static ModuleBase getCoreModule(List<ModuleBase> modules) {
        for (ModuleBase module : modules) {
            Module annotation = module.getClass().getAnnotation(Module.class);
            if (annotation.coreModule()) return module;
        }
        return null;
    }

    public void setup() {

        Map<String, List<ModuleBase>> modules = getModules();

        configureModules(modules);

        for (ModuleBase module : loadedModules) {
            module.getLogger().info("Registering event handlers");
            for (Class<?> clazz : module.getEventBusSubscribers()) {
                MinecraftForge.EVENT_BUS.register(clazz);
            }
        }

    }

    private Map<String, List<ModuleBase>> getModules() {
        var instances = AnnotationUtils.getAnnotations(Module.class, ModuleBase.class);
        Map<String, List<ModuleBase>> modules = new LinkedHashMap<>();
        for (var module : instances.keySet()) {
            var info = module.getClass().getAnnotation(Module.class);
            modules.computeIfAbsent(info.moduleID().getID(), k -> new ArrayList<>()).add(module);
        }
        return modules;
    }

    private void configureModules(Map<String, List<ModuleBase>> modules) {
        Set<ResourceLocation> toLoad = new LinkedHashSet<>();
        Set<ModuleBase> modulesToLoad = new LinkedHashSet<>();

        for (var container : modules.keySet()) {
            List<ModuleBase> containerModules = modules.get(container);
            ModuleBase coreModule = getCoreModule(containerModules);

            if (coreModule == null) {
                throw new IllegalStateException("Could not find core module for module container " + container);
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
                    ModuleManager.LOGGER.debug("Module disabled: {}", module);
                    continue;
                }
                Module annotation = module.getClass().getAnnotation(Module.class);
                toLoad.add(new ResourceLocation(container, annotation.moduleID().getName()));
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
                    String moduleID = annotation.moduleID().getName();
                    toLoad.remove(new ResourceLocation(moduleID));
                    ModuleManager.LOGGER.info("Module {} is missing at least one of module dependencies: {}, skipping loading...", moduleID, dependencies);
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
                    var annotation = module.getClass().getAnnotation(Module.class).moduleID();
                    sortedModules.put(new ResourceLocation(annotation.getID(), annotation.getName()), module);
                    changed = true;
                    break;
                }
            }
        } while (changed);

        loadedModules.addAll(sortedModules.values());
    }

    public boolean isModuleEnabled(ResourceLocation id) {
        return sortedModules.containsKey(id);
    }

    public boolean isModuleEnabled(ModuleBase module) {
        var annotation = module.getClass().getAnnotation(Module.class);
        return annotation.moduleID().isEnabled();
    }

    public void routeFMLStateEvent(FMLStateEvent event) {
        // Маршрутизируем событие FML
        this.moduleEventRouter.routeFMLStateEvent(event);
    }
}
