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
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLStateEvent;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ModuleManager implements IModuleManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleManager.class.getSimpleName());

  private static final Map<ResourceLocation, IModule> MODULE_MAP = new Object2ObjectLinkedOpenHashMap<>();
  private static final Map<String, IModuleContainer> CONTAINER_MAP = new Object2ObjectLinkedOpenHashMap<>();

  private final String modId;
  private final ModuleEventRouter moduleEventRouter;

  private ModuleManager(String modId) {

    configureContainers();
    configureModules();

    this.modId = modId;
    this.moduleEventRouter = new ModuleEventRouter(MODULE_MAP.values());

    MinecraftForge.EVENT_BUS.register(this.moduleEventRouter);
  }


  public static IModuleManager of(String modId) {
    return new ModuleManager(modId);
  }

  private static @Nullable IModule getCoreModule(List<IModule> modules) {
    return modules.stream()
      .filter(module -> AnnotationUtils.getAnnotation(module, ModuleInfo.class).coreModule())
      .findFirst()
      .orElse(null);
  }

  public void registerContainer(IModuleContainer container) {
    Preconditions.checkNotNull(container, "Module container cannot be null ");
    CONTAINER_MAP.put(container.getID(), container);
  }

  private void configureContainers() {
    LOGGER.debug("Configuring containers...");

    AnnotationUtils.getAnnotations(ModuleContainer.class, IModuleContainer.class,
      aClass -> AnnotationUtils.getAnnotation(aClass, ModuleContainer.class).enabled()
    ).keySet().forEach(this::registerContainer);
  }

  private void configureModules() {
    LOGGER.debug("Configuring modules...");

    Set<ResourceLocation> toLoad = new LinkedHashSet<>();
    Set<IModule> modulesToLoad = new LinkedHashSet<>();

    for (IModuleContainer container : CONTAINER_MAP.values()) {
      String containerID = container.getID();
      List<IModule> containerModules = getModules().get(containerID);
      IModule coreModule = getCoreModule(containerModules);
      Preconditions.checkNotNull(coreModule, "Could not find core module for module container " + containerID);

      containerModules.remove(coreModule);
      containerModules.add(0, coreModule);

      // Remove disabled modules and gather potential modules to load
      Iterator<IModule> iterator = containerModules.iterator();
      while (iterator.hasNext()) {
        IModule module = iterator.next();
        if (!isModuleEnabled(module)) {
          iterator.remove();
          LOGGER.debug("Module disabled: {}", module);
          continue;
        }
        ModuleInfo annotation = module.getClass().getAnnotation(ModuleInfo.class);
        toLoad.add(new ResourceLocation(containerID, annotation.moduleID()));
        modulesToLoad.add(module);
      }

    }

    // Check any module dependencies
    Iterator<IModule> iterator;
    boolean changed;
    do {
      changed = false;
      iterator = modulesToLoad.iterator();
      while (iterator.hasNext()) {
        IModule module = iterator.next();

        // Check module dependencies
        Set<ResourceLocation> dependencies = module.getDependencyUids();
        if (!toLoad.containsAll(dependencies)) {
          iterator.remove();
          changed = true;
          ModuleInfo annotation = module.getClass().getAnnotation(ModuleInfo.class);
          String moduleID = annotation.moduleID();
          toLoad.remove(new ResourceLocation(moduleID));
          LOGGER.info("Module {} is missing at least one of module dependencies: {}, skipping loading...", moduleID, dependencies);
        }
      }
    } while (changed);

    // Sort modules by their module dependencies
    do {
      changed = false;
      iterator = modulesToLoad.iterator();
      while (iterator.hasNext()) {
        IModule module = iterator.next();
        if (MODULE_MAP.keySet().containsAll(module.getDependencyUids())) {
          iterator.remove();
          ModuleInfo annotation = module.getClass().getAnnotation(ModuleInfo.class);
          MODULE_MAP.put(new ResourceLocation(annotation.containerID(), annotation.moduleID()), module);
          changed = true;
          break;
        }
      }
    } while (changed);

  }

  private Map<String, List<IModule>> getModules() {
    return AnnotationUtils.getAnnotations(ModuleInfo.class, IModule.class, module -> {
        var annotation = AnnotationUtils.getAnnotation(module, ModuleInfo.class);

        if (!CONTAINER_MAP.containsKey(annotation.containerID())) {
          LOGGER.debug("Module disabled: {}", annotation.moduleID());
          return false;
        }

        var modDependencies = Arrays.asList(annotation.modDependencies());
        return annotation.enabled() && modDependencies.stream().allMatch(Loader::isModLoaded);
      }).keySet().stream()
      .sorted(Comparator.comparing(module -> {
        ModuleInfo info = AnnotationUtils.getAnnotation(module, ModuleInfo.class);
        return info.containerID() + ":" + info.moduleID();
      }))
      .collect(Collectors.groupingBy(module -> {
        ModuleInfo info = AnnotationUtils.getAnnotation(module, ModuleInfo.class);
        return info.containerID();
      }, LinkedHashMap::new, Collectors.toList()));
  }

  @Override
  public boolean isModuleEnabled(ResourceLocation id) {

    return MODULE_MAP.containsKey(id);
  }

  @Override
  public boolean isModuleEnabled(IModule module) {
    var annotation = AnnotationUtils.getAnnotation(module, ModuleInfo.class);
    var modDependencies = Arrays.asList(annotation.modDependencies());
    return annotation.enabled() && modDependencies.stream().allMatch(Loader::isModLoaded);
  }

  @Override
  public void routeEvent(FMLStateEvent event) {

    this.moduleEventRouter.routeEvent(event);
  }

}

