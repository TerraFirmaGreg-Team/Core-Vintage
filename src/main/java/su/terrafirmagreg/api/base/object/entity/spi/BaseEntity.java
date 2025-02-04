package su.terrafirmagreg.api.base.object.entity.spi;

import su.terrafirmagreg.api.base.object.entity.api.IEntitySettings;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TranslatorUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
public abstract class BaseEntity extends Entity implements IEntitySettings {

  protected final Settings settings;

  public BaseEntity(World worldIn) {
    super(worldIn);

    this.settings = Settings.of();
  }

  @Override
  public String getName() {
    if (this.hasCustomName()) {
      return this.getCustomNameTag();
    } else {
      String string = EntityList.getEntityString(this);

      if (string == null) {
        string = "generic";
      }

      return TranslatorUtils.translateToLocal(ModUtils.localize("entity", string, "name"));
    }
  }
}
