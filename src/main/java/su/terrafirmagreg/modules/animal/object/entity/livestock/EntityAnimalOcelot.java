package su.terrafirmagreg.modules.animal.object.entity.livestock;

import su.terrafirmagreg.api.helper.BiomeHelper;
import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.TranslatorUtils;
import su.terrafirmagreg.framework.network.spi.datasync.DataSerializers;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.network.SCPacketSimple;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

// Changes in config allow placing this animal in livestock and still respawn
public class EntityAnimalOcelot extends EntityOcelot implements IAnimal, ILivestock {

  //Values that has a visual effect on client
  private static final DataParameter<Boolean> GENDER = EntityDataManager.createKey(
    EntityAnimalOcelot.class, DataSerializers.BOOLEAN);
  private static final DataParameter<Integer> BIRTHDAY = EntityDataManager.createKey(
    EntityAnimalOcelot.class, DataSerializers.VARINT);
  private static final DataParameter<Float> FAMILIARITY = EntityDataManager.createKey(
    EntityAnimalOcelot.class, DataSerializers.FLOAT);
  private long lastFed; //Last time(in days) this entity was fed
  private long lastFDecay; //Last time(in days) this entity's familiarity had decayed
  private boolean fertilized; //Is this female fertilized?
  private long matingTime; //The last time(in ticks) this male tried fertilizing females
  private long lastDeath; //Last time(in days) this entity checked for dying of old age
  private long pregnantTime; // The time(in days) this entity became pregnant

  @SuppressWarnings("unused")
  public EntityAnimalOcelot(World world) {
    this(world, Gender.valueOf(MathUtils.RNG.nextBoolean()),
      EntityAnimalBase.getRandomGrowth(ConfigAnimal.ENTITY.OCELOT.adulthood,
        ConfigAnimal.ENTITY.OCELOT.elder));
  }

  public EntityAnimalOcelot(World world, Gender gender, int birthDay) {
    super(world);
    this.setGender(gender);
    this.setBirthDay(birthDay);
    this.setFamiliarity(0);
    this.setGrowingAge(0); //We don't use this
    this.matingTime = Calendar.PLAYER_TIME.getTicks();
    this.lastDeath = Calendar.PLAYER_TIME.getTotalDays();
    this.lastFDecay = Calendar.PLAYER_TIME.getTotalDays();
    this.fertilized = false;
  }

  @Override
  public void setGrowingAge(int age) {
    super.setGrowingAge(0); // Ignoring this
  }

  @Override
  public boolean isChild() {
    return this.getAge() == Age.CHILD;
  }

  @Override
  public void setScaleForAge(boolean child) {
    double ageScale = 1 / (2.0D - getPercentToAdulthood());
    this.setScale((float) ageScale);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity,
                            float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeHelper.isOceanic(biome) && !BiomeHelper.isBeach(biome) &&
        (biomeType == BiomeUtils.BiomeType.TROPICAL_FOREST
         || biomeType == BiomeUtils.BiomeType.SAVANNA)) {
      return ConfigAnimal.ENTITY.OCELOT.rarity;
    }
    return 0;
  }

  @Override
  public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
    return AnimalGroupingRules.MOTHER_AND_CHILDREN_OR_SOLO_MALE;
  }

  @Override
  public int getMinGroupSize() {
    return 1;
  }

  @Override
  public int getMaxGroupSize() {
    return 4;
  }

  @Override
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (this.ticksExisted % 100 == 0) {
      setScaleForAge(false);
    }
    if (!this.world.isRemote) {
      if (this.isFertilized()
          && Calendar.PLAYER_TIME.getTotalDays() >= pregnantTime + gestationDays()) {
        birthChildren();
        this.setFertilized(false);
      }
      // Is it time to decay familiarity?
      // If this entity was never fed(eg: new born, wild)
      // or wasn't fed yesterday(this is the starting of the second day)
      if (this.lastFDecay > -1 && this.lastFDecay + 1 < Calendar.PLAYER_TIME.getTotalDays()) {
        float familiarity = getFamiliarity();
        if (familiarity < 0.3f) {
          familiarity -= 0.02 * (Calendar.PLAYER_TIME.getTotalDays() - this.lastFDecay);
          this.lastFDecay = Calendar.PLAYER_TIME.getTotalDays();
          this.setFamiliarity(familiarity);
        }
      }
      if (this.getGender() == Gender.MALE && this.isReadyToMate()) {
        this.matingTime = Calendar.PLAYER_TIME.getTicks();
        EntityAnimalBase.findFemaleMate(this);
      }
      if (this.getAge() == Age.OLD && lastDeath < Calendar.PLAYER_TIME.getTotalDays()) {
        this.lastDeath = Calendar.PLAYER_TIME.getTotalDays();
        // Randomly die of old age, tied to entity UUID and calendar time
        final Random random = new Random(
          this.entityUniqueID.getMostSignificantBits() * Calendar.PLAYER_TIME.getTotalDays());
        if (random.nextDouble() < ConfigAnimal.ENTITY.OCELOT.oldDeathChance) {
          this.setDead();
        }
      }
      // Wild animals disappear after 125% lifespan
      if (this.getDaysToElderly() > 0 && this.getFamiliarity() < 0.10F &&
          (this.getDaysToElderly() + this.getDaysToAdulthood()) * 1.25F
          <= Calendar.PLAYER_TIME.getTotalDays() - this.getBirthDay()) {
        this.setDead();
      }
    }
  }

  public long gestationDays() {
    return ConfigAnimal.ENTITY.OCELOT.gestation;
  }

  public void birthChildren() {
    int numberOfChildren = ConfigAnimal.ENTITY.OCELOT.babies;
    for (int i = 0; i < numberOfChildren; i++) {
      EntityAnimalOcelot baby = new EntityAnimalOcelot(this.world,
        Gender.valueOf(MathUtils.RNG.nextBoolean()),
        (int) Calendar.PLAYER_TIME.getTotalDays());
      baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
      if (this.isTamed()) {
        baby.setOwnerId(this.getOwnerId());
        baby.setTamed(true);
        baby.setTameSkin(this.getTameSkin());
      }
      this.world.spawnEntity(baby);
    }
  }

  @Override
  public Gender getGender() {
    return Gender.valueOf(this.dataManager.get(GENDER));
  }

  @Override
  public void setGender(Gender gender) {
    this.dataManager.set(GENDER, gender.toBool());
  }

  @Override
  public float getAdultFamiliarityCap() {
    return 0.4f;
  }

  @Override
  public void onFertilized(@NotNull IAnimal male) {
    this.pregnantTime = Calendar.PLAYER_TIME.getTotalDays();
  }

  @Override
  public int getBirthDay() {
    return this.dataManager.get(BIRTHDAY);
  }

  @Override
  public void setBirthDay(int value) {
    this.dataManager.set(BIRTHDAY, value);
  }

  @Override
  public int getDaysToAdulthood() {
    return ConfigAnimal.ENTITY.OCELOT.adulthood;
  }

  @Override
  public boolean isReadyToMate() {
    if (this.getAge() != Age.ADULT || this.getFamiliarity() < 0.3f || this.isFertilized()
        || this.isHungry()) {
      return false;
    }
    return this.matingTime + EntityAnimalBase.MATING_COOLDOWN_DEFAULT_TICKS
           <= Calendar.PLAYER_TIME.getTicks();
  }

  @Override
  public float getFamiliarity() {
    return this.dataManager.get(FAMILIARITY);
  }

  @Override
  public void setFamiliarity(float value) {
    if (value < 0f) {
      value = 0f;
    }
    if (value > 1f) {
      value = 1f;
    }
    this.dataManager.set(FAMILIARITY, value);
  }

  @Override
  public boolean isFertilized() {
    return this.fertilized;
  }

  @Override
  public void setFertilized(boolean value) {
    this.fertilized = value;
  }

  @Override
  public boolean isHungry() {
    return lastFed < Calendar.PLAYER_TIME.getTotalDays();
  }

  @Override
  public int getDaysToElderly() {
    return ConfigAnimal.ENTITY.OCELOT.elder;
  }

  @Override
  public Type getType() {
    return Type.MAMMAL;
  }

  @Override
  public TextComponentTranslation getAnimalName() {
    String entityString = isTamed() ? "cat" : EntityList.getEntityString(this);
    return new TextComponentTranslation(
      ModUtils.localize("animal", entityString, this.getGender().name()));
  }

  @Override
  protected void initEntityAI() {
    super.initEntityAI();

    int priority = 1;
    for (String input : ConfigAnimal.ENTITY.OCELOT.huntCreatures) {
      ResourceLocation key = new ResourceLocation(input);
      EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(key);
      if (entityEntry != null) {
        Class<? extends Entity> entityClass = entityEntry.getEntityClass();
        if (EntityLivingBase.class.isAssignableFrom(entityClass)) {
          //noinspection unchecked
          this.targetTasks.addTask(priority++,
            new EntityAITargetNonTamed<>(this, (Class<EntityLivingBase>) entityClass, false,
              ent -> true));
        }
      }
    }
  }

  @Override
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(GENDER, true);
    getDataManager().register(BIRTHDAY, 0);
    getDataManager().register(FAMILIARITY, 0f);
  }

  @Override
  public void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
    super.writeEntityToNBT(nbt);
    NBTUtils.setGenericNBTValue(nbt, "gender", getGender().toBool());
    NBTUtils.setGenericNBTValue(nbt, "birth", getBirthDay());
    NBTUtils.setGenericNBTValue(nbt, "fed", lastFed);
    NBTUtils.setGenericNBTValue(nbt, "decay", lastFDecay);
    NBTUtils.setGenericNBTValue(nbt, "fertilized", this.fertilized);
    NBTUtils.setGenericNBTValue(nbt, "mating", matingTime);
    NBTUtils.setGenericNBTValue(nbt, "familiarity", getFamiliarity());
    NBTUtils.setGenericNBTValue(nbt, "lastDeath", lastDeath);
    NBTUtils.setGenericNBTValue(nbt, "pregnant", pregnantTime);
  }

  @Override
  public void readEntityFromNBT(@NotNull NBTTagCompound nbt) {
    super.readEntityFromNBT(nbt);
    this.setGender(Gender.valueOf(nbt.getBoolean("gender")));
    this.setBirthDay(nbt.getInteger("birth"));
    this.lastFed = nbt.getLong("fed");
    this.lastFDecay = nbt.getLong("decay");
    this.matingTime = nbt.getLong("mating");
    this.fertilized = nbt.getBoolean("fertilized");
    this.setFamiliarity(nbt.getFloat("familiarity"));
    this.lastDeath = nbt.getLong("lastDeath");
    this.pregnantTime = nbt.getLong("pregnant");
  }

  @Override
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_OCELOT;
  }

  @Override
  public boolean processInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
    ItemStack itemstack = player.getHeldItem(hand);

    if (!itemstack.isEmpty()) {
      if (itemstack.getItem() == Items.SPAWN_EGG) {
        return super.processInteract(player, hand); // Let vanilla spawn a baby
      } else if (!this.isTamed()) {
        // Ocelots -> Cats transformation before familiarization
        if (isFood(itemstack) && player.getDistanceSq(this) < 9.0D) {
          if (!player.isCreative()) {
            itemstack.shrink(1);
          }
          if (!this.world.isRemote) {
            if (this.rand.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
              this.setTamedBy(player);
              this.setTameSkin(1 + this.world.rand.nextInt(3));
              this.playTameEffect(true);
              this.aiSit.setSitting(true);
              this.world.setEntityState(this, (byte) 7);
            } else {
              this.playTameEffect(false);
              this.world.setEntityState(this, (byte) 6);
            }
          }
          return true;
        }
        return false;
      } else if (this.isFood(itemstack) && player.isSneaking() && getAdultFamiliarityCap() > 0.0F) {
        if (this.isHungry()) {
          // Refuses to eat rotten stuff
          ICapabilityFood cap = itemstack.getCapability(CapabilityFood.CAPABILITY, null);
          if (cap != null) {
            if (cap.isRotten()) {
              return false;
            }
          }
          if (!this.world.isRemote) {
            lastFed = Calendar.PLAYER_TIME.getTotalDays();
            lastFDecay = lastFed; //No decay needed
            this.consumeItemFromStack(player, itemstack);
            if (this.getAge() == Age.CHILD || this.getFamiliarity() < getAdultFamiliarityCap()) {
              float familiarity = this.getFamiliarity() + 0.06f;
              if (this.getAge() != Age.CHILD) {
                familiarity = Math.min(familiarity, getAdultFamiliarityCap());
              }
              this.setFamiliarity(familiarity);
            }
            world.playSound(null, this.getPosition(), SoundEvents.ENTITY_PLAYER_BURP,
              SoundCategory.AMBIENT, 1.0F, 1.0F);
          }
          return true;
        } else {
          if (!this.world.isRemote) {
            //Show tooltips
            if (this.isFertilized() && this.getType() == Type.MAMMAL) {
              ModuleAnimal.NETWORK.sendTo(SCPacketSimple.translateMessage(
                  SCPacketSimple.MessageCategory.ANIMAL,
                  ModUtils.localize("tooltip", "animal.mating.pregnant"), getAnimalName()),
                (EntityPlayerMP) player);
            }
          }
        }
      }
    }
    return super.processInteract(player, hand);
  }

  @Nullable
  @Override
  public EntityAnimalOcelot createChild(@NotNull EntityAgeable other) {
    // Cancel default vanilla behaviour (immediately spawns children of this animal) and set this female as fertilized
    if (other != this && this.getGender() == Gender.FEMALE && other instanceof IAnimal) {
      this.fertilized = true;
      this.resetInLove();
      this.onFertilized((IAnimal) other);
    } else if (other == this) {
      // Only called if this animal is interacted with a spawn egg
      // Try to return to vanilla's default method a baby of this animal, as if bred normally
      EntityAnimalOcelot baby = new EntityAnimalOcelot(this.world,
        Gender.valueOf(MathUtils.RNG.nextBoolean()),
        (int) Calendar.PLAYER_TIME.getTotalDays());
      if (this.isTamed()) {
        baby.setOwnerId(this.getOwnerId());
        baby.setTamed(true);
        baby.setTameSkin(this.getTameSkin());
      }
      return baby;
    }
    return null;
  }

  @Override
  public boolean canMateWith(EntityAnimal otherAnimal) {
    if (otherAnimal.getClass() != this.getClass()) {
      return false;
    }
    EntityAnimalOcelot other = (EntityAnimalOcelot) otherAnimal;
    return this.getGender() != other.getGender() && this.isInLove() && other.isInLove();
  }

  @Override
  public boolean getCanSpawnHere() {
    return this.world.checkNoEntityCollision(getEntityBoundingBox())
           && this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()
           && !this.world.containsAnyLiquid(getEntityBoundingBox())
           && BlockHelper.isGround(this.world.getBlockState(this.getPosition().down()));
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

      return TranslatorUtils.translateToLocal(ModUtils.localize("entity", string, "name"));
    }
  }
}
