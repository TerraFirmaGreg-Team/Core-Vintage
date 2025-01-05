package su.terrafirmagreg.framework.network.api;

import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.network.api.packet.IPacket;
import su.terrafirmagreg.framework.network.spi.NetworkThreadedWrapper;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.relauncher.Side;

public interface INetworkManager {

  IModule getModule();

  NetworkThreadedWrapper getWrapper();

  <P extends IPacket<P>> void register(Side side, Class<P> clazz);

  void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range);

  void sendToAllAround(IMessage message, int dimension, double x, double y, double z);

  void sendToAllAround(IMessage message, int dimension, BlockPos blockPos, double range);

  void sendToAllAround(IMessage message, int dimension, BlockPos blockPos);

  void sendToAllAround(IMessage message, TileEntity tileEntity, int range);

  void sendToAllAround(IMessage message, TileEntity tileEntity);

  void sendToDimension(IMessage message, TileEntity tileEntity);

  void sendToDimension(IMessage message, int dimension);

  void sendToAllTracking(IMessage message, Entity entity);

  void sendToAll(IMessage message);

  void sendTo(IMessage message, EntityPlayerMP player);

  void sendToServer(IMessage message);
}
