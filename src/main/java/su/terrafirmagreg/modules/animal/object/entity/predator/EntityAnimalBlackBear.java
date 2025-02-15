package su.terrafirmagreg.modules.animal.object.entity.predator;

import su.terrafirmagreg.api.helper.BiomeHelper;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.IPredator;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.object.entity.ai.EntityAnimalAIAttackMelee;
import su.terrafirmagreg.modules.animal.object.entity.ai.EntityAnimalAIStandAttack;
import su.terrafirmagreg.modules.animal.object.entity.ai.EntityAnimalAIWanderHuntArea;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.util.MathUtils.RNG;

public class EntityAnimalBlackBear extends EntityAnimalGrizzlyBear implements IPredator,
                                                                              EntityAnimalAIStandAttack.IEntityStandAttack {

  private static final int DAYS_TO_ADULTHOOD = 240;

  @SuppressWarnings("unused")
  public EntityAnimalBlackBear(World worldIn) {
    this(worldIn, Gender.valueOf(RNG.nextBoolean()),
      EntityAnimalBase.getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
  }

  public EntityAnimalBlackBear(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    this.setSize(1.4F, 1.7F);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity,
                            float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeHelper.isOceanic(biome) && !BiomeHelper.isBeach(biome) &&
        (biomeType == BiomeUtils.BiomeType.TEMPERATE_FOREST)) {
      return ConfigAnimal.ENTITY.BLACK_BEAR.rarity;
    }
    return 0;
  }

  @Override
  protected void initEntityAI() {
    EntityAIWander wander = new EntityAnimalAIWanderHuntArea(this, 1.0D);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1,
      new EntityAnimalAIStandAttack<>(this, 1.2D, 2.0D,
        EntityAnimalAIAttackMelee.AttackBehavior.DAYLIGHT_ONLY).setWanderAI(wander));
    this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
    this.tasks.addTask(5, wander);
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.targetTasks.addTask(1,
      new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

    int priority = 2;
    for (String input : ConfigAnimal.ENTITY.BLACK_BEAR.huntCreatures) {
      ResourceLocation key = new ResourceLocation(input);
      EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(key);
      if (entityEntry != null) {
        Class<? extends Entity> entityClass = entityEntry.getEntityClass();
        if (EntityLivingBase.class.isAssignableFrom(entityClass)) {
          //noinspection unchecked
          this.targetTasks.addTask(priority++,
            new EntityAINearestAttackableTarget<>(this, (Class<EntityLivingBase>) entityClass,
              false));
        }
      }
    }
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_BLACK_BEAR;
  }
}
