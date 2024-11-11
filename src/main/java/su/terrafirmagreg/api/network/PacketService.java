package su.terrafirmagreg.api.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import static net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class PacketService implements IPacketService {

  private static final int DEFAULT_RANGE = 64;

  private final ThreadedNetworkWrapper wrapper;

  public PacketService(ThreadedNetworkWrapper wrapper) {
    this.wrapper = wrapper;
  }

  @Override
  public Packet<?> getPacketFrom(IMessage message) {
    return this.wrapper.getPacketFrom(message);
  }

  @Override
  public void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
    this.wrapper.sendToAllAround(message, new TargetPoint(dimension, x, y, z, range));
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
    this.wrapper.sendToDimension(message, dimension);
  }

  @Override
  public void sendToAllTracking(IMessage message, Entity entity) {
    this.wrapper.sendToAllTracking(message, entity);
  }

  @Override
  public void sendToAll(IMessage message) {
    this.wrapper.sendToAll(message);
  }

  @Override
  public void sendTo(IMessage message, EntityPlayerMP player) {
    this.wrapper.sendTo(message, player);
  }

  @Override
  public void sendToServer(IMessage message) {
    this.wrapper.sendToServer(message);
  }
}
