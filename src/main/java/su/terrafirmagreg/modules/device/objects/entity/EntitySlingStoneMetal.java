package su.terrafirmagreg.modules.device.objects.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntitySlingStoneMetal extends EntitySlingStone {

  public EntitySlingStoneMetal(World worldIn) {
    super(worldIn);
  }

  public EntitySlingStoneMetal(World worldIn, EntityLivingBase throwerIn, int power) {
    super(worldIn, throwerIn, power);
  }

}
