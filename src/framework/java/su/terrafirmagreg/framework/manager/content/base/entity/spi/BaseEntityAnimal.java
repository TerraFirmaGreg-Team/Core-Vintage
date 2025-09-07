package su.terrafirmagreg.framework.manager.content.base.entity.spi;

import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TranslatorUtils;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.world.World;

import lombok.Getter;

@Getter
public abstract class BaseEntityAnimal extends EntityAnimal {


  public BaseEntityAnimal(World worldIn) {
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
}
