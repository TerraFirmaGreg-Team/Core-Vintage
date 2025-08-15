package su.terrafirmagreg.framework.manager.command;

import su.terrafirmagreg.framework.manager.command.api.ICommandEntry;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
public class CommandRegistrar implements ICommandRegistrar {

  private final IModuleEntry module;
  private final Multimap<Class<?>, ICommandEntry> mapEntry;

  public CommandRegistrar(CommandManager manager) {

    this.module = manager.getModule();
    this.mapEntry = manager.getMapEntry();
  }

  @Override
  public <E extends ICommandEntry> void addCommand(E entry) {

    addEntry(entry);
  }
}
