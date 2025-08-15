package su.terrafirmagreg.framework.module.api;

public interface IModuleRegistrar {

  <T extends IModuleEntry> void addModule(T module);

}
