package su.terrafirmagreg.modules.food.network;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.packet.BasePacket;
import su.terrafirmagreg.modules.core.capabilities.food.spi.Nutrient;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SCPacketFoodStatsUpdate extends BasePacket<SCPacketFoodStatsUpdate> {

  private final float[] nutrients;
  private float thirst;

  @SuppressWarnings("unused")
  @Deprecated
  public SCPacketFoodStatsUpdate() {
    this.nutrients = new float[Nutrient.TOTAL];
  }

  public SCPacketFoodStatsUpdate(float[] nutrients, float thirst) {
    this.nutrients = nutrients;
    this.thirst = thirst;
  }

//  @Override
//  public void fromBytes(ByteBuf buf) {
//    thirst = buf.readFloat();
//    for (int i = 0; i < nutrients.length; i++) {
//      nutrients[i] = buf.readFloat();
//    }
//  }
//
//  @Override
//  public void toBytes(ByteBuf buf) {
//    buf.writeFloat(thirst);
//    for (float f : nutrients) {
//      buf.writeFloat(f);
//    }
//  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    TerraFirmaGreg.getProxy().getThreadListener(context).addScheduledTask(() -> {
      final EntityPlayer player = TerraFirmaGreg.getProxy().getPlayer(context);
      if (player != null) {
        FoodStats foodStats = player.getFoodStats();
        if (foodStats instanceof FoodStatsTFC foodStatsTFC) {
          foodStatsTFC.onReceivePacket(nutrients, thirst);
        }
      }
    });
    return null;
  }
}
