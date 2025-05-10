package su.terrafirmagreg.api.base.object.entity.api;

import su.terrafirmagreg.api.base.IBaseSettings;
import su.terrafirmagreg.api.base.object.entity.api.IEntitySettings.Settings;

import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import lombok.Getter;

public interface IEntitySettings extends IBaseSettings<Settings, EntityEntry> {

  @Getter
  class Settings extends BaseSettings<Settings> {

    final EntityEntryBuilder<Entity> builder;

    Class<? extends Entity> entityClass;


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

    public Settings tracker(int range, final int updateFrequency, final boolean sendVelocityUpdates) {
      this.builder.tracker(range, updateFrequency, sendVelocityUpdates);
      return this;
    }


    public <T extends Entity> Settings entity(Class<T> entityClass, IRenderFactory<? super T> renderFactory) {
      entity(entityClass);
      if (renderFactory != null) {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
      }
      return this;
    }

    public <E extends Entity> Settings entity(Class<E> entityClass) {
      this.entityClass = entityClass;
      this.builder.entity(entityClass);
      this.builder.tracker(64, 1, true);
      return this;
    }


  }
}
