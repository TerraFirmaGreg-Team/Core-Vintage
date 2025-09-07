package su.terrafirmagreg.modules.wood.packet;

import su.terrafirmagreg.framework.manager.packet.base.BasePacketClient;
import su.terrafirmagreg.modules.wood.content.entity.spi.EntityWoodCart;

import net.minecraft.client.Minecraft;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SCPacketDrawnUpdate extends BasePacketClient {

  public int pullingId;
  public int cartId;


  public SCPacketDrawnUpdate(int horseIn, int cartIn) {
    pullingId = horseIn;
    cartId = cartIn;
  }


  @Override
  public void process(Minecraft minecraft) {
    var world = minecraft.world;
    if (world != null) {
      var cart = world.getEntityByID(cartId);
      if (cart instanceof EntityWoodCart entityWoodCart) {
        if (pullingId < 0) {
          entityWoodCart.setPulling(null);
        } else {
          entityWoodCart.setPullingId(pullingId);
        }
      }
    }
  }
}
