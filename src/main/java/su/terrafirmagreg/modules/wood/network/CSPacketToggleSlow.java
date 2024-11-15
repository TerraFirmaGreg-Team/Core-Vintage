package su.terrafirmagreg.modules.wood.network;

import su.terrafirmagreg.api.base.packet.BasePacket;
import su.terrafirmagreg.modules.core.capabilities.pull.CapabilityPull;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodCart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CSPacketToggleSlow extends BasePacket<CSPacketToggleSlow> {

  public CSPacketToggleSlow() {
  }

//  @Override
//  public void fromBytes(ByteBuf buf) {
//  }
//
//  @Override
//  public void toBytes(ByteBuf buf) {
//  }

  @Override
  public IMessage handleMessage(MessageContext context) {
    EntityPlayerMP player = context.getServerHandler().player;
    if (player.isRiding()) {
      Entity ridden = player.getRidingEntity();
      if (ridden instanceof EntityLivingBase entityLivingBase && CapabilityPull.has(ridden)) {
        if (CapabilityPull.get(ridden).getDrawn() != null) {
          if (entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                              .hasModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER)) {
            entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                            .removeModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER);
          } else {
            entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                            .applyModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER);
          }
        }
      }
    }
    return null;
  }
}
