package su.terrafirmagreg.framework.command;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.command.api.ICommandManager;
import su.terrafirmagreg.framework.command.spi.CommandWhapper;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;

@Getter
public class CommandManager implements ICommandManager {

  private static final LoggingHelper LOGGER = LoggingHelper.of(CommandManager.class.getSimpleName());

  private static final Map<String, CommandWhapper> COMMAND_WRAPPER_MAP = new Object2ObjectOpenHashMap<>();

  private final IModule module;
  private final CommandWhapper wrapper;

  private CommandManager(IModule module) {
    this.module = module;

    var moduleIdentifier = module.getIdentifier();
    this.wrapper = COMMAND_WRAPPER_MAP.computeIfAbsent(moduleIdentifier.getNamespace(), CommandWhapper::new);
  }

  public static synchronized ICommandManager of(IModule module) {
    return new CommandManager(module);
  }


  @Override
  public void addCommand(ICommand command) {
    this.wrapper.addSubcommand(command);
  }

  @Override
  public void registerServerCommand(FMLServerStartingEvent event) {
    event.registerServerCommand(wrapper);
  }

  public static class NoOp extends CommandManager {

    private NoOp(IModule module) {
      super(module);
    }

    @Override
    public void addCommand(ICommand command) {

    }

    @Override
    public void registerServerCommand(FMLServerStartingEvent event) {

    }
  }
}
