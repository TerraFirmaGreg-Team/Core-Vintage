package su.terrafirmagreg.framework.module;


import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.util.AnnotationUtils;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.api.IModuleContainer;
import su.terrafirmagreg.framework.module.api.IModuleManager;
import su.terrafirmagreg.framework.module.api.ModuleContainer;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleEventRouter;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLStateEvent;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModuleManager implements IModuleManager {

  private static final LoggingHelper LOGGER = LoggingHelper.of(ModuleManager.class.getName());

  private static final Map<ResourceLocation, IModule> MODULE_MAP = new Object2ObjectLinkedOpenHashMap<>();
  private static final Map<String, IModuleContainer> CONTAINER_MAP = new Object2ObjectLinkedOpenHashMap<>();

  private final ModuleEventRouter moduleEventRouter;

  private ModuleManager() {
    configureContainers();
    configureModules();

    this.moduleEventRouter = new ModuleEventRouter(MODULE_MAP.values());

    MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);
  }

  private static String getContainerID(IModule module) {
    var annotation = module.getClass().getAnnotation(ModuleInfo.class);
    return annotation.containerID();
  }

  private static IModule getCoreModule(List<IModule> modules) {
    return modules.stream()
      .filter(module -> module.getClass().getAnnotation(ModuleInfo.class).coreModule())
      .findFirst()
      .orElse(null);
  }

  public static IModuleManager of() {
    return new ModuleManager();
  }

  private void configureContainers() {
    LOGGER.info("Configuring containers...");

    AnnotationUtils.getAnnotations(ModuleContainer.class, IModuleContainer.class).entrySet().stream()
      .filter(entry -> entry.getValue().enabled())
      .map(Map.Entry::getKey)
      .forEach(container -> CONTAINER_MAP.put(container.getID(), container));
  }

  private void configureModules() {
    LOGGER.info("Configuring modules...");

    AnnotationUtils.getAnnotations(ModuleInfo.class, IModule.class).keySet().stream().filter(module -> {
        if (!CONTAINER_MAP.containsKey(getContainerID(module))) {
          return false;
        }
        return isModuleEnabled(module);
      })
      .collect(Collectors.groupingBy(module -> module.getIdentifier().getNamespace(), LinkedHashMap::new, Collectors.toList()))
      .forEach((container, containerModules) -> {
        var coreModule = getCoreModule(containerModules);
        Preconditions.checkNotNull(coreModule, "Could not find core module for module container " + container);

        containerModules.remove(coreModule);
        containerModules.add(0, coreModule);

        MODULE_MAP.putAll(containerModules.stream().collect(Collectors.toMap(IModule::getIdentifier, module -> module)));
      });

  }

  @Override
  public boolean isModuleEnabled(ResourceLocation id) {

    return MODULE_MAP.containsKey(id);
  }

  @Override
  public boolean isModuleEnabled(IModule module) {
    var annotation = module.getClass().getAnnotation(ModuleInfo.class);
    return annotation.enabled();
  }

  @Override
  public void routeEvent(FMLStateEvent event) {

    this.moduleEventRouter.routeEvent(event);
  }

}

