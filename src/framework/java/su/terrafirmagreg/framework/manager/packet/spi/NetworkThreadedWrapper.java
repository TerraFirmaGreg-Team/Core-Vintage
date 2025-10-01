package su.terrafirmagreg.framework.manager.packet.spi;

import su.terrafirmagreg.api.library.IdSupplier;
import su.terrafirmagreg.api.util.BufUtils;
import su.terrafirmagreg.api.util.ClassUtils;
import su.terrafirmagreg.api.util.NetworkUtils;
import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.framework.manager.packet.api.IPacketEntry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Map;

@Getter
public class NetworkThreadedWrapper {

  private static final Map<String, NetworkThreadedWrapper> WRAPPER_MAP = new Object2ObjectOpenHashMap<>();
  private static final Map<Class<? extends IPacketEntry>, NetworkThreadedWrapper> ALL_PACKET_MAP = new Object2ObjectOpenHashMap<>();


  private final IntIdentityHashBiMap<Class<? extends IPacketEntry>> packetMap;
  private final String netId;
  private final SimpleNetworkWrapper channel;
  private final IdSupplier idSupplier;

  public NetworkThreadedWrapper(String netId) {

    this.netId = netId;
    this.channel = NetworkRegistry.INSTANCE.newSimpleChannel(netId);
    this.idSupplier = new IdSupplier();
    this.packetMap = new IntIdentityHashBiMap<>(10);

    this.channel.registerMessage(new PacketWrapper(this), PacketWrapper.class, 0, Side.SERVER);
    this.channel.registerMessage(new PacketWrapper(this), PacketWrapper.class, 1, Side.CLIENT);

  }

  // ContainerId : ModuleId
  public static NetworkThreadedWrapper of(ResourceLocation netId) {
    NetworkUtils.isValidChannel(netId);
    return WRAPPER_MAP.computeIfAbsent(netId.toString(), NetworkThreadedWrapper::new);
  }

  public static NetworkThreadedWrapper of(String netId) {
    NetworkUtils.isValidChannel(netId);
    return WRAPPER_MAP.computeIfAbsent(netId, NetworkThreadedWrapper::new);
  }

  public static @NotNull NetworkThreadedWrapper getChannel(IPacketEntry packet) {
    var packetClass = packet.getClass();
    var wrapper = ALL_PACKET_MAP.get(packetClass);
    if (wrapper == null) {
      throw new RuntimeException("Trying to send unregistered network packet: " + packetClass.getSimpleName());
    }
    return wrapper;
  }

  public <P extends IPacketEntry> void registerPacket(P packet) {

    registerPacket(packet.getClass());
  }

  public <P extends IPacketEntry> void registerPacket(Class<P> packetClass) {
    ALL_PACKET_MAP.put(packetClass, this);
    packetMap.put(packetClass, idSupplier.getAndIncrement());
  }

  public <P extends IPacketEntry> int getPacketId(Class<P> packetClass) {

    return packetMap.getId(packetClass);
  }

  public Class<? extends IPacketEntry> getPacketClass(int packetId) {

    return packetMap.get(packetId);
  }


  /**
   * Retrieves the packet corresponding to the given packet from the server parent.
   *
   * @param packet The packet for which to retrieve the packet.
   * @return The packet corresponding to the packet.
   */
  public Packet<?> getPacketFrom(IPacketEntry packet) {

    return this.channel.getPacketFrom(new PacketWrapper(this).setPacket(packet));
  }

  // region ====== Send Messages ======


  /**
   * Sends the packet to all players.
   *
   * @param packet The packet to send.
   */
  public void sendToAll(IPacketEntry packet) {

    this.channel.sendToAll(new PacketWrapper(this).setPacket(packet));
  }

  /**
   * Sends the packet to a specific player.
   *
   * @param packet The packet to send.
   * @param player The player to receive the packet.
   */
  public void sendTo(IPacketEntry packet, EntityPlayerMP player) {

    this.channel.sendTo(new PacketWrapper(this).setPacket(packet), player);
  }

  /**
   * Sends the specified packet packet to all players around the given position in the world.
   *
   * @param packet The packet packet to send.
   * @param pos    The position around which to send the packet packet.
   * @param world  The world in which to send the packet packet.
   */
  public void sendTo(IPacketEntry packet, BlockPos pos, World world) {
    if (!(world instanceof WorldServer worldServer)) {
      return;
    }

    PlayerChunkMap playerManager = worldServer.getPlayerChunkMap();

    int chunkX = pos.getX() >> 4;
    int chunkZ = pos.getZ() >> 4;

    for (Object playerObj : world.playerEntities) {
      if (playerObj instanceof EntityPlayerMP player) {

        if (playerManager.isPlayerWatchingChunk(player, chunkX, chunkZ)) {
          this.sendTo(packet, player);
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
  public void sendToAllAround(IPacketEntry packet, TargetPoint point) {

    this.channel.sendToAllAround(new PacketWrapper(this).setPacket(packet), point);
  }

  /**
   * Sends the packet to everyone near a point.
   *
   * @param packet The packet to send.
   * @param world  The world to send the packet to.
   * @param pos    The position to send the packet to.
   * @param range  The range of the packet.
   */
  public void sendToAllAround(IPacketEntry packet, World world, BlockPos pos, double range) {

    this.sendToAllAround(packet, new TargetPoint(world.provider.getDimension(), pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, range));
  }

  public void sendToAllAround(IPacketEntry packet, World world, BlockPos pos) {

    this.sendToAllAround(packet, world, pos, NetworkUtils.DEFAULT_RANGE);
  }

  public void sendToAllAround(IPacketEntry packet, int dimension, BlockPos blockPos, double range) {

    this.sendToAllAround(packet, dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ(), range);
  }

  public void sendToAllAround(IPacketEntry packet, int dimension, BlockPos blockPos) {

    this.sendToAllAround(packet, dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ(), NetworkUtils.DEFAULT_RANGE);
  }

  public void sendToAllAround(IPacketEntry packet, int dimension, double x, double y, double z, double range) {

    this.sendToAllAround(packet, new TargetPoint(dimension, x, y, z, range));
  }

  public void sendToAllAround(IPacketEntry packet, int dimension, double x, double y, double z) {

    this.sendToAllAround(packet, dimension, x, y, z, NetworkUtils.DEFAULT_RANGE);
  }

  public void sendToAllAround(IPacketEntry packet, TileEntity tile, int range) {
    BlockPos pos = tile.getPos();
    World world = tile.getWorld();
    WorldProvider provider = world.provider;
    int dimension = provider.getDimension();
    this.sendToAllAround(packet, dimension, pos.getX(), pos.getY(), pos.getZ(), range);
  }

  public void sendToAllAround(IPacketEntry packet, TileEntity tile) {

    this.sendToAllAround(packet, tile, NetworkUtils.DEFAULT_RANGE);
  }

  /**
   * Sends the specified packet to all entities tracking the given entity.
   *
   * @param packet The packet to send.
   * @param entity The entity whose tracking entities should receive the packet.
   */
  public void sendToAllTracking(IPacketEntry packet, Entity entity) {

    this.channel.sendToAllTracking(new PacketWrapper(this).setPacket(packet), entity);
  }

  public void sendToAllTracking(IPacketEntry packet, TargetPoint point) {

    this.channel.sendToAllTracking(new PacketWrapper(this).setPacket(packet), point);
  }

  public void sendToAllTracking(IPacketEntry packet, int dimension, BlockPos blockPos, double range) {

    this.sendToAllTracking(packet, new TargetPoint(dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ(), range));
  }

  public void sendToAllTracking(IPacketEntry packet, int dimension, double x, double y, double z, double range) {

    this.sendToAllTracking(packet, new TargetPoint(dimension, x, y, z, range));
  }

  /**
   * Sends the packet to everyone in a dimension.
   *
   * @param packet      The packet to send.
   * @param dimensionId The id of the dimension to send the packet to.
   */
  public void sendToDimension(IPacketEntry packet, int dimensionId) {

    this.channel.sendToDimension(new PacketWrapper(this).setPacket(packet), dimensionId);
  }

  public void sendToDimension(IPacketEntry packet, TileEntity tileEntity) {
    World world = tileEntity.getWorld();
    WorldProvider provider = world.provider;
    int dimensionId = provider.getDimension();

    this.sendToDimension(packet, dimensionId);
  }


  /**
   * Sends a packet to the server from a client.
   *
   * @param packet The packet to send.
   */
  public void sendToServer(IPacketEntry packet) {

    this.channel.sendToServer(new PacketWrapper(this).setPacket(packet));
  }

  // endregion

  private void write(IPacketEntry packet, PacketBuffer buffer) {
    // assume the packet has already been checked for registration here
    int index = this.getPacketId(packet.getClass());
    buffer.writeInt(index);
//    packet.write(buffer);
    ClassUtils.processFields(packet, (obj, field) -> BufUtils.writeField(obj, field, buffer));
  }

  private IPacketEntry read(PacketBuffer buffer) {
    int index = buffer.readInt();

    var clazz = this.getPacketClass(index);
    IPacketEntry packet = this.instantiate(clazz);
//    packet.read(buffer);
    ClassUtils.processFields(packet, (obj, field) -> BufUtils.readField(obj, field, buffer));
    return packet;
  }

  private IPacketEntry instantiate(Class<? extends IPacketEntry> packetClass) {
    try {
      return packetClass.getDeclaredConstructor().newInstance();
    } catch (Throwable e) {
      FrameworkLogger.LOGGER.error("Failed to instanciate " + packetClass);
      throw new RuntimeException(e);
    }
  }

  private void handle(IPacketEntry packet, MessageContext context) {
    if (packet.verify(context)) {
      packet.process(context);
    }
  }

  /**
   * Don't access this, this may change between versions and is only public because the {@link SimpleNetworkWrapper} requires it to be
   */
  public static class PacketWrapper implements IMessage, IMessageHandler<PacketWrapper, IMessage> {

    private NetworkThreadedWrapper channel;
    private IPacketEntry packet;

    public PacketWrapper() {
    }

    public PacketWrapper(NetworkThreadedWrapper channel) {
      this.channel = channel;
    }

    private PacketWrapper setPacket(IPacketEntry packet) {
      this.packet = packet;
      return this;
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
      PacketBuffer packetBuffer = new PacketBuffer(buffer);
      this.channel = WRAPPER_MAP.get(packetBuffer.readString(32767));
      if (this.channel == null) {throw new IllegalStateException("Couldn't find received channel name!");}

      this.packet = this.channel.read(packetBuffer);
    }

    @Override
    public void toBytes(ByteBuf buffer) {
      PacketBuffer packetBuffer = new PacketBuffer(buffer);
      packetBuffer.writeString(this.channel.netId);

      this.channel.write(this.packet, packetBuffer);
    }

    @Override
    public IMessage onMessage(PacketWrapper message, MessageContext context) {
      this.channel.handle(message.packet, context);
      return null;
    }
  }

}
