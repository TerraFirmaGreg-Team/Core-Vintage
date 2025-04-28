package su.terrafirmagreg.framework.manager.command.api;

import net.minecraft.command.ICommand;

public interface ICommandRegistrar {

  <T extends ICommand> void addCommand(T command);
}
