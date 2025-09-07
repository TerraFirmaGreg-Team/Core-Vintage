package su.terrafirmagreg.framework.manager.content.base.entity.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TranslatorUtils;
import su.terrafirmagreg.framework.manager.content.base.entity.api.IEntityEntry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityEntry;

import lombok.Getter;

import java.util.Objects;

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

      return TranslatorUtils.translateToLocal(ModUtils.localize(LocalizeKeys.ENTITY, string, "name"));
    }
  }


  @Getter
  public abstract static class BaseEntityType extends EntityEntry implements IEntityEntry {

    protected final EntitySettings settings;

    public BaseEntityType(EntitySettings settings) {
      super(settings.getEntity(), settings.getRegistryKey());

      this.settings = settings;
    }

    public String getLocalizedName() {
      return TranslatorUtils.translateToLocal(this.getTranslationKey() + ".name");
    }


    public String getTranslationKey() {
      return ModUtils.localize(LocalizeKeys.ENTITY, this.getRegistryName());
    }

    @Override
    public String getName() {
      return ModUtils.localize(Objects.requireNonNull(this.getRegistryName()));
    }
  }
}
