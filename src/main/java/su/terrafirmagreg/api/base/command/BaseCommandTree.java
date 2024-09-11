package su.terrafirmagreg.api.base.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.command.CommandTreeBase;


import java.util.StringJoiner;

/**
 * This implementation of CommandTreeBase fixes a few permission issues, and adds a better help screen.
 */
public abstract class BaseCommandTree extends CommandTreeBase {

  @Override
  public void execute(MinecraftServer server, ICommandSender sender, String[] args)
          throws CommandException {

    if (args.length == 0) {

      sender.sendMessage(new TextComponentString(this.getSubCommandDescriptions(sender)));
    } else {

      super.execute(server, sender, args);
    }
  }

  /**
   * Creates a string with all the usage lines for the sub commands.
   *
   * @param sender The thing sending the command.
   * @return A string with all the usage lines for sub commands.
   */
  private String getSubCommandDescriptions(ICommandSender sender) {

    final StringJoiner joiner = new StringJoiner("\n");

    for (final ICommand command : this.getSubCommands()) {

      joiner.add(command.getUsage(sender));
    }

    return joiner.toString();
  }

  @Override
  public boolean checkPermission(MinecraftServer server, ICommandSender sender) {

    return this.getRequiredPermissionLevel() <= 0 || super.checkPermission(server, sender);
  }
}
