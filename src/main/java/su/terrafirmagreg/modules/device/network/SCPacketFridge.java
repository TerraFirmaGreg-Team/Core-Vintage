package su.terrafirmagreg.modules.device.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.packet.BasePacket;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.object.tile.TileFridge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import org.jetbrains.annotations.NotNull;

public class SCPacketFridge extends BasePacket<SCPacketFridge> {

  private BlockPos pos;
  private float efficiency;

  public SCPacketFridge() {
  }

  public SCPacketFridge(@NotNull BlockPos pos, float efficiency) {
    this.pos = pos;
    this.efficiency = efficiency;
  }

//  @Override
//  public void fromBytes(ByteBuf buf) {
//    pos = BlockPos.fromLong(buf.readLong());
//    efficiency = buf.readFloat();
//  }
//
//  @Override
//  public void toBytes(ByteBuf buf) {
//    buf.writeLong(pos.toLong());
//    buf.writeFloat(efficiency);
//  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(context);
    if (player != null) {
      World world = player.getEntityWorld();
      TileUtils.getTile(world, pos, TileFridge.class).ifPresent(tile -> tile.updateClient(efficiency));
    }
    return null;
  }
}
