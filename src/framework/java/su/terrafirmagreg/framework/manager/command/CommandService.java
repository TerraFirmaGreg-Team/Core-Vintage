package su.terrafirmagreg.framework.manager.command;

import su.terrafirmagreg.framework.manager.command.api.ICommandEntry;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.manager.command.api.ICommandService;
import su.terrafirmagreg.framework.manager.command.spi.CommandTree;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.module.spi.EventState;

import net.minecraft.command.CommandHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import lombok.Getter;

@Getter
public class CommandService implements ICommandService {

  private final IModule module;
  private final ICommandRegistrar registrar;
  private final CommandMap map;

  public CommandService(CommandManager manager) {

    this.module = manager.getModule();
    this.registrar = manager.getRegistrar();
    this.map = manager.getMap();
  }


  @SubscribeEvent
  public void onServerStarting(EventState.ServerStarting event) {

    var moduleIdentifier = module.getIdentifier();

    var containerCmdTree = new CommandTree(moduleIdentifier.getNamespace());
    var moduleCmdTree = new CommandTree(moduleIdentifier.getPath());

    containerCmdTree.addSubcommand(moduleCmdTree);
    this.map.values().forEach(wrapper -> {

      var command = wrapper.getCommand();
      var identifier = wrapper.getIdentifier();

      if (command instanceof ICommandEntry commandEntry) {
        if (!identifier.equals(commandEntry.getRegistryName())) {
          commandEntry.setRegistryName(identifier);
        }
      }

      moduleCmdTree.addSubcommand(command);
      CommandHandler ch = (CommandHandler) event.getServer().getCommandManager();
      ch.registerCommand(containerCmdTree);
    });
  }
}
