package su.terrafirmagreg.modules.wood.object.entity.ai;


import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.modules.core.feature.pull.capability.CapabilityPull;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

import java.util.Optional;

public class EntityWoodAIPullCart extends EntityAIBase {

  private final EntityLiving living;

  public EntityWoodAIPullCart(EntityLiving livingIn) {
    this.living = livingIn;
    this.setMutexBits(3);
  }

  @Override
  public boolean shouldExecute() {

    return CapabilityUtils.getOptional(this.living, CapabilityPull.CAPABILITY)
      .flatMap(cap -> Optional.ofNullable(cap.getDrawn()))
      .isPresent();
  }

}
