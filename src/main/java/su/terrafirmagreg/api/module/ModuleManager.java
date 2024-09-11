package su.terrafirmagreg.api.module;

import su.terrafirmagreg.api.util.AnnotationUtils;
import su.terrafirmagreg.data.lib.LoggingHelper;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLStateEvent;


import com.google.common.base.Preconditions;

import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class ModuleManager {

  @Getter
  private static final ModuleManager instance = new ModuleManager();
  private static final LoggingHelper LOGGER = LoggingHelper.of(ModuleManager.class.getSimpleName());

  private final Set<IModule> loadedModules = new LinkedHashSet<>();
  private final Map<ResourceLocation, IModule> sortedModules = new LinkedHashMap<>();

  private final ModuleEventRouter moduleEventRouter;

  private ModuleManager() {

    this.moduleEventRouter = new ModuleEventRouter(this.loadedModules);
    MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);
  }

  public void setup() {

    configureModules(getModules());

    loadedModules.forEach(module -> {
      module.getLogger().info("Registering event handlers");
      module.getEventBusSubscribers().forEach(MinecraftForge.EVENT_BUS::register);
    });
  }

  private void configureModules(Map<String, List<IModule>> modules) {
    Set<ResourceLocation> toLoad = new HashSet<>();
    Set<IModule> modulesToLoad = new HashSet<>();

    modules.forEach((container, containerModules) -> {
      IModule coreModule = getCoreModule(containerModules);
      Preconditions.checkNotNull(coreModule,
              "Could not find core module for module container " + container);

      containerModules.remove(coreModule);
      containerModules.add(0, coreModule);

      modulesToLoad.addAll(containerModules.stream()
              .filter(this::isModuleEnabled)
              .collect(Collectors.toSet()));

      toLoad.addAll(containerModules.stream()
              .filter(this::isModuleEnabled)
              .map(module -> new ResourceLocation(container,
                      module.getClass().getAnnotation(Module.class).moduleID().getName()))
              .collect(Collectors.toSet()));
    });

    modulesToLoad.removeIf(module -> {
      Set<ResourceLocation> dependencies = module.getDependencyUids();
      if (!toLoad.containsAll(dependencies)) {
        Module annotation = module.getClass().getAnnotation(Module.class);
        String moduleID = annotation.moduleID().getName();
        toLoad.remove(new ResourceLocation(moduleID));
        ModuleManager.LOGGER.info(
                "Module {} is missing at least one of module dependencies: {}, skipping loading...",
                moduleID, dependencies);
        return true;
      }
      return false;
    });

    List<IModule> sortedModulesList = modulesToLoad.stream()
            .filter(module -> sortedModules.keySet().containsAll(module.getDependencyUids()))
            .collect(Collectors.toList());

    sortedModulesList.forEach(module -> {
      var annotation = module.getClass().getAnnotation(Module.class).moduleID();
      sortedModules.put(new ResourceLocation(annotation.getID(), annotation.getName()), module);
    });

    loadedModules.addAll(sortedModules.values());
  }

  private Map<String, List<IModule>> getModules() {
    return AnnotationUtils.getAnnotations(Module.class, IModule.class).keySet().stream()
            .collect(Collectors.groupingBy(
                    module -> module.getClass().getAnnotation(Module.class).moduleID().getID(),
                    LinkedHashMap::new,
                    Collectors.toList()
            ));
  }

  private static IModule getCoreModule(List<IModule> modules) {
    return modules.stream()
            .filter(module -> module.getClass().getAnnotation(Module.class).coreModule())
            .findFirst()
            .orElse(null);
  }

  public boolean isModuleEnabled(IModule module) {
    var annotation = module.getClass().getAnnotation(Module.class);
    return annotation.moduleID().isEnabled();
  }

  public boolean isModuleEnabled(ResourceLocation id) {
    return sortedModules.containsKey(id);
  }

  public void routeFMLStateEvent(FMLStateEvent event) {
    // Маршрутизируем событие FML
    this.moduleEventRouter.routeFMLStateEvent(event);
  }
}
