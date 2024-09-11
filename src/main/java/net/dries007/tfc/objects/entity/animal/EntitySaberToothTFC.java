package net.dries007.tfc.objects.entity.animal;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.data.MathConstants;
import su.terrafirmagreg.modules.animal.api.type.IPredator;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalMammal;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.objects.entity.ai.EntityAIAttackMeleeTFC;
import net.dries007.tfc.objects.entity.ai.EntityAIWanderHuntArea;
import net.dries007.tfc.util.climate.BiomeHelper;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class EntitySaberToothTFC extends EntityAnimalMammal implements IPredator {

  private static final int DAYS_TO_ADULTHOOD = 192;

  @SuppressWarnings("unused")
  public EntitySaberToothTFC(World worldIn) {
    this(worldIn, Gender.valueOf(MathConstants.RNG.nextBoolean()),
            getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
  }

  public EntitySaberToothTFC(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    this.setSize(1.3F, 1.5F);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
    BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
            (biomeType == BiomeHelper.BiomeType.TROPICAL_FOREST)) {
      return ConfigTFC.Animals.SABER_TOOTH.rarity;
    }
    return 0;
  }

  @Override
  public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
    return AnimalGroupingRules.ELDER_AND_POPULATION;
  }

  @Override
  public int getDaysToAdulthood() {
    return DAYS_TO_ADULTHOOD;
  }

  @Override
  public int getDaysToElderly() {
    return 0;
  }

  @Override
  public long gestationDays() {
    return 0; // not farmable
  }

  @Override
  public void birthChildren() {
    // Not farmable
  }

  @Override
  public double getOldDeathChance() {
    return 0;
  }

  @Override
  public boolean canMateWith(EntityAnimal otherAnimal) {
    return false;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundsAnimal.ANIMAL_SABERTOOTH_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundsAnimal.ANIMAL_SABERTOOTH_DEATH;
  }

  @Override
  public boolean attackEntityAsMob(Entity entityIn) {
    double attackDamage = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    if (this.isChild()) {
      attackDamage /= 2;
    }
    boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) attackDamage);
    if (flag) {
      this.applyEnchantments(this, entityIn);
    }
    return flag;
  }

  @Override
  protected void updateAITasks() {
    super.updateAITasks();
    if (!this.hasHome()) {
      this.setHomePosAndDistance(this.getPosition(), 80);
    }
  }

  @Override
  protected void initEntityAI() {
    EntityAIWander wander = new EntityAIWanderHuntArea(this, 1.0D);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(3,
            new EntityAIAttackMeleeTFC<>(this, 1.2D, 1.25D, EntityAIAttackMeleeTFC.AttackBehavior.NIGHTTIME_ONLY).setWanderAI(wander));
    this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
    this.tasks.addTask(5, wander);
    this.tasks.addTask(7, new EntityAILookIdle(this));
    this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    // Avoid players at daytime
    this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 16.0F, 1.0D, 1.25D));

    int priority = 2;
    for (String input : ConfigTFC.Animals.SABER_TOOTH.huntCreatures) {
      ResourceLocation key = new ResourceLocation(input);
      EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(key);
      if (entityEntry != null) {
        Class<? extends Entity> entityClass = entityEntry.getEntityClass();
        if (EntityLivingBase.class.isAssignableFrom(entityClass)) {
          //noinspection unchecked
          this.targetTasks.addTask(priority++, new EntityAINearestAttackableTarget<>(this, (Class<EntityLivingBase>) entityClass, false));
        }
      }
    }
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
    this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.38D);
    this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return MathConstants.RNG.nextInt(100) < 5 ? SoundsAnimal.ANIMAL_SABERTOOTH_CRY : SoundsAnimal.ANIMAL_SABERTOOTH_SAY;
  }

  @Nullable
  @Override
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_SABERTOOTH;
  }

  @Override
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundsAnimal.ANIMAL_FELINE_STEP, 0.15F, 1.0F);
  }
}
