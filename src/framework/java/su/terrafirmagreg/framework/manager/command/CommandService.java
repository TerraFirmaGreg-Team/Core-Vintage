package su.terrafirmagreg.framework.manager.command;

import su.terrafirmagreg.framework.manager.command.api.ICommandService;
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

    this.map.register(event);
  }
}
