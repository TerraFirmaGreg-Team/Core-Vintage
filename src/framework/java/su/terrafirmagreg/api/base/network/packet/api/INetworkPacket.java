package su.terrafirmagreg.api.base.network.packet.api;

import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.api.util.NetworkUtils;
import su.terrafirmagreg.framework.manager.network.NetworkManager;
import su.terrafirmagreg.framework.manager.network.spi.NetworkThreadedWrapper;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface INetworkPacket {

//  /**
//   * Used to write data from a Packet into a PacketBuffer.<br>
//   * <br>
//   * <p>
//   * This is the first step in sending a Packet to a different thread, and is done on the "sending" side.
//   *
//   * @param buffer The PacketBuffer to write Packet data to.
//   */
//  void write(PacketBuffer buffer);
//
//  /**
//   * Used to read data from a PacketBuffer into this Packet.<br>
//   * <br>
//   * <p>
//   * This is the next step of sending a Packet to a different thread, and is done on the "receiving" side.
//   *
//   * @param buffer The PacketBuffer to read Packet data from.
//   */
//  void read(PacketBuffer buffer);

  default NetworkThreadedWrapper getWrapper() {

    return NetworkManager.getChannel(this);
  }

  /**
   * Checks whether the received values are valid. If {@code false} is returned, the packet will be discarded.
   *
   * @return {@code true} if all received values are valid
   */
  default boolean verify(MessageContext context) {
    return true;
  }

  INetworkPacket process(MessageContext context);


  /**
   * Base interface for packet, which can be send from client to server
   */
  interface Server extends INetworkPacket {

    @Override
    default INetworkPacket process(MessageContext context) {
      NetworkUtils.queueTask(context, () -> context.getServerHandler().player, this::process);
      return null;
    }


    void process(EntityPlayerMP player);

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
  interface Client extends INetworkPacket {

    @Override
    default INetworkPacket process(MessageContext context) {
      NetworkUtils.queueTask(context, GameUtils::getMinecraft, this::process);
      return null;
    }

    @SideOnly(Side.CLIENT)
    void process(Minecraft minecraft);


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


    default void sendTo(EntityPlayerMP player) {

      getWrapper().sendTo(this, player);
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
