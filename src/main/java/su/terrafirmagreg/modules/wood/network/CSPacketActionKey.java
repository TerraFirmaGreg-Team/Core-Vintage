package su.terrafirmagreg.modules.wood.network;

import su.terrafirmagreg.modules.core.capabilities.pull.CapabilityPull;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodCart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


import io.netty.buffer.ByteBuf;

import java.util.List;

public class CSPacketActionKey implements IMessage, IMessageHandler<CSPacketActionKey, IMessage> {

  public CSPacketActionKey() {
  }

  @Override
  public void fromBytes(ByteBuf buf) {
  }

  @Override
  public void toBytes(ByteBuf buf) {
  }

  @Override
  public IMessage onMessage(CSPacketActionKey message, MessageContext ctx) {
    EntityPlayerMP sender = ctx.getServerHandler().player;
    sender.getServerWorld().addScheduledTask(() -> {
      List<EntityWoodCart> result = sender.getServerWorld()
          .getEntitiesWithinAABB(EntityWoodCart.class, sender.getEntityBoundingBox()
              .grow(3), entity -> entity != sender.getRidingEntity() && entity.isEntityAlive());
      if (!result.isEmpty()) {
        Entity target = sender.isRiding() ? sender.getRidingEntity() : sender;
        EntityWoodCart closest = result.get(0);
        for (EntityWoodCart cart : result) {
          if (cart.getPulling() == target) {
            cart.setPulling(null);
            return;
          }
          if (new Vec3d(cart.posX - sender.posX, cart.posY - sender.posY,
              cart.posZ - sender.posZ).length() <
              new Vec3d(closest.posX - sender.posX, closest.posY - sender.posY,
                  closest.posZ - sender.posZ).length()) {
            closest = cart;
          }
        }
        if (closest.canBePulledBy(target)) {
          assert target != null;
          if (CapabilityPull.has(target)) {
            EntityWoodCart drawn = CapabilityPull.get(target).getDrawn();
            if (drawn != null && drawn.getPulling() == target) {
              return;
            }
          }
          closest.setPulling(target);
        }
      }
    });
    return null;
  }
}
