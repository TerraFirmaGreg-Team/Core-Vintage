package su.terrafirmagreg.framework.command.spi;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

/**
 * This class is a new implementation of CommandBase, which adds working permission levels.
 */
public abstract class CommandBase extends net.minecraft.command.CommandBase {

  @Override
  public boolean checkPermission(MinecraftServer server, ICommandSender sender) {

    return this.getRequiredPermissionLevel() <= 0 || super.checkPermission(server, sender);
  }
}
