package su.terrafirmagreg.modules.animal.object.entity.predator;

import su.terrafirmagreg.api.helper.BiomeHelper;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.framework.network.spi.datasync.DataSerializers;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.IPredator;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalMammal;
import su.terrafirmagreg.modules.animal.object.entity.ai.EntityAnimalAIAttackMelee;
import su.terrafirmagreg.modules.animal.object.entity.ai.EntityAnimalAIWanderHuntArea;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

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
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static su.terrafirmagreg.api.util.MathUtils.RNG;

public class EntityAnimalLion extends EntityAnimalMammal implements IPredator {

  private static final int DAYS_TO_ADULTHOOD = 192;

  //Values that has a visual effect on client
  private static final DataParameter<Integer> MOUTH_TICKS = EntityDataManager.createKey(
    EntityAnimalLion.class, DataSerializers.VARINT);

  @SuppressWarnings("unused")
  public EntityAnimalLion(World worldIn) {
    this(worldIn, Gender.valueOf(RNG.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
  }

  public EntityAnimalLion(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    this.setSize(1.3F, 1.2F);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity,
                            float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeHelper.isOceanic(biome) && !BiomeHelper.isBeach(biome) &&
        (biomeType == BiomeUtils.BiomeType.SAVANNA)) {
      return ConfigAnimal.ENTITY.LION.rarity;
    }
    return 0;
  }

  @Override
  public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
    return AnimalGroupingRules.ELDER_AND_POPULATION;
  }

  @Override
  public int getMinGroupSize() {
    return 1;
  }

  @Override
  public int getMaxGroupSize() {
    return 5;
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
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(MOUTH_TICKS, 0);
  }

  @Override
  public long gestationDays() {
    return 0;
  }

  @Override
  public void birthChildren() {
    int numberOfChildren = 1; //one always
    for (int i = 0; i < numberOfChildren; i++) {
      EntityAnimalLion baby = new EntityAnimalLion(this.world, Gender.valueOf(RNG.nextBoolean()),
        (int) Calendar.PLAYER_TIME.getTotalDays());
      baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
      this.world.spawnEntity(baby);
    }
  }

  @Override
  public double getOldDeathChance() {
    return 0;
  }

  @Override
  public boolean canMateWith(EntityAnimal otherAnimal) {
    return false;
  }

  public int getMouthTicks() {
    return this.dataManager.get(MOUTH_TICKS);
  }

  public void setMouthTicks(int value) {
    this.dataManager.set(MOUTH_TICKS, value);
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundsAnimal.ANIMAL_LION_HURT.get();
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundsAnimal.ANIMAL_LION_DEATH.get();
  }

  @Override
  public boolean attackEntityAsMob(Entity entityIn) {
    double attackDamage = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
      .getAttributeValue();
    if (this.isChild()) {
      attackDamage /= 2;
    }
    boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this),
      (float) attackDamage);
    if (flag) {
      this.applyEnchantments(this, entityIn);
    }
    return flag;
  }

  @Override
  protected void initEntityAI() {
    EntityAIWander wander = new EntityAnimalAIWanderHuntArea(this, 1.0D);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(2, new EntityAnimalAILionAttack().setWanderAI(wander));
    this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
    this.tasks.addTask(5, wander);
    this.tasks.addTask(7, new EntityAILookIdle(this));
    // Avoid players at daytime
    this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 16.0F, 1.0D, 1.25D));

    this.targetTasks.addTask(1,
      new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

    int priority = 2;
    for (String input : ConfigAnimal.ENTITY.LION.huntCreatures) {
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

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
    this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.34D);
    this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return RNG.nextInt(100) < 5 ? SoundsAnimal.ANIMAL_LION_CRY.get() : SoundsAnimal.ANIMAL_LION_SAY.get();
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_LION;
  }

  @Override
  protected void updateAITasks() {
    super.updateAITasks();
    if (!this.hasHome()) {
      this.setHomePosAndDistance(this.getPosition(), 80);
    }
  }

  @Override
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundsAnimal.ANIMAL_FELINE_STEP.get(), 0.15F, 1.0F);
  }

  /**
   * Adds a bit of animation to the attack
   */
  protected class EntityAnimalAILionAttack extends EntityAnimalAIAttackMelee<EntityAnimalLion> {

    protected int attackTicks;

    public EntityAnimalAILionAttack() {
      super(EntityAnimalLion.this, 1.3D, 1.5D, AttackBehavior.NIGHTTIME_ONLY);
      this.attackTicks = 0;
    }

    @Override
    public void resetTask() {
      super.resetTask();
      this.attackTicks = 0;
      EntityAnimalLion.this.setMouthTicks(0);
    }

    @Override
    public void updateTask() {
      super.updateTask();
      ++this.attackTicks;
      EntityAnimalLion.this.setMouthTicks(attackTicks);
    }
  }
}
