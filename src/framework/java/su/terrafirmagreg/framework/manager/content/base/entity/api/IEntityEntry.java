package su.terrafirmagreg.framework.manager.content.base.entity.api;

import su.terrafirmagreg.api.util.EntityUtils;
import su.terrafirmagreg.api.util.EntityUtils.EggInfo;
import su.terrafirmagreg.api.util.EntityUtils.SpawnInfo;
import su.terrafirmagreg.api.util.EntityUtils.UpdateInfo;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.framework.manager.content.api.IContentEntry;
import su.terrafirmagreg.framework.manager.content.base.entity.api.IEntityEntry.EntitySettings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.registry.EntityEntry;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.google.common.base.Preconditions.checkNotNull;

public interface IEntityEntry extends IContentEntry<EntitySettings, EntityEntry> {

  @Override
  default void postRegister() {
    var settings = getSettings();

    EntityUtils.addUpdateInfo(asEntry(), settings.getUpdateInfo());
    EntityUtils.addEggInfo(asEntry(), settings.getEggInfo());
    EntityUtils.addSpawnInfo(asEntry(), settings.getSpawnInfo());
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  class EntitySettings extends ContentSettings<EntitySettings> {

    protected Class<? extends Entity> entity;
    protected SpawnInfo spawnInfo;
    protected UpdateInfo updateInfo = new UpdateInfo();
    protected EggInfo eggInfo;


    public static EntitySettings of() {
      return new EntitySettings();
    }

    public EntitySettings egg(int primaryColor, int secondaryColor) {
      this.eggInfo = new EggInfo(primaryColor, secondaryColor);
      return this;
    }

    public EntitySettings updateInfo(int range, int updateFrequency, boolean sendVelocityUpdates) {
      this.updateInfo = new UpdateInfo(range, updateFrequency, sendVelocityUpdates);
      return this;
    }

    public EntitySettings spawn(int weightedProb, int min, int max, EnumCreatureType typeOfCreature, Biome... biomes) {
      this.spawnInfo = new SpawnInfo(weightedProb, min, max, typeOfCreature, biomes);
      return this;
    }


    public <E extends Entity> EntitySettings entity(Class<E> entity, IRenderFactory<? super E> renderFactory) {
      this.entity = checkNotNull(entity, "entity class");
      ModelUtils.entity(entity, renderFactory);
      return this;
    }

    public <E extends Entity> EntitySettings entity(Class<E> entity) {
      this.entity = checkNotNull(entity, "entity class");
      return this;
    }


  }
}
