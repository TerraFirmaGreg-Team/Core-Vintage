package su.terrafirmagreg.api.util;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public final class CommandUtils {

  private static final String USAGE_TAG = "usage";
  private static final String EXPECTED_TAG = "expected";

  public static void sendLocalizedChatMessage(ICommandSender sender, String name, Object... args) {
    sender.sendMessage(new TextComponentTranslation(name, args));
  }

  public static void sendLocalizedChatMessage(ICommandSender sender, ICommand command, String name, Object... args) {
    sender.sendMessage(new TextComponentTranslation(ModUtils.format(command.getUsage(sender), name), args));
  }


  public static void sendLocalizedChatMessage(ICommandSender sender, Style chatStyle, String locTag, Object... args) {
    TextComponentTranslation chat = new TextComponentTranslation(locTag, args);
    chat.setStyle(chatStyle);
    sender.sendMessage(chat);
  }

  /**
   * Avoid using this function if at all possible. Commands are processed on the server, which has no localization information.
   * <p>
   * StringUtil.localize() is NOT a valid alternative for sendLocalizedChatMessage(). Messages will not be localized properly if you use StringUtil.localize().
   */
  public static void sendChatMessage(ICommandSender sender, String message) {
    sender.sendMessage(new TextComponentString(message));
  }

  public static List<String> getListOfStringsMatchingLastWord(String[] strings, String... lastWords) {
    return CommandBase.getListOfStringsMatchingLastWord(strings, lastWords);
  }

  public static void throwWrongUsage(ICommandSender sender, ICommand command, String name, Object... replacements) throws WrongUsageException {
    throw new WrongUsageException(ModUtils.format(command.getUsage(sender), EXPECTED_TAG, name), replacements);
  }


  @Getter
  public enum Level {
    ALL(0, (server, sender, command) -> true),
    OP_OR_SP(2, (server, sender, command) -> server.isSinglePlayer() || sender.canUseCommand(2, command.getName())),
    OP(2, (server, sender, command) -> sender.canUseCommand(2, command.getName())),
    STRONG_OP_OR_SP(4, (server, sender, command) -> server.isSinglePlayer() || sender.canUseCommand(4, command.getName())),
    STRONG_OP(4, (server, sender, command) -> sender.canUseCommand(4, command.getName())),
    SERVER(4, (server, sender, command) -> sender instanceof MinecraftServer);


    public final int requiredPermissionLevel;
    public final PermissionChecker permissionChecker;

    Level(int requiredPermissionLevel, PermissionChecker permissionChecker) {
      this.requiredPermissionLevel = requiredPermissionLevel;
      this.permissionChecker = permissionChecker;
    }

    public interface PermissionChecker {

      boolean checkPermission(MinecraftServer server, ICommandSender sender, ICommand command);
    }
  }

  public enum ExecuteType {
    SET,
    RESET,
    ADD,
    GET;

    public static ExecuteType parse(ICommandSender sender, ICommand command, String text) throws CommandException {
      try {
        return ExecuteType.valueOf(text.toUpperCase());

      } catch (IllegalArgumentException e) {
        throw new WrongUsageException(ModUtils.format(command.getUsage(sender), EXPECTED_TAG, "first_argument"));
      }
    }

    public static ExecuteType parse(String text) throws CommandException {
      try {
        return ExecuteType.valueOf(text.toUpperCase());
      } catch (IllegalArgumentException e) {
        throw new WrongUsageException(
          ModUtils.localize("command", "player.usage_expected_first_argument"));
      }
    }
  }
}
