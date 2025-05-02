package su.terrafirmagreg.framework.manager.command;

import su.terrafirmagreg.api.base.command.spi.CmdTreeBase;
import su.terrafirmagreg.framework.manager.command.CommandMap.CommandWrapper;

import net.minecraft.command.ICommand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

public class CommandMap extends Object2ObjectOpenHashMap<Class<? extends ICommand>, CommandWrapper> {

  public static CommandMap of() {
    return new CommandMap();
  }

  public void register(FMLServerStartingEvent event) {

    this.values().forEach(wrapper -> {
      var cmdTree = new CmdTreeBase(wrapper.getIdentifier());
      cmdTree.addSubcommand(wrapper.getCommand());
      event.registerServerCommand(cmdTree);
    });
  }

  @Data(staticConstructor = "of")
  public static class CommandWrapper {

    private final ResourceLocation identifier;
    private final ICommand command;


  }
}
