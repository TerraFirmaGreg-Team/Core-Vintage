package su.terrafirmagreg.framework.manager.packet.base;


import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.api.util.NetworkUtils;

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

/**
 * Base class for packet, which can be send from server to client
 */
public abstract class BasePacketClient extends BasePacket {

  @Override
  public IMessage process(MessageContext context) {
    NetworkUtils.queueTask(context, GameUtils::getMinecraft, this::process);
    return null;
  }

  @SideOnly(Side.CLIENT)
  public void process(Minecraft minecraft) {

  }


  public Packet<?> getPacketFrom() {

    return getWrapper().getPacketFrom(this);
  }

  // Send To All

  /**
   * Use it for send packet instance to all players
   */
  public void sendToAll() {

    getWrapper().sendToAll(this);
  }

  // Send To


  public void sendTo(EntityPlayer player) {
    if (player instanceof EntityPlayerMP playerMP) {
      getWrapper().sendTo(this, playerMP);
    }

  }

  public void sendTo(EntityLivingBase player) {
    if (player instanceof EntityPlayerMP playerMP) {
      getWrapper().sendTo(this, playerMP);
    }
  }


  public void sendTo(BlockPos pos, World world) {

    getWrapper().sendTo(this, pos, world);
  }

  // Send To All Around


  public void sendToAllAround(int dimension, double x, double y, double z, double range) {

    getWrapper().sendToAllAround(this, dimension, x, y, z, range);
  }


  public void sendToAllAround(int dimension, double x, double y, double z) {

    getWrapper().sendToAllAround(this, dimension, x, y, z);
  }


  public void sendToAllAround(int dimension, BlockPos blockPos, double range) {

    getWrapper().sendToAllAround(this, dimension, blockPos, range);
  }


  public void sendToAllAround(int dimension, BlockPos blockPos) {

    getWrapper().sendToAllAround(this, dimension, blockPos);
  }


  public void sendToAllAround(World world, BlockPos pos, double range) {

    getWrapper().sendToAllAround(this, world, pos, range);
  }


  public void sendToAllAround(World world, BlockPos pos) {

    getWrapper().sendToAllAround(this, world, pos);
  }


  public void sendToAllAround(TileEntity tileEntity, int range) {

    getWrapper().sendToAllAround(this, tileEntity, range);
  }


  public void sendToAllAround(TileEntity tileEntity) {

    getWrapper().sendToAllAround(this, tileEntity);
  }

  // Send To All Tracking


  public void sendToAllTracking(Entity entity) {

    getWrapper().sendToAllTracking(this, entity);
  }


  public void sendToAllTracking(TargetPoint point) {

    getWrapper().sendToAllTracking(this, point);
  }


  public void sendToAllTracking(int dimension, BlockPos blockPos, double range) {

    getWrapper().sendToAllTracking(this, dimension, blockPos, range);
  }


  public void sendToAllTracking(int dimension, double x, double y, double z, double range) {

    getWrapper().sendToAllTracking(this, dimension, x, y, z, range);
  }

  // Send To Dimension


  public void sendToDimension(TileEntity tileEntity) {

    getWrapper().sendToDimension(this, tileEntity);
  }


  public void sendToDimension(int dimension) {

    getWrapper().sendToDimension(this, dimension);
  }
}
