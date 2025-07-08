package su.terrafirmagreg.framework.manager.registry.base.entity.api;

import su.terrafirmagreg.api.util.EntityUtils;
import su.terrafirmagreg.api.util.EntityUtils.EggInfo;
import su.terrafirmagreg.api.util.EntityUtils.SpawnInfo;
import su.terrafirmagreg.api.util.EntityUtils.UpdateInfo;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.entity.api.IEntityEntry.Settings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.registry.EntityEntry;

import lombok.Getter;

import static com.google.common.base.Preconditions.checkNotNull;

public interface IEntityEntry extends IRegistryEntry<Settings, EntityEntry> {

  @Override
  default void postRegister() {
    var settings = getSettings();

    EntityUtils.addUpdateInfo(asEntry(), settings.getUpdateInfo());
    EntityUtils.addEggInfo(asEntry(), settings.getEggInfo());
    EntityUtils.addSpawnInfo(asEntry(), settings.getSpawnInfo());
  }

  @Getter
  class Settings extends RegistrySettings<Settings> {

    Class<? extends Entity> entity;
    SpawnInfo spawnInfo;
    UpdateInfo updateInfo;
    EggInfo eggInfo;


    protected Settings() {
      this.updateInfo = new UpdateInfo();
    }

    public static Settings of() {
      return new Settings();
    }

    public Settings egg(int primaryColor, int secondaryColor) {
      this.eggInfo = new EggInfo(primaryColor, secondaryColor);
      return this;
    }

    public Settings updateInfo(int range, int updateFrequency, boolean sendVelocityUpdates) {
      this.updateInfo = new UpdateInfo(range, updateFrequency, sendVelocityUpdates);
      return this;
    }

    public Settings spawn(int weightedProb, int min, int max, EnumCreatureType typeOfCreature, Biome... biomes) {
      this.spawnInfo = new SpawnInfo(weightedProb, min, max, typeOfCreature, biomes);
      return this;
    }


    public <T extends Entity> Settings entity(Class<T> entity, IRenderFactory<? super T> renderFactory) {
      this.entity = checkNotNull(entity, "entity class");
      ModelUtils.entity(entity, renderFactory);
      return this;
    }

    public <E extends Entity> Settings entity(Class<E> entity) {
      this.entity = checkNotNull(entity, "entity class");
      return this;
    }


  }
}
