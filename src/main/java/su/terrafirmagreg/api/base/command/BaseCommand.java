package su.terrafirmagreg.api.base.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

/**
 * This class is a new implementation of CommandBase, which adds working permission levels.
 */
public abstract class BaseCommand extends CommandBase {

  @Override
  public boolean checkPermission(MinecraftServer server, ICommandSender sender) {

    return this.getRequiredPermissionLevel() <= 0 || super.checkPermission(server, sender);
  }
}
