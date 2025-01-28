package su.terrafirmagreg.framework.module;


import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.util.AnnotationUtils;
import su.terrafirmagreg.api.util.ModUtils;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ModuleManager implements IModuleManager {

  public static final Map<ResourceLocation, IModule> MODULE_MAP = new Object2ObjectLinkedOpenHashMap<>();
  private static final LoggingHelper LOGGER = LoggingHelper.of(ModuleManager.class.getSimpleName());
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

  private static @Nullable IModule getCoreModule(List<IModule> modules) {
    return modules.stream()
      .filter(module -> module.getClass().getAnnotation(ModuleInfo.class).coreModule())
      .findFirst()
      .orElse(null);
  }

  public static IModuleManager of() {
    return new ModuleManager();
  }

  public void registerContainer(IModuleContainer container) {
    Preconditions.checkNotNull(container);
    CONTAINER_MAP.put(container.getID(), container);
  }

  private void configureContainers() {
    LOGGER.debug("Configuring containers...");

    AnnotationUtils.getAnnotations(ModuleContainer.class, IModuleContainer.class).entrySet().stream()
      .filter(entry -> entry.getValue().enabled())
      .map(Map.Entry::getKey)
      .forEach(this::registerContainer);
  }

  private void configureModules() {
    LOGGER.debug("Configuring modules...");

    Set<ResourceLocation> toLoad = new LinkedHashSet<>();
    Set<IModule> modulesToLoad = new LinkedHashSet<>();

    AnnotationUtils.getAnnotations(ModuleInfo.class, IModule.class).keySet().stream()
      .filter(module -> {
        if (!CONTAINER_MAP.containsKey(getContainerID(module))) {
          LOGGER.debug("Module disabled: {}", module);
          return false;
        }
        if (!isModuleEnabled(module)) {
          LOGGER.debug("Module disabled: {}", module.getIdentifier());
          return false;
        }
        toLoad.add(module.getIdentifier());
        modulesToLoad.add(module);
        return true;
      })
      .collect(Collectors.groupingBy(module -> module.getIdentifier().getNamespace(), LinkedHashMap::new, Collectors.toList()))
      .forEach((containerID, modules) -> {
        IModule coreModule = getCoreModule(modules);
        Preconditions.checkNotNull(coreModule, "Could not find core module for module container " + containerID);

        modules.remove(coreModule);
        modules.add(0, coreModule);

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
              String moduleID = module.getIdentifier().getPath();
              toLoad.remove(ModUtils.resource(module.getIdentifier().getPath()));
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
              MODULE_MAP.put(module.getIdentifier(), module);
              changed = true;
              break;
            }
          }
        } while (changed);
      });

  }

  @Override
  public boolean isModuleEnabled(ResourceLocation id) {

    return MODULE_MAP.containsKey(id);
  }

  @Override
  public boolean isModuleEnabled(IModule module) {
    var annotation = module.getClass().getAnnotation(ModuleInfo.class);
    var modDependencies = Arrays.asList(annotation.modDependencies());
    return annotation.enabled() && modDependencies.stream().allMatch(Loader::isModLoaded);
  }

  @Override
  public void routeEvent(FMLStateEvent event) {

    this.moduleEventRouter.routeEvent(event);
  }

}

