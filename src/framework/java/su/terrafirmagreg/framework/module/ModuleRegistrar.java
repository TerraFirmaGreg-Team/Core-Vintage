package su.terrafirmagreg.framework.module;

import su.terrafirmagreg.api.util.AnnotationUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.ModuleMap.ModuleWrapper;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.api.IModuleManager;
import su.terrafirmagreg.framework.module.api.IModuleRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;

import net.minecraftforge.fml.common.Loader;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class ModuleRegistrar implements IModuleRegistrar {

  private final String modId;
  private final ModuleMap map;

  public ModuleRegistrar(IModuleManager manager) {
    this.modId = manager.getModId();
    this.map = manager.getMap();

  }

  @Override
  public <T extends IModule> void addModule(T module) {

    var moduleClass = module.getClass();
    if (validate(moduleClass)) {
      var moduleInfo = AnnotationUtils.getAnnotation(module, ModuleInfo.class);
      var identifier = ModUtils.resource(modId, moduleInfo.id());
      module.setIdentifier(identifier);
      map.put(moduleClass, ModuleWrapper.of(identifier, module));
    }
  }


  private <T extends IModule> boolean validate(Class<T> module) {
    var annotation = AnnotationUtils.getAnnotation(module, ModuleInfo.class);
    boolean moduleIsEnabled = annotation.enabled();
    boolean dependenciesAreLoaded = Arrays.stream(annotation.modDependencies()).allMatch(Loader::isModLoaded);
    boolean moduleIsNotRegistered = !map.containsKey(module);

    return moduleIsEnabled && dependenciesAreLoaded && moduleIsNotRegistered;
  }

}
