package su.terrafirmagreg.framework.manager.command;

import su.terrafirmagreg.framework.manager.command.api.ICommandEntry;
import su.terrafirmagreg.framework.manager.command.api.ICommandService;
import su.terrafirmagreg.framework.manager.command.spi.CommandTree;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import lombok.Getter;

@Getter
public class CommandService implements ICommandService {

  private final IModule module;
  private final CommandMap map;

  public CommandService(CommandManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();
  }

  @Override
  public void routeEvent(FMLServerStartingEvent event) {

    var moduleIdentifier = module.getIdentifier();

    var containerCmdTree = new CommandTree(moduleIdentifier.getNamespace());
    var moduleCmdTree = new CommandTree(moduleIdentifier.getPath());

    containerCmdTree.addSubcommand(moduleCmdTree);
    this.map.values().forEach(wrapper -> {

      var command = wrapper.getCommand();
      var identifier = wrapper.getIdentifier();

      if (command instanceof ICommandEntry cmdSettings) {
        if (!identifier.equals(cmdSettings.getRegistryName())) {
          cmdSettings.setRegistryName(identifier);
        }
      }

      moduleCmdTree.addSubcommand(command);
      event.registerServerCommand(containerCmdTree);
    });
  }
}
