package su.terrafirmagreg.modules.core.object.potion;

import su.terrafirmagreg.api.base.effects.BasePotion;
import su.terrafirmagreg.data.DamageSources;
import su.terrafirmagreg.data.MathConstants;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class PotionSwarm extends BasePotion {

  public PotionSwarm() {
    super(true, 0xffff1a);
    formatTexture("swarm");
  }

  @Override
  public void performEffect(EntityLivingBase entity, int amplifier) {
    World world = entity.getEntityWorld();
    if (world.isRemote) {
      BlockPos pos = entity.getPosition();
      Random rand = MathConstants.RNG;
      double x = pos.getX() + 0.5;
      double y = pos.getY() + 0.5;
      double z = pos.getZ() + 0.5;
      for (int i = 0; i < 3 + rand.nextInt(4); i++) {
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + rand.nextFloat() - rand.nextFloat(),
                            y + rand.nextFloat(),
                            z + rand.nextFloat() - rand.nextFloat(),
                            0.5 * (rand.nextFloat() - rand.nextFloat()),
                            0.5 * (rand.nextFloat() - rand.nextFloat()),
                            0.5 * (rand.nextFloat() - rand.nextFloat()));
      }
    } else {
      if (!entity.isWet()) {
        entity.attackEntityFrom(DamageSources.SWARM, 1.0f + amplifier);
      }
    }

  }
}
