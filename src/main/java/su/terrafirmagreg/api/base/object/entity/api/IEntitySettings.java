package su.terrafirmagreg.api.base.object.entity.api;

import su.terrafirmagreg.api.base.object.entity.api.IEntitySettings.Settings;
import su.terrafirmagreg.api.library.IBaseSettings;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import lombok.Getter;

public interface IEntitySettings extends IBaseSettings<Settings> {

  @Getter
  class Settings extends BaseSettings<Settings> {

    final EntityEntryBuilder<Entity> builder;

    Class<? extends Entity> entity;
    IRenderFactory<? super Entity> renderFactory;


    protected Settings() {
      this.builder = EntityEntryBuilder.create();
    }

    public static Settings of() {
      return new Settings();
    }

    public Settings egg(int primaryColor, int secondaryColor) {
      this.builder.egg(primaryColor, secondaryColor);
      return this;
    }

    public <E extends Entity> Settings render(IRenderFactory<? super Entity> renderFactory) {
      this.renderFactory = renderFactory;
      return this;
    }

    public Settings tracker(int range, final int updateFrequency, final boolean sendVelocityUpdates) {
      this.builder.tracker(range, updateFrequency, sendVelocityUpdates);
      return this;
    }

    public <T extends Entity> Settings entity(Class<T> entityClass, IRenderFactory<? super T> renderFactory) {

    }

    public <E extends Entity> Settings entity(Class<E> entClass) {
      this.builder.entity(entClass);
      return this;
    }


  }
}
