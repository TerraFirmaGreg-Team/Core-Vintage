package su.terrafirmagreg.framework.manager.network.spi;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.api.library.IdSupplier;
import su.terrafirmagreg.api.library.serialization.ClassBufSerializer;
import su.terrafirmagreg.api.util.NetworkUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;

@Getter
public class NetworkThreadedWrapper extends SimpleNetworkWrapper {

  public static final Map<String, NetworkThreadedWrapper> WRAPPER_MAP = new Object2ObjectOpenHashMap<>();


  public static final int DEFAULT_RANGE = 64;

  private final String netId;
  private final IdSupplier idSupplier;

  public NetworkThreadedWrapper(String netId) {
    super(netId);

    this.netId = netId;
    this.idSupplier = new IdSupplier();

    this.registerMessage(new PacketInternal(this), PacketInternal.class, 0, Side.SERVER);
    this.registerMessage(new PacketInternal(this), PacketInternal.class, 1, Side.CLIENT);
  }

  // ContainerId : ModuleId
  public static synchronized NetworkThreadedWrapper of(ResourceLocation netId) {

    return WRAPPER_MAP.computeIfAbsent(netId.toString(), NetworkThreadedWrapper::new);
  }

  public static synchronized NetworkThreadedWrapper of(String netId) {

    return WRAPPER_MAP.computeIfAbsent(netId, NetworkThreadedWrapper::new);
  }


  /**
   * Retrieves the packet corresponding to the given packet from the server parent.
   *
   * @param packet The packet for which to retrieve the packet.
   * @return The packet corresponding to the packet.
   */
  public Packet<?> getPacketFrom(INetworkPacket packet) {

    return this.getPacketFrom(new PacketInternal(this).setPacket(packet));
  }

  // region ====== Send Messages ======


  /**
   * Sends the packet to all players.
   *
   * @param packet The packet to send.
   */
  public void sendToAll(INetworkPacket packet) {

    this.sendToAll(new PacketInternal(this).setPacket(packet));
  }

  /**
   * Sends the packet to a specific player.
   *
   * @param packet The packet to send.
   * @param player The player to receive the packet.
   */
  public void sendTo(INetworkPacket packet, EntityPlayerMP player) {

    this.sendTo(new PacketInternal(this).setPacket(packet), player);
  }

  /**
   * Sends the specified packet packet to all players around the given position in the world.
   *
   * @param packet The packet packet to send.
   * @param pos    The position around which to send the packet packet.
   * @param world  The world in which to send the packet packet.
   */
  public void sendTo(INetworkPacket packet, BlockPos pos, World world) {
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
   * Sends the packet to everyone near a point.
   *
   * @param packet The packet to send.
   * @param point  The point to send the packet to.
   */
  public void sendToAllAround(INetworkPacket packet, TargetPoint point) {

    this.sendToAllAround(new PacketInternal(this).setPacket(packet), point);
  }

  /**
   * Sends the packet to everyone near a point.
   *
   * @param packet The packet to send.
   * @param world  The world to send the packet to.
   * @param pos    The position to send the packet to.
   * @param range  The range of the packet.
   */
  public void sendToAllAround(INetworkPacket packet, World world, BlockPos pos, double range) {

    sendToAllAround(packet, new TargetPoint(world.provider.getDimension(), pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, range));
  }

  public void sendToAllAround(INetworkPacket packet, World world, BlockPos pos) {

    sendToAllAround(packet, world, pos, DEFAULT_RANGE);
  }

  public void sendToAllAround(INetworkPacket packet, int dimension, BlockPos blockPos, double range) {

    sendToAllAround(packet, dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ(), range);
  }

  public void sendToAllAround(INetworkPacket packet, int dimension, BlockPos blockPos) {

    sendToAllAround(packet, dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ(), DEFAULT_RANGE);
  }

  public void sendToAllAround(INetworkPacket packet, int dimension, double x, double y, double z, double range) {

    sendToAllAround(packet, new TargetPoint(dimension, x, y, z, range));
  }

  public void sendToAllAround(INetworkPacket packet, int dimension, double x, double y, double z) {

    sendToAllAround(packet, dimension, x, y, z, DEFAULT_RANGE);
  }

  public void sendToAllAround(INetworkPacket packet, TileEntity tile, int range) {
    BlockPos pos = tile.getPos();
    World world = tile.getWorld();
    WorldProvider provider = world.provider;
    int dimension = provider.getDimension();
    sendToAllAround(packet, dimension, pos.getX(), pos.getY(), pos.getZ(), range);
  }

  public void sendToAllAround(INetworkPacket packet, TileEntity tile) {

    sendToAllAround(packet, tile, DEFAULT_RANGE);
  }

  /**
   * Sends the specified packet to all entities tracking the given entity.
   *
   * @param packet The packet to send.
   * @param entity The entity whose tracking entities should receive the packet.
   */
  public void sendToAllTracking(INetworkPacket packet, Entity entity) {

    this.sendToAllTracking(new PacketInternal(this).setPacket(packet), entity);
  }

  public void sendToAllTracking(INetworkPacket packet, TargetPoint point) {

    this.sendToAllTracking(new PacketInternal(this).setPacket(packet), point);
  }

  public void sendToAllTracking(INetworkPacket packet, int dimension, BlockPos blockPos, double range) {

    sendToAllTracking(packet, new TargetPoint(dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ(), range));
  }

  public void sendToAllTracking(INetworkPacket packet, int dimension, double x, double y, double z, double range) {

    sendToAllTracking(packet, new TargetPoint(dimension, x, y, z, range));
  }

  /**
   * Sends the packet to everyone in a dimension.
   *
   * @param packet      The packet to send.
   * @param dimensionId The id of the dimension to send the packet to.
   */
  public void sendToDimension(INetworkPacket packet, int dimensionId) {

    this.sendToDimension(new PacketInternal(this).setPacket(packet), dimensionId);
  }

  public void sendToDimension(INetworkPacket packet, TileEntity tileEntity) {
    World world = tileEntity.getWorld();
    WorldProvider provider = world.provider;
    int dimensionId = provider.getDimension();

    sendToDimension(packet, dimensionId);
  }


  /**
   * Sends a packet to the server from a client.
   *
   * @param packet The packet to send.
   */
  public void sendToServer(INetworkPacket packet) {

    this.sendToServer(new PacketInternal(this).setPacket(packet));
  }

  // endregion


  public final class PacketInternal implements IMessage, IMessageHandler<PacketInternal, IMessage> {

    private NetworkThreadedWrapper channel;
    private INetworkPacket packet;

    private PacketInternal() {}

    public PacketInternal(NetworkThreadedWrapper channel) {

      this.channel = channel;
    }

    private PacketInternal setPacket(INetworkPacket packet) {
      this.packet = packet;
      return this;
    }

    @Override
    public IMessage onMessage(final PacketInternal packet, final MessageContext ctx) {

      NetworkUtils.queueTask(ctx.side, new Runner(ctx));
      return null;
    }

    @Override
    public int hashCode() {
      return this.packet.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
      if (obj instanceof PacketInternal wrapper) {
        return this.packet.equals(wrapper.packet);

      } else {
        return this.packet.equals(obj);
      }
    }

    @Override
    public String toString() {
      return this.packet.toString();
    }

    @Override
    public void fromBytes(ByteBuf buffer) {

      ClassBufSerializer.read(packet, buffer);
    }

    @Override
    public void toBytes(ByteBuf buffer) {

      ClassBufSerializer.write(packet, buffer);
    }

    private final class Runner implements Runnable {

      private final MessageContext context;

      public Runner(final MessageContext context) {
        this.context = context;
      }

      @Override
      public void run() {

        if (!packet.verify(context)) {
          return;
        }
        final INetworkPacket reply = packet.process(this.context);

        if (reply != null) {
          if (this.context.side == Side.CLIENT) {
            NetworkThreadedWrapper.this.sendToServer(reply);

          } else {
            final EntityPlayerMP player = this.context.getServerHandler().player;

            if (player != null) {
              NetworkThreadedWrapper.this.sendTo(reply, player);
            }
          }
        }
      }

    }

  }
}
