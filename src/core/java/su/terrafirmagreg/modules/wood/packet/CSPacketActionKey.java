package su.terrafirmagreg.modules.wood.packet;

import su.terrafirmagreg.framework.manager.packet.base.BasePacketServer;
import su.terrafirmagreg.modules.core.feature.pull.capability.CapabilityPull;
import su.terrafirmagreg.modules.wood.content.entity.spi.EntityWoodCart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class CSPacketActionKey extends BasePacketServer {

  @Override
  public void process(EntityPlayerMP player) {
    player.getServerWorld().addScheduledTask(() -> {
      List<EntityWoodCart> result = player.getServerWorld().getEntitiesWithinAABB(
        EntityWoodCart.class,
        player.getEntityBoundingBox().grow(3),
        entity -> entity != player.getRidingEntity() && entity.isEntityAlive()
      );
      if (!result.isEmpty()) {
        Entity target = player.isRiding() ? player.getRidingEntity() : player;
        EntityWoodCart closest = result.get(0);
        for (EntityWoodCart cart : result) {
          if (cart.getPulling() == target) {
            cart.resetPulling();
            return;
          }
          if (new Vec3d(cart.posX - player.posX, cart.posY - player.posY, cart.posZ - player.posZ).length()
              < new Vec3d(closest.posX - player.posX, closest.posY - player.posY, closest.posZ - player.posZ).length()) {
            closest = cart;
          }
        }
        if (closest.canBePulledBy(target)) {
          assert target != null;
          if (CapabilityPull.has(target)) {
            var drawn = CapabilityPull.get(target).getDrawn();
            if (drawn != null && drawn.getPulling() == target) {
              return;
            }
          }
          closest.setPulling(target);
        }
      }
    });
  }

}
