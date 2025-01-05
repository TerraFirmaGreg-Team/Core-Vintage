package su.terrafirmagreg.api.base.object.entity.spi;

import su.terrafirmagreg.api.base.object.entity.api.IEntitySettings;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
public abstract class BaseEntity extends Entity implements IEntitySettings {

  protected final Settings settings;

  public BaseEntity(World worldIn) {
    super(worldIn);

    this.settings = Settings.of();
  }
}
