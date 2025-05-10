package su.terrafirmagreg.framework.manager.command;

import su.terrafirmagreg.framework.manager.command.CommandMap.CommandWrapper;

import net.minecraft.command.ICommand;
import net.minecraft.util.ResourceLocation;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Data;

public class CommandMap extends Object2ObjectOpenHashMap<Class<? extends ICommand>, CommandWrapper> {

  public static CommandMap of() {
    return new CommandMap();
  }


  @Data(staticConstructor = "of")
  public static class CommandWrapper {

    private final ResourceLocation identifier;
    private final ICommand command;


  }
}
