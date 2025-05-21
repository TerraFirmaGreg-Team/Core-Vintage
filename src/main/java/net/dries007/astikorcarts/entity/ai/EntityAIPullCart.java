package net.dries007.astikorcarts.entity.ai;

import su.terrafirmagreg.modules.core.feature.pull.capability.CapabilityPull;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIPullCart extends EntityAIBase {

  private final EntityLiving living;

  public EntityAIPullCart(EntityLiving livingIn) {
    this.living = livingIn;
    this.setMutexBits(3);
  }

  @Override
  public boolean shouldExecute() {
    if (this.living.hasCapability(CapabilityPull.CAPABILITY, null)) {
      return this.living.getCapability(CapabilityPull.CAPABILITY, null).getDrawn() != null;
    }
    return false;
  }

}
