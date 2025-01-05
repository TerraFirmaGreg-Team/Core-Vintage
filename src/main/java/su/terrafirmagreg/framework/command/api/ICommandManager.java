package su.terrafirmagreg.framework.command.api;

import su.terrafirmagreg.framework.command.spi.CommandWhapper;

import net.minecraft.command.ICommand;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public interface ICommandManager {

  CommandWhapper getWrapper();

  void addCommand(ICommand command);

  void registerServerCommand(FMLServerStartingEvent event);
}
