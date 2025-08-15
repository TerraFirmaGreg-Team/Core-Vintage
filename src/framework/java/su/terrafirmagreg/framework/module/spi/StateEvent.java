package su.terrafirmagreg.framework.module.spi;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.eventhandler.Event;

import lombok.Getter;

public class StateEvent extends Event {

  public static class PreInitialization extends StateEvent {

  }

  public static class Initialization extends StateEvent {

  }

  public static class PostInitialization extends StateEvent {

  }

  public static class LoadComplete extends StateEvent {

  }

  public static class ServerAboutToStart extends StateEvent {

  }

  public static class ServerStarted extends StateEvent {

  }

  public static class ServerStarting extends StateEvent {

    @Getter
    private MinecraftServer server;

    public ServerStarting(MinecraftServer server) {
      this.server = server;
    }

  }

  public static class ServerStopped extends StateEvent {

  }

  public static class ServerStopping extends StateEvent {

  }


}
