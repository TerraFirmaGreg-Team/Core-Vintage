package su.terrafirmagreg.framework.manager.command.api;

import su.terrafirmagreg.framework.manager.api.IBaseRegistrar;

public interface ICommandRegistrar extends IBaseRegistrar<ICommandEntry> {


  <E extends ICommandEntry> void addCommand(E entry);
}
