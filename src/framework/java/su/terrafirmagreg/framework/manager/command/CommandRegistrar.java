package su.terrafirmagreg.framework.manager.command;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.command.CommandMap.CommandWrapper;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraft.command.ICommand;
import net.minecraft.util.ResourceLocation;

import lombok.Getter;

@Getter
public class CommandRegistrar implements ICommandRegistrar {

  private final IModule module;
  private final CommandMap map;

  public CommandRegistrar(CommandManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();
  }

  public ResourceLocation getIdentifier(String identifier) {

    return ModUtils.resource(module.getIdentifier(), identifier);
  }

  @Override
  public <T extends ICommand> void addCommand(T command) {
    var commandClass = command.getClass();
    this.map.put(commandClass, CommandWrapper.of(getIdentifier(command.getName()), command));
  }
}
