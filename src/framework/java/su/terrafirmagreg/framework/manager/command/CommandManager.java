package su.terrafirmagreg.framework.manager.command;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.command.api.ICommandManager;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.manager.command.api.ICommandService;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.common.MinecraftForge;

import lombok.Getter;

@Getter
public class CommandManager implements ICommandManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(CommandManager.class);


  private final IModule module;
  private final CommandMap map;

  private final ICommandRegistrar registrar;
  private final ICommandService service;


  private CommandManager(IModule module) {

    this.module = module;
    this.map = CommandMap.of();

    this.registrar = new CommandRegistrar(this);
    this.service = new CommandService(this);

    MinecraftForge.EVENT_BUS.register(this.service);
  }

  public static synchronized ICommandManager of(IModule module) {

    return MANAGER_MAP.computeIfAbsent(module, CommandManager::new);
  }

}
