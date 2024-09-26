package su.terrafirmagreg.api.network;

import su.terrafirmagreg.TerraFirmaGreg;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;

import com.google.common.base.Throwables;

import org.jetbrains.annotations.NotNull;

import java.util.IdentityHashMap;
import java.util.Map;

// * https://github.com/SleepyTrousers/EnderCore/blob/1.12/src/main/java/com/enderio/core/common/network/ThreadedNetworkWrapper.java

public class ThreadedNetworkWrapper {

  private static final @NotNull Map<Class<? extends IMessage>, SimpleNetworkWrapper> typesClient = new IdentityHashMap<>();
  private static final @NotNull Map<Class<? extends IMessage>, SimpleNetworkWrapper> typesServer = new IdentityHashMap<>();
  /**
   * The network wrapper instance, created when a new handler is constructed.
   */
  private final @NotNull SimpleNetworkWrapper parent;

  /**
   * Constructs a new NetworkHandler, which is basically a wrapper for SimpleNetworkWrapper.
   *
   * @param netId The id for the new network channel. This should probably be your mod id.
   */
  public ThreadedNetworkWrapper(String netId) {
    if (netId.length() > 20) {
      throw new RuntimeException("Channel name '" + netId
                                 + "' is too long for Forge. Maximum length supported is 20 characters.");
    }
    this.parent = NetworkRegistry.INSTANCE.newSimpleChannel(netId);
  }

  static synchronized void registerChannelMapping(Class<? extends IMessage> requestMessageType,
                                                  @NotNull SimpleNetworkWrapper parent, Side side) {
    (side == Side.CLIENT ? typesClient : typesServer).put(requestMessageType, parent);
  }

  private static <REQ extends IMessage, REPLY extends IMessage> IMessageHandler<? super REQ, ? extends REPLY> instantiate(
    Class<? extends IMessageHandler<? super REQ, ? extends REPLY>> handler) {
    try {
      return handler.getDeclaredConstructor().newInstance();
    } catch (Throwable e) {
      TerraFirmaGreg.LOGGER.error("Failed to instanciate " + handler);
      throw Throwables.propagate(e);
    }
  }

  private static synchronized SimpleNetworkWrapper getServerParent(IMessage message) {
    final SimpleNetworkWrapper parent = typesClient.get(message.getClass());
    if (parent == null) {
      throw new RuntimeException(
        "Trying to send unregistered network packet: " + message.getClass());
    }
    return parent;
  }

  private static synchronized SimpleNetworkWrapper getClientParent(IMessage message) {
    final SimpleNetworkWrapper parent = typesServer.get(message.getClass());
    if (parent == null) {
      throw new RuntimeException(
        "Trying to send unregistered network packet: " + message.getClass());
    }
    return parent;
  }

  public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
    Class<? extends IMessageHandler<REQ, REPLY>> messageHandler,
    Class<REQ> requestMessageType,
    int discriminator,
    Side side) {

    if (side == Side.CLIENT && FMLLaunchHandler.side() != Side.CLIENT) {
      parent.registerMessage(new NullHandler<REQ, REPLY>(), requestMessageType, discriminator,
                             side);
      registerChannelMapping(requestMessageType, parent, side);
      return;
    }
    registerMessage(instantiate(messageHandler), requestMessageType, discriminator, side);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public <REQ extends IMessage, REPLY extends IMessage> void registerMessage(
    IMessageHandler<? super REQ, ? extends REPLY> messageHandler,
    Class<REQ> requestMessageType,
    int discriminator,
    Side side) {

    parent.registerMessage((Wrapper<REQ, REPLY>) new Wrapper(messageHandler), requestMessageType,
                           discriminator, side);
    registerChannelMapping(requestMessageType, parent, side);
  }

  /**
   * Retrieves the packet corresponding to the given message from the server parent.
   *
   * @param message The message for which to retrieve the packet.
   * @return The packet corresponding to the message.
   */
  public Packet<?> getPacketFrom(IMessage message) {
    return getServerParent(message).getPacketFrom(message);
  }

  /**
   * Sends the specified message to all entities tracking the given entity.
   *
   * @param message The message to send.
   * @param entity  The entity whose tracking entities should receive the message.
   */
  public void sendToAllTracking(IMessage message, Entity entity) {
    getServerParent(message).sendToAllTracking(message, entity);
  }

  /**
   * Sends the message to all players.
   *
   * @param message The message to send.
   */
  public void sendToAll(IMessage message) {
    getServerParent(message).sendToAll(message);
  }

  /**
   * Sends the specified message to all players around the given TileEntity.
   *
   * @param message The message to send.
   * @param tile    The TileEntity from which to calculate the position and world.
   */
  public void sendToAllAround(IMessage message, TileEntity tile) {
    sendToAllAround(message, tile.getPos(), tile.getWorld());
  }

  /**
   * Sends the specified message packet to all players around the given position in the world.
   *
   * @param packet The message packet to send.
   * @param pos    The position around which to send the message packet.
   * @param world  The world in which to send the message packet.
   */
  public void sendToAllAround(IMessage packet, BlockPos pos, World world) {
    if (!(world instanceof WorldServer worldServer)) {
      return;
    }

    PlayerChunkMap playerManager = worldServer.getPlayerChunkMap();

    int chunkX = pos.getX() >> 4;
    int chunkZ = pos.getZ() >> 4;

    for (Object playerObj : world.playerEntities) {
      if (playerObj instanceof EntityPlayerMP player) {

        if (playerManager.isPlayerWatchingChunk(player, chunkX, chunkZ)) {
          sendTo(packet, player);
        }
      }
    }
  }

  /**
   * Sends the message to a specific player.
   *
   * @param message The message to send.
   * @param player  The player to receive the message.
   */
  public void sendTo(IMessage message, EntityPlayerMP player) {
    getServerParent(message).sendTo(message, player);
  }

  /**
   * Sends the message to everyone near a point.
   *
   * @param message The message to send.
   * @param world   The world to send the message to.
   * @param pos     The position to send the message to.
   * @param range   The range of the message.
   */
  public void sendToAllAround(IMessage message, World world, BlockPos pos, double range) {

    sendToAllAround(message,
                    new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX() + 0.5d,
                                                    pos.getY() + 0.5d, pos.getZ() + 0.5d, range));
  }

  /**
   * Sends the message to everyone near a point.
   *
   * @param message The message to send.
   * @param point   The point to send the message to.
   */
  public void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
    getServerParent(message).sendToAllAround(message, point);
  }

  /**
   * Sends the message to everyone in a dimension.
   *
   * @param message     The message to send.
   * @param dimensionId The id of the dimension to send the message to.
   */
  public void sendToDimension(IMessage message, int dimensionId) {
    getServerParent(message).sendToDimension(message, dimensionId);
  }

  /**
   * Sends a message to the server from a client.
   *
   * @param message The message to send.
   */
  public void sendToServer(IMessage message) {
    getClientParent(message).sendToServer(message);
  }

  private final class NullHandler<REQ extends IMessage, REPLY extends IMessage> implements
                                                                                IMessageHandler<REQ, REPLY> {

    @Override
    public REPLY onMessage(REQ message, MessageContext ctx) {
      return null;
    }

  }

  private final class Wrapper<REQ extends IMessage, REPLY extends IMessage> implements
                                                                            IMessageHandler<REQ, REPLY> {

    private final IMessageHandler<REQ, REPLY> wrapped;

    public Wrapper(IMessageHandler<REQ, REPLY> wrapped) {

      this.wrapped = wrapped;
    }

    @Override
    public REPLY onMessage(final REQ message, final MessageContext ctx) {

      final IThreadListener target = ctx.side == Side.CLIENT ?
                                     Minecraft.getMinecraft() :
                                     FMLCommonHandler.instance().getMinecraftServerInstance();

      if (target != null) {
        target.addScheduledTask(new Runner(message, ctx));
      }
      return null;
    }

    @Override
    public int hashCode() {
      return this.wrapped.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Wrapper) {
        return this.wrapped.equals(((Wrapper<?, ?>) obj).wrapped);

      } else {
        return this.wrapped.equals(obj);
      }
    }

    @Override
    public String toString() {
      return this.wrapped.toString();
    }

    private final class Runner implements Runnable {

      private final REQ message;
      private final MessageContext ctx;

      public Runner(final REQ message, final MessageContext ctx) {
        this.message = message;
        this.ctx = ctx;
      }

      @Override
      public void run() {
        final REPLY reply = Wrapper.this.wrapped.onMessage(this.message, this.ctx);

        if (reply != null) {
          if (this.ctx.side == Side.CLIENT) {
            sendToServer(reply);

          } else {
            final EntityPlayerMP player = this.ctx.getServerHandler().player;

            if (player != null) {
              sendTo(reply, player);
            }
          }
        }
      }

    }

  }
}
