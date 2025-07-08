package su.terrafirmagreg.framework.module.spi;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.eventhandler.Event;

import lombok.Getter;

public class EventState extends Event {

  public static class Construction extends EventState {

  }

  public static class PreInitialization extends EventState {

  }

  public static class Initialization extends EventState {

  }

  public static class PostInitialization extends EventState {

  }

  public static class LoadComplete extends EventState {

  }

  public static class ServerAboutToStart extends EventState {

  }

  public static class ServerStarted extends EventState {

  }

  public static class ServerStarting extends EventState {

    @Getter
    private MinecraftServer server;

    public ServerStarting(MinecraftServer server) {
      this.server = server;
    }

  }

  public static class ServerStopped extends EventState {

  }

  public static class ServerStopping extends EventState {

  }


}
