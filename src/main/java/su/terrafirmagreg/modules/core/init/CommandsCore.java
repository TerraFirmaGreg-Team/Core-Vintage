package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.object.command.CommandDebugInfo;
import su.terrafirmagreg.modules.core.object.command.CommandHeat;
import su.terrafirmagreg.modules.core.object.command.CommandHorsePower;
import su.terrafirmagreg.modules.core.object.command.CommandPlayer;
import su.terrafirmagreg.modules.core.object.command.CommandStripWorld;
import su.terrafirmagreg.modules.core.object.command.CommandTime;
import su.terrafirmagreg.modules.wood.object.command.CommandGenTree;

public final class CommandsCore {

  public static CommandDebugInfo DEBUG_INFO;
  public static CommandStripWorld STRIP_WORLD;
  public static CommandGenTree MAKE_TREE;
  public static CommandHeat HEAT;
  public static CommandPlayer PLAYER;
  public static CommandTime TIME;
  public static CommandHorsePower HORSE_POWER;

  public static void onRegister(RegistryManager registryManager) {

    DEBUG_INFO = registryManager.command(new CommandDebugInfo());
    STRIP_WORLD = registryManager.command(new CommandStripWorld());
    MAKE_TREE = registryManager.command(new CommandGenTree());
    HEAT = registryManager.command(new CommandHeat());
    PLAYER = registryManager.command(new CommandPlayer());
    TIME = registryManager.command(new CommandTime());
    HORSE_POWER = registryManager.command(new CommandHorsePower());

  }
}
