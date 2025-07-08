package su.terrafirmagreg.modules.wood.packet;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.framework.manager.packet.base.BasePacketServer;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CSPacketOpenCartGui extends BasePacketServer {

  public int cartId;


  public CSPacketOpenCartGui(int cartIdIn) {
    cartId = cartIdIn;
  }

  @Override
  public void process(EntityPlayerMP player) {
    GuiHandler.openGui(player.world, new BlockPos(cartId, 0, 0), player);
  }


}
