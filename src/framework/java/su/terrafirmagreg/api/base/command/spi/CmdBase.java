package su.terrafirmagreg.api.base.command.spi;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import lombok.Getter;

/**
 * This class is a new implementation of CommandBase, which adds working permission levels.
 */
public abstract class CmdBase extends CommandBase {

  @Override
  public boolean checkPermission(MinecraftServer server, ICommandSender sender) {

    return this.getRequiredPermissionLevel() <= 0 || super.checkPermission(server, sender);
  }

  @Getter
  public static class Level {

    public static final Level ALL;
    public static final Level OP_OR_SP;
    public static final Level OP;
    public static final Level STRONG_OP_OR_SP;
    public static final Level STRONG_OP;
    public static final Level SERVER;

    static {
      ALL = new Level(0, (server, sender, command) -> true);
      OP_OR_SP = new Level(2, (server, sender, command) -> server.isSinglePlayer() || sender.canUseCommand(2, command.getName()));
      OP = new Level(2, (server, sender, command) -> sender.canUseCommand(2, command.getName()));
      STRONG_OP_OR_SP = new Level(4, (server, sender, command) -> server.isSinglePlayer() || sender.canUseCommand(4, command.getName()));
      STRONG_OP = new Level(4, (server, sender, command) -> sender.canUseCommand(4, command.getName()));
      SERVER = new Level(4, (server, sender, command) -> sender instanceof MinecraftServer);
    }


    public final int requiredPermissionLevel;
    public final PermissionChecker permissionChecker;

    public Level(int requiredPermissionLevel, PermissionChecker permissionChecker) {
      this.requiredPermissionLevel = requiredPermissionLevel;
      this.permissionChecker = permissionChecker;
    }

    public interface PermissionChecker {

      boolean checkPermission(MinecraftServer server, ICommandSender sender, ICommand command);
    }
  }
}
