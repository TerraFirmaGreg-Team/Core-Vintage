package su.terrafirmagreg.framework.manager.command;

import su.terrafirmagreg.framework.manager.command.CommandMap.CommandWrapper;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraft.command.ICommand;

import lombok.Getter;

@Getter
public class CommandRegistrar implements ICommandRegistrar {

  private final IModule module;
  private final CommandMap map;

  public CommandRegistrar(CommandManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();
  }

  @Override
  public <T extends ICommand> void addCommand(T command) {
    var commandClass = command.getClass();
    this.map.put(commandClass, CommandWrapper.of(module.getIdentifier(), command));
  }
}
