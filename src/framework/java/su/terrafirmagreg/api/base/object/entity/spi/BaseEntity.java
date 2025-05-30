package su.terrafirmagreg.api.base.object.entity.spi;

import su.terrafirmagreg.api.base.object.entity.api.IEntitySettings;
import su.terrafirmagreg.api.data.ToolTipKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TranslatorUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;

import lombok.Getter;

public abstract class BaseEntity extends Entity {

  public BaseEntity(World worldIn) {
    super(worldIn);
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

      return TranslatorUtils.translateToLocal(ModUtils.localize(ToolTipKeys.ENTITY, string, "name"));
    }
  }


  @Getter
  public abstract static class BaseEntityType extends EntityEntry implements IEntitySettings {

    protected final Settings settings;

    public BaseEntityType(Settings settings) {
      super(settings.getEntityClass(), settings.getRegistryKey());

      this.settings = settings;
    }


  }
}
