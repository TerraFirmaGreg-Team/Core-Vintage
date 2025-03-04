package su.terrafirmagreg.modules.animal.object.entity.huntable;

import su.terrafirmagreg.api.helper.BiomeHelper;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.framework.network.spi.datasync.DataSerializers;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.IHuntable;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalMammal;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class EntityAnimalRabbit extends EntityAnimalMammal implements IHuntable {

  private static final int DAYS_TO_ADULTHOOD = 16;
  private static final DataParameter<Integer> RABBIT_TYPE = EntityDataManager.createKey(
    EntityAnimalRabbit.class, DataSerializers.VARINT);

  private int jumpTicks;
  private int jumpDuration;
  private boolean wasOnGround;
  private int currentMoveTypeDuration;

  @SuppressWarnings("unused")
  public EntityAnimalRabbit(World worldIn) {
    this(worldIn, Gender.valueOf(MathUtils.RNG.nextBoolean()),
      getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
  }

  public EntityAnimalRabbit(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    this.setSize(0.4F, 0.5F);
    this.jumpHelper = new RabbitJumpHelper(this);
    this.moveHelper = new RabbitMoveHelper(this);
    this.setMovementSpeed(0.0D);
  }

  public void setMovementSpeed(double newSpeed) {
    this.getNavigator().setSpeed(newSpeed);
    this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(),
      this.moveHelper.getZ(), newSpeed);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity,
                            float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeHelper.isOceanic(biome) && !BiomeHelper.isBeach(biome) &&
        (biomeType == BiomeUtils.BiomeType.TAIGA || biomeType == BiomeUtils.BiomeType.PLAINS
         || biomeType == BiomeUtils.BiomeType.TUNDRA)) {
      return ConfigAnimal.ENTITY.RABBIT.rarity;
    }
    return 0;
  }

  @Override
  public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
    return AnimalGroupingRules.ELDER_AND_POPULATION;
  }

  @Override
  public int getMinGroupSize() {
    return 4;
  }

  @Override
  public int getMaxGroupSize() {
    return 7;
  }

  @Override
  public void updateAITasks() {
    super.updateAITasks();

    if (this.currentMoveTypeDuration > 0) {
      --this.currentMoveTypeDuration;
    }

    if (this.onGround) {
      if (!this.wasOnGround) {
        this.setJumping(false);
        this.checkLandingDelay();
      }

      RabbitJumpHelper entityrabbit$rabbitjumphelper = (RabbitJumpHelper) this.jumpHelper;

      if (!entityrabbit$rabbitjumphelper.isJumping()) {
        if (this.moveHelper.isUpdating() && this.currentMoveTypeDuration == 0) {
          Path path = this.navigator.getPath();
          Vec3d vec3d = new Vec3d(this.moveHelper.getX(), this.moveHelper.getY(),
            this.moveHelper.getZ());

          if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
            vec3d = path.getPosition(this);
          }

          this.calculateRotationYaw(vec3d.x, vec3d.z);
          this.startJumping();
        }
      } else if (!entityrabbit$rabbitjumphelper.canJump()) {
        this.enableJumpControl();
      }
    }

    this.wasOnGround = this.onGround;
  }

  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 1) {
      this.createRunningParticles();
      this.jumpDuration = 10;
      this.jumpTicks = 0;
    } else {
      super.handleStatusUpdate(id);
    }
  }

  private void checkLandingDelay() {
    this.updateMoveTypeDuration();
    this.disableJumpControl();
  }

  private void calculateRotationYaw(double x, double z) {
    this.rotationYaw =
      (float) (MathHelper.atan2(z - this.posZ, x - this.posX) * (180D / Math.PI)) - 90.0F;
  }

  public void startJumping() {
    this.setJumping(true);
    this.jumpDuration = 10;
    this.jumpTicks = 0;
  }

  private void enableJumpControl() {
    ((RabbitJumpHelper) this.jumpHelper).setCanJump(true);
  }

  protected SoundEvent getJumpSound() {
    return SoundEvents.ENTITY_RABBIT_JUMP;
  }

  private void updateMoveTypeDuration() {
    if (this.moveHelper.getSpeed() < 2.2D) {
      this.currentMoveTypeDuration = 10;
    } else {
      this.currentMoveTypeDuration = 1;
    }
  }

  private void disableJumpControl() {
    ((RabbitJumpHelper) this.jumpHelper).setCanJump(false);
  }

  @SideOnly(Side.CLIENT)
  public float getJumpCompletion(float p_175521_1_) {
    return this.jumpDuration == 0 ? 0.0F
                                  : ((float) this.jumpTicks + p_175521_1_) / (float) this.jumpDuration;
  }

  @Override
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(RABBIT_TYPE, 0);
  }

  public void onLivingUpdate() {
    super.onLivingUpdate();

    if (this.jumpTicks != this.jumpDuration) {
      ++this.jumpTicks;
    } else if (this.jumpDuration != 0) {
      this.jumpTicks = 0;
      this.jumpDuration = 0;
      this.setJumping(false);
    }
  }

  @Override
  public long gestationDays() {
    return 0;
  }

  @Override
  public void birthChildren() {
    int numberOfChildren = 5 + rand.nextInt(5); // 5-10
    for (int i = 0; i < numberOfChildren; i++) {
      EntityAnimalRabbit baby = new EntityAnimalRabbit(this.world,
        Gender.valueOf(MathUtils.RNG.nextBoolean()),
        (int) Calendar.PLAYER_TIME.getTotalDays());
      baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
      this.world.spawnEntity(baby);
    }
  }

  public void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
    super.writeEntityToNBT(nbt);
    NBTUtils.setGenericNBTValue(nbt, "RabbitType", this.getRabbitType());
  }

  public int getRabbitType() {
    return this.dataManager.get(RABBIT_TYPE);
  }

  public void setRabbitType(int rabbitTypeId) {
    this.dataManager.set(RABBIT_TYPE, rabbitTypeId);
  }

  public void readEntityFromNBT(@NotNull NBTTagCompound nbt) {
    super.readEntityFromNBT(nbt);
    this.setRabbitType(nbt.getInteger("RabbitType"));
  }

  @NotNull
  @Override
  public SoundCategory getSoundCategory() {
    return SoundCategory.NEUTRAL;
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
  public double getOldDeathChance() {
    return 0;
  }

  @Override
  public boolean canMateWith(EntityAnimal otherAnimal) {
    return false;
  }

  @Override
  protected void initEntityAI() {
    double speedMult = 2.2D;
    addWildPreyAI(this, speedMult);
    addCommonPreyAI(this, speedMult);

    this.tasks.taskEntries.removeIf(entry -> entry.action instanceof EntityAIPanic);

    this.tasks.addTask(1, new AIPanic(this, 1.4D * speedMult));
    this.tasks.addTask(2, new EntityAIMate(this, 1.2D));
    for (ItemStack is : OreDictionary.getOres("carrot")) {
      Item item = is.getItem();
      this.tasks.addTask(3, new EntityAITempt(this, 1.4D, item, false));
    }
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
      .setBaseValue(0.30000001192092896D);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_RABBIT_AMBIENT;
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_RABBIT;
  }

  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty,
                                          @Nullable IEntityLivingData livingdata) {
    livingdata = super.onInitialSpawn(difficulty, livingdata);
    int i = this.getRandomRabbitType();

    if (livingdata instanceof RabbitTypeData) {
      i = ((RabbitTypeData) livingdata).typeData;
    } else {
      livingdata = new RabbitTypeData(i);
    }

    this.setRabbitType(i);

    return livingdata;
  }

  private int getRandomRabbitType() {
    Biome biome = this.world.getBiome(new BlockPos(this));
    int i = this.rand.nextInt(100);
    if (biome.isSnowyBiome()) {
      return i < 5 ? 7 : (i < 10 ? 6 : (i < 80 ? 1 : 3));
    } else {
      return i < 50 ? 0 : (i < 90 ? 5 : (i < 95 ? 2 : 4));
    }
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundEvents.ENTITY_RABBIT_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_RABBIT_DEATH;
  }

  protected float getJumpUpwardsMotion() {
    if (!this.collidedHorizontally && (!this.moveHelper.isUpdating()
                                       || this.moveHelper.getY() <= this.posY + 0.5D)) {
      Path path = this.navigator.getPath();

      if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
        Vec3d vec3d = path.getPosition(this);

        if (vec3d.y > this.posY + 0.5D) {
          return 0.5F;
        }
      }

      return this.moveHelper.getSpeed() <= 0.6D ? 0.2F : 0.3F;
    } else {
      return 0.5F;
    }
  }

  protected void jump() {
    super.jump();
    double d0 = this.moveHelper.getSpeed();

    if (d0 > 0.0D) {
      double d1 = this.motionX * this.motionX + this.motionZ * this.motionZ;

      if (d1 < 0.010000000000000002D) {
        this.moveRelative(0.0F, 0.0F, 1.0F, 0.1F);
      }
    }

    if (!this.world.isRemote) {
      this.world.setEntityState(this, (byte) 1);
    }
  }

  public void setJumping(boolean jumping) {
    super.setJumping(jumping);

    if (jumping) {
      this.playSound(this.getJumpSound(), this.getSoundVolume(),
        ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
    }
  }

  static class RabbitMoveHelper extends EntityMoveHelper {

    private final EntityAnimalRabbit rabbit;
    private double nextJumpSpeed;

    public RabbitMoveHelper(EntityAnimalRabbit rabbit) {
      super(rabbit);
      this.rabbit = rabbit;
    }

    public void setMoveTo(double x, double y, double z, double speedIn) {
      if (this.rabbit.isInWater()) {
        speedIn = 1.5D;
      }

      super.setMoveTo(x, y, z, speedIn);

      if (speedIn > 0.0D) {
        this.nextJumpSpeed = speedIn;
      }
    }

    public void onUpdateMoveHelper() {
      if (this.rabbit.onGround && !this.rabbit.isJumping
          && !((RabbitJumpHelper) this.rabbit.jumpHelper).isJumping()) {
        this.rabbit.setMovementSpeed(0.0D);
      } else if (this.isUpdating()) {
        this.rabbit.setMovementSpeed(this.nextJumpSpeed);
      }

      super.onUpdateMoveHelper();
    }
  }

  public static class RabbitTypeData implements IEntityLivingData {

    public int typeData;

    public RabbitTypeData(int type) {
      this.typeData = type;
    }
  }

  static class AIPanic extends EntityAIPanic {

    private final EntityAnimalRabbit rabbit;

    public AIPanic(EntityAnimalRabbit rabbit, double speedIn) {
      super(rabbit, speedIn);
      this.rabbit = rabbit;
    }

    public void updateTask() {
      super.updateTask();
      this.rabbit.setMovementSpeed(this.speed);
    }
  }

  public static class RabbitJumpHelper extends EntityJumpHelper {

    private final EntityAnimalRabbit rabbit;
    private boolean canJump;

    public RabbitJumpHelper(EntityAnimalRabbit rabbit) {
      super(rabbit);
      this.rabbit = rabbit;
    }

    public boolean isJumping() {
      return this.isJumping;
    }

    public boolean canJump() {
      return this.canJump;
    }

    public void setCanJump(boolean canJumpIn) {
      this.canJump = canJumpIn;
    }

    public void doJump() {
      if (this.isJumping) {
        this.rabbit.startJumping();
        this.isJumping = false;
      }
    }
  }
}
