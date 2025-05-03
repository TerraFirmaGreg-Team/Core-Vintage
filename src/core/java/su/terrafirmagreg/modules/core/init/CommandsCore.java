package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.command.api.ICommandRegistrar;
import su.terrafirmagreg.modules.core.command.CommandPlayer;

public final class CommandsCore {

  public static void onRegister(ICommandRegistrar registrar) {

    registrar.addCommand(new CommandPlayer());
  }
}
