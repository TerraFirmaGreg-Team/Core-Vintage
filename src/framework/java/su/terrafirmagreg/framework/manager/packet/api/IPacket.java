package su.terrafirmagreg.framework.manager.packet.api;

import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.api.util.NetworkUtils;
import su.terrafirmagreg.framework.manager.packet.PacketManager;
import su.terrafirmagreg.framework.manager.packet.spi.NetworkThreadedWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IPacket {


  /**
   * Checks whether the received values are valid. If {@code false} is returned, the packet will be discarded.
   *
   * @return {@code true} if all received values are valid
   */
  default boolean verify(MessageContext context) {
    return true;
  }

  /**
   * Called when the message is received and handled. This is where you process the message.
   *
   * @param context The context for the message.
   * @return A message to send as a response.
   */
  IMessage process(MessageContext context);


  default NetworkThreadedWrapper getWrapper() {

    return PacketManager.getChannel(this);
  }


  /**
   * Base interface for packet, which can be send from client to server
   */
  interface Server extends IPacket {

    @Override
    default IMessage process(MessageContext context) {
      NetworkUtils.queueTask(context, () -> context.getServerHandler().player, this::process);
      return null;
    }


    default void process(EntityPlayerMP player) {

    }

    // Send To Server

    /**
     * Use it for send packet instance to server
     */
    default void sendToServer() {

      getWrapper().sendToServer(this);
    }
  }

  /**
   * Base interface for packet, which can be send from server to client
   */
  interface Client extends IPacket {

    @Override
    default IMessage process(MessageContext context) {
      NetworkUtils.queueTask(context, GameUtils::getMinecraft, this::process);
      return null;
    }

    @SideOnly(Side.CLIENT)
    default void process(Minecraft minecraft) {

    }


    default Packet<?> getPacketFrom() {

      return getWrapper().getPacketFrom(this);
    }

    // Send To All

    /**
     * Use it for send packet instance to all players
     */
    default void sendToAll() {

      getWrapper().sendToAll(this);
    }

    // Send To


    default void sendTo(EntityPlayer player) {
      if (player instanceof EntityPlayerMP playerMP) {
        getWrapper().sendTo(this, playerMP);
      }

    }

    default void sendTo(EntityLivingBase player) {
      if (player instanceof EntityPlayerMP playerMP) {
        getWrapper().sendTo(this, playerMP);
      }
    }


    default void sendTo(BlockPos pos, World world) {

      getWrapper().sendTo(this, pos, world);
    }

    // Send To All Around


    default void sendToAllAround(int dimension, double x, double y, double z, double range) {

      getWrapper().sendToAllAround(this, dimension, x, y, z, range);
    }


    default void sendToAllAround(int dimension, double x, double y, double z) {

      getWrapper().sendToAllAround(this, dimension, x, y, z);
    }


    default void sendToAllAround(int dimension, BlockPos blockPos, double range) {

      getWrapper().sendToAllAround(this, dimension, blockPos, range);
    }


    default void sendToAllAround(int dimension, BlockPos blockPos) {

      getWrapper().sendToAllAround(this, dimension, blockPos);
    }


    default void sendToAllAround(World world, BlockPos pos, double range) {

      getWrapper().sendToAllAround(this, world, pos, range);
    }


    default void sendToAllAround(World world, BlockPos pos) {

      getWrapper().sendToAllAround(this, world, pos);
    }


    default void sendToAllAround(TileEntity tileEntity, int range) {

      getWrapper().sendToAllAround(this, tileEntity, range);
    }


    default void sendToAllAround(TileEntity tileEntity) {

      getWrapper().sendToAllAround(this, tileEntity);
    }

    // Send To All Tracking


    default void sendToAllTracking(Entity entity) {

      getWrapper().sendToAllTracking(this, entity);
    }


    default void sendToAllTracking(TargetPoint point) {

      getWrapper().sendToAllTracking(this, point);
    }


    default void sendToAllTracking(int dimension, BlockPos blockPos, double range) {

      getWrapper().sendToAllTracking(this, dimension, blockPos, range);
    }


    default void sendToAllTracking(int dimension, double x, double y, double z, double range) {

      getWrapper().sendToAllTracking(this, dimension, x, y, z, range);
    }

    // Send To Dimension


    default void sendToDimension(TileEntity tileEntity) {

      getWrapper().sendToDimension(this, tileEntity);
    }


    default void sendToDimension(int dimension) {

      getWrapper().sendToDimension(this, dimension);
    }

  }


}
