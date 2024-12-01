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

  public static void onRegister(RegistryManager manager) {

    DEBUG_INFO = manager.command(new CommandDebugInfo());
    STRIP_WORLD = manager.command(new CommandStripWorld());
    MAKE_TREE = manager.command(new CommandGenTree());
    HEAT = manager.command(new CommandHeat());
    PLAYER = manager.command(new CommandPlayer());
    TIME = manager.command(new CommandTime());
    HORSE_POWER = manager.command(new CommandHorsePower());

  }
}
