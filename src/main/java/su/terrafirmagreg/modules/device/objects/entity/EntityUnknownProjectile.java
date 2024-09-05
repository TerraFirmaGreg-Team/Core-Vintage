package su.terrafirmagreg.modules.device.objects.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityUnknownProjectile extends EntitySlingStone {

  public EntityUnknownProjectile(World worldIn) {
    super(worldIn);
  }

  public EntityUnknownProjectile(World worldIn, EntityLivingBase throwerIn, int power) {
    super(worldIn, throwerIn, power);
  }

}
