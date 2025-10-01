package su.terrafirmagreg.framework.manager.command;

import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.framework.manager.command.api.ICommandEntry;
import su.terrafirmagreg.framework.manager.command.api.ICommandManager;
import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.framework.manager.command.spi.CommandTree;
import su.terrafirmagreg.framework.module.api.IModuleEntry;
import su.terrafirmagreg.framework.module.spi.StateEvent;

import net.minecraft.command.CommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.google.common.base.Preconditions;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
public class CommandManager implements ICommandManager {

  public static final FrameworkLogger LOGGER = FrameworkLogger.of(CommandManager.class);


  private final IModuleEntry module;
  private final Multimap<Class<?>, ICommandEntry> mapEntry;

  private final ICommandRegistrar registrar;


  private CommandManager(IModuleEntry module) {

    this.module = module;
    this.mapEntry = LinkedHashMultimap.create();

    this.registrar = new CommandRegistrar(this);

    MinecraftForge.EVENT_BUS.register(this);
  }

  public static ICommandManager of(IModuleEntry module) {

    return MANAGER_MAP.computeIfAbsent(module, CommandManager::new);
  }

  @Override
  public FrameworkLogger getLogger() {
    return LOGGER;
  }

  @SubscribeEvent
  public void onServerStarting(StateEvent.ServerStarting event) {

    CommandHandler registry = Preconditions.checkNotNull(
      (CommandHandler) event.getServer().getCommandManager(), "Registry not found: %s", event.getClass()
    );

    var moduleIdentifier = module.getIdentifier();

    var modCommandTree = new CommandTree(moduleIdentifier.getNamespace());
    var moduleCommandTree = new CommandTree(moduleIdentifier.getPath());

    modCommandTree.addSubcommand(moduleCommandTree);
    this.getMapEntry().values().forEach(entry -> {
      moduleCommandTree.addSubcommand(entry.asEntry());

      getLogger().info("Service {}: {}",
        entry.getClass().getSimpleName(), entry.asEntry().getName()
      );

      registry.registerCommand(modCommandTree);
    });
  }
}
