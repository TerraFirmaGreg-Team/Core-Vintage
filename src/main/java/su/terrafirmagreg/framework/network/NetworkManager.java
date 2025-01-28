package su.terrafirmagreg.framework.network;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.network.api.INetworkManager;
import su.terrafirmagreg.framework.network.api.packet.IPacket;
import su.terrafirmagreg.framework.network.spi.GuiHandler;
import su.terrafirmagreg.framework.network.spi.NetworkThreadedWrapper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.Getter;

import java.util.Map;

@Getter
public class NetworkManager implements INetworkManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(NetworkManager.class.getSimpleName());

  private static final Map<String, NetworkThreadedWrapper> NETWORK_WRAPPER_MAP = new Object2ObjectOpenHashMap<>();
  private static final Map<String, IGuiHandler> GUI_HANDLER_MAP = new Object2ObjectOpenHashMap<>();


  private static final int DEFAULT_RANGE = 64;

  private final IModule module;
  private final NetworkThreadedWrapper wrapper;
  private final IGuiHandler guiHandler;


  private NetworkManager(IModule module) {

    var moduleIdentifier = module.getIdentifier();

    this.module = module;
    this.wrapper = NETWORK_WRAPPER_MAP.computeIfAbsent(moduleIdentifier.getNamespace(), NetworkThreadedWrapper::new);
    this.guiHandler = GUI_HANDLER_MAP.computeIfAbsent(moduleIdentifier.getNamespace(), GuiHandler::new);


  }

  public static synchronized INetworkManager of(IModule module) {
    return new NetworkManager(module);
  }

  /**
   * Registers a new packet to the network handler.
   *
   * @param clazz The class of the packet. This class must implement IMessage and IMessageHandler!
   * @param side  The side that receives this packet.
   */
  @Override
  public <P extends IPacket<P>> void register(Side side, Class<P> clazz) {
    this.getWrapper().registerMessage(clazz, clazz, side);
  }


  @Override
  public void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
    this.getWrapper().sendToAllAround(message, new TargetPoint(dimension, x, y, z, range));
  }

  @Override
  public void sendToAllAround(IMessage message, int dimension, double x, double y, double z) {
    this.sendToAllAround(message, dimension, x, y, z, DEFAULT_RANGE);
  }

  @Override
  public void sendToAllAround(IMessage message, int dimension, BlockPos blockPos, double range) {
    this.sendToAllAround(message, dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ(), range);
  }

  @Override
  public void sendToAllAround(IMessage message, int dimension, BlockPos blockPos) {
    this.sendToAllAround(message, dimension, blockPos.getX(), blockPos.getY(), blockPos.getZ());
  }

  @Override
  public void sendToAllAround(IMessage message, TileEntity tileEntity, int range) {

    BlockPos pos = tileEntity.getPos();
    World world = tileEntity.getWorld();
    WorldProvider provider = world.provider;
    int dimension = provider.getDimension();
    this.sendToAllAround(message, dimension, pos.getX(), pos.getY(), pos.getZ(), range);
  }

  @Override
  public void sendToAllAround(IMessage message, TileEntity tileEntity) {
    this.sendToAllAround(message, tileEntity, DEFAULT_RANGE);
  }

  @Override
  public void sendToDimension(IMessage message, TileEntity tileEntity) {

    World world = tileEntity.getWorld();
    WorldProvider provider = world.provider;
    int dimension = provider.getDimension();
    this.sendToDimension(message, dimension);
  }

  @Override
  public void sendToDimension(IMessage message, int dimension) {
    this.getWrapper().sendToDimension(message, dimension);
  }

  @Override
  public void sendToAllTracking(IMessage message, Entity entity) {
    this.getWrapper().sendToAllTracking(message, entity);
  }

  @Override
  public void sendToAll(IMessage message) {
    this.getWrapper().sendToAll(message);
  }

  @Override
  public void sendTo(IMessage message, EntityPlayerMP player) {
    this.getWrapper().sendTo(message, player);
  }

  @Override
  public void sendToServer(IMessage message) {
    this.getWrapper().sendToServer(message);
  }

  public static class NoOp extends NetworkManager {

    private NoOp(IModule module) {
      super(module);
    }

    @Override
    public void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {

    }

    @Override
    public void sendToAllAround(IMessage message, int dimension, double x, double y, double z) {

    }

    @Override
    public void sendToAllAround(IMessage message, int dimension, BlockPos blockPos, double range) {

    }

    @Override
    public void sendToAllAround(IMessage message, int dimension, BlockPos blockPos) {

    }

    @Override
    public void sendToAllAround(IMessage message, TileEntity tileEntity, int range) {

    }

    @Override
    public void sendToAllAround(IMessage message, TileEntity tileEntity) {

    }

    @Override
    public void sendToDimension(IMessage message, TileEntity tileEntity) {

    }

    @Override
    public void sendToDimension(IMessage message, int dimension) {

    }

    @Override
    public void sendToAllTracking(IMessage message, Entity entity) {

    }

    @Override
    public void sendToAll(IMessage message) {

    }

    @Override
    public void sendTo(IMessage message, EntityPlayerMP player) {

    }

    @Override
    public void sendToServer(IMessage message) {

    }
  }
}
