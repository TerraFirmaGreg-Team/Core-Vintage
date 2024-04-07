package su.terrafirmagreg.api.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public interface IPacketService {

    Packet<?> getPacketFrom(IMessage message);

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
