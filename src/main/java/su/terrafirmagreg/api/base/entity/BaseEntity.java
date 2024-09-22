package su.terrafirmagreg.api.base.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public abstract class BaseEntity extends Entity {

  public BaseEntity(World worldIn) {
    super(worldIn);
  }
}
