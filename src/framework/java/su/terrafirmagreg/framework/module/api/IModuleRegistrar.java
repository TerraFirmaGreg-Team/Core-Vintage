package su.terrafirmagreg.framework.module.api;

public interface IModuleRegistrar {

  <T extends IModule> void addModule(T module);

}
