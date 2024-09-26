package su.terrafirmagreg.api.base.biome;

import su.terrafirmagreg.api.base.biome.spi.IBiomeSettings;
import su.terrafirmagreg.modules.world.ConfigWorld;
import su.terrafirmagreg.modules.world.classic.objects.spawner.EntitySpawnerWorldData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import net.dries007.tfc.util.climate.Climate;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.base.biome.BaseBiomeProvider.WORLD_GEN_BIOMES;

@Getter
public abstract class BaseBiome extends Biome implements IBiomeSettings {

  public final Settings settings;

  public BaseBiome(Settings settings) {

    super(settings.build());

    this.settings = settings;

    // throw out the first decorator, because it's missing the lilypad & plant settings
    this.decorator = createBiomeDecorator();
    this.spawnableCreatureList.clear();

    if (settings.isWorldGen()) {
      WORLD_GEN_BIOMES.add(this);
    }

    // Add creatures to respawn listgetBiomeWeight
    for (String input : ConfigWorld.MISC.respawnableCreatures) {
      String[] split = input.split(" ");
      if (split.length == 4) {
        ResourceLocation key = new ResourceLocation(split[0]);
        int rarity;
        int min;
        int max;
        try {
          rarity = Integer.parseInt(split[1]);
          min = Integer.parseInt(split[2]);
          max = Integer.parseInt(split[3]);
        } catch (NumberFormatException e) {
          continue;
        }
        EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(key);
        if (entityEntry != null) {
          Class<? extends Entity> entityClass = entityEntry.getEntityClass();
          if (EntityLiving.class.isAssignableFrom(entityClass)) {
            //noinspection unchecked
            spawnableCreatureList.add(
              new Biome.SpawnListEntry((Class<? extends EntityLiving>) entityClass, rarity, min,
                                       max));
          }
        }
      }
    }

    // todo: Experimental Livestock respawning
    for (Class<? extends EntityLiving> entityClass : EntitySpawnerWorldData.LIVESTOCK.keySet()) {
      spawnableCreatureList.add(new Biome.SpawnListEntry(entityClass, 300, 1, 1));
    }
  }

  @Override
  public BiomeDecorator createBiomeDecorator() {
    return new BaseBiomeDecorator(0, 0);
  }

  @Override
  public float getTemperature(@NotNull BlockPos pos) {
    // Vanilla spec: 0.15 = snow threshold, range = [-1, 1] for overworld temps.
    return MathHelper.clamp(0.15f + Climate.getDailyTemp(pos) / 35, -1, 1);
  }

  @Override
  public boolean ignorePlayerSpawnSuitability() {

    return getSettings().isSpawnBiome();
  }

  public abstract int getBiomeWeight();

  public abstract BiomeDictionary.Type[] getTypes();

  public BaseBiome mutate(Random rand) {

    return this;
  }

}
