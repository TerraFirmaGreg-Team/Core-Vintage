package su.terrafirmagreg.modules.wood.packet;

import su.terrafirmagreg.framework.manager.packet.base.BasePacketServer;
import su.terrafirmagreg.modules.core.feature.pull.capability.CapabilityPull;
import su.terrafirmagreg.modules.wood.object.entity.spi.EntityWoodCart;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CSPacketToggleSlow extends BasePacketServer {

  @Override
  public void process(EntityPlayerMP player) {
    if (player.isRiding()) {
      Entity ridden = player.getRidingEntity();
      if (ridden instanceof EntityLivingBase entityLivingBase && CapabilityPull.has(ridden)) {
        if (CapabilityPull.get(ridden).getDrawn() != null) {
          if (entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).hasModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER)) {
            entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER);
          } else {
            entityLivingBase.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(EntityWoodCart.PULL_SLOWLY_MODIFIER);
          }
        }
      }
    }
  }

}
