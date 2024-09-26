package su.terrafirmagreg.modules.core.object.command;

import su.terrafirmagreg.api.base.command.BaseCommandTree;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import com.google.common.collect.Lists;

import java.util.List;

public class CommandManager extends BaseCommandTree {

  private static final CommandManager INSTANCE = new CommandManager();

  private CommandManager() {
  }

  public static CommandManager create(FMLServerStartingEvent event) {
    event.registerServerCommand(INSTANCE);
    return INSTANCE;
  }

  @Override
  public String getName() {
    return "terrafirmagreg";
  }

  @Override
  public String getUsage(ICommandSender sender) {
    return ModUtils.localize("command", "usage");
  }

  @Override
  public List<String> getAliases() {
    return Lists.newArrayList("tfg");
  }

  public void addCommand(ICommand command) {
    this.addSubcommand(command);
  }

}
