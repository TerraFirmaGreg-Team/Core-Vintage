package su.terrafirmagreg.modules.animal.object.entity.livestock;

import su.terrafirmagreg.api.helper.BiomeHelper;
import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.network.datasync.DataSerializers;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.player.EntityPlayer;
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

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static su.terrafirmagreg.api.util.MathUtils.RNG;

public class EntityAnimalParrot extends EntityParrot implements IAnimal, ILivestock {

  private static final int DAYS_TO_ADULTHOOD = 96;
  //Values that has a visual effect on client
  private static final DataParameter<Boolean> GENDER = EntityDataManager.createKey(
    EntityAnimalParrot.class, DataSerializers.BOOLEAN);
  private static final DataParameter<Integer> BIRTHDAY = EntityDataManager.createKey(
    EntityAnimalParrot.class, DataSerializers.VARINT);
  private static final DataParameter<Float> FAMILIARITY = EntityDataManager.createKey(
    EntityAnimalParrot.class, DataSerializers.FLOAT);
  private long lastFed; //Last time(in days) this entity was fed
  private long lastFDecay; //Last time(in days) this entity's familiarity had decayed
  private boolean fertilized; //Is this female fertilized? (in oviparous, the egg laying is fertilized, for mammals this is pregnancy)
  private long matingTime; //The last time(in ticks) this male tried fertilizing females
  private long lastDeath; //Last time(in days) this entity checked for dying of old age

  @SuppressWarnings("unused")
  public EntityAnimalParrot(World world) {
    this(world, IAnimal.Gender.valueOf(RNG.nextBoolean()),
         EntityAnimalBase.getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
  }

  public EntityAnimalParrot(World world, IAnimal.Gender gender, int birthDay) {
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
    return this.getAge() == IAnimal.Age.CHILD;
  }

  @Override
  public void setScaleForAge(boolean child) {
    double ageScale = 1 / (2.0D - getPercentToAdulthood());
    this.setScale((float) ageScale);
  }

  @Override
  public boolean setEntityOnShoulder(@NotNull EntityPlayer player) {
    // todo if vanilla fixes the class enforcement in 1.15, do rework this animal
    return false;
  }

  @Override
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (this.ticksExisted % 100 == 0) {
      setScaleForAge(false);
    }
    if (!this.world.isRemote) {
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

                /* todo disabled for the time being (since it is not possible to breed parrots), in 1.15 see if this animal can sit in player shoulder and do a proper mechanic here
                this.lastDeath = CalendarTFC.PLAYER_TIME.getTotalDays();
                // Randomly die of old age, tied to entity UUID and calendar time
                final Random random = new Random(this.entityUniqueID.getMostSignificantBits() * CalendarTFC.PLAYER_TIME.getTotalDays());
                if (random.nextDouble() < ConfigTFC.GENERAL.chanceAnimalDeath)
                {
                    this.setDead();
                }
                */
      }
    }
  }

  @Override
  public boolean processInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
    ItemStack itemstack = player.getHeldItem(hand);

    if (!itemstack.isEmpty()) {
      if (itemstack.getItem() == Items.SPAWN_EGG) {
        return super.processInteract(player, hand); // Let vanilla spawn a baby
      } else if (this.isFood(itemstack) && player.isSneaking() && getAdultFamiliarityCap() > 0.0F) {
        if (this.isHungry()) {
          // Refuses to eat rotten stuff
          IFood cap = itemstack.getCapability(CapabilityFood.CAPABILITY, null);
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
        }
      }
    }
    // Only let vanilla's mechanics (taming / cooking / emitting love particles) if this parrot is familiarized
    if (this.getFamiliarity() < 0.3f) {
      return false;
    } else {
      return super.processInteract(player, hand);
    }
  }

  @Override
  public boolean getCanSpawnHere() {
    return this.world.checkNoEntityCollision(getEntityBoundingBox())
           && this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()
           && !this.world.containsAnyLiquid(getEntityBoundingBox())
           && BlockHelper.isGround(this.world.getBlockState(this.getPosition().down()));
  }

  @Override
  public boolean canMateWith(@NotNull EntityAnimal otherAnimal) {
    // At the time I wrote this, I decided to stick to vanilla.
    // Should we change this I think we also need to think how they would lay eggs
    // Because copying chicken mechanics for parrots is weird
    return false;
  }

  @Override
  public EntityAgeable createChild(@NotNull EntityAgeable ageable) {
    return new EntityAnimalParrot(this.world, IAnimal.Gender.valueOf(RNG.nextBoolean()),
                                  (int) Calendar.PLAYER_TIME.getTotalDays()); // Used by spawn eggs
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
  }

  @Override
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_PARROT;
  }

  @Override
  public IAnimal.Gender getGender() {
    return IAnimal.Gender.valueOf(this.dataManager.get(GENDER));
  }

  @Override
  public void setGender(IAnimal.Gender gender) {
    this.dataManager.set(GENDER, gender.toBool());
  }

  @Override
  public float getAdultFamiliarityCap() {
    return 0.4f;
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
    return DAYS_TO_ADULTHOOD;
  }

  @Override
  public boolean isReadyToMate() {
    // At the time I wrote this, I decided to stick to vanilla.
    // Should we change this I think we also need to think how they would lay eggs
    // Because copying chicken mechanics for parrots is weird
    return false;
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
    return fertilized;
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
    return 0;
  }

  @Override
  public IAnimal.Type getType() {
    return Type.OVIPAROUS;
  }

  @Override
  public TextComponentTranslation getAnimalName() {
    String entityString = EntityList.getEntityString(this);
    return new TextComponentTranslation(
      ModUtils.localize("animal." + entityString + "." + this.getGender().name()));
  }

  @NotNull
  @Override
  public String getName() {
    if (this.hasCustomName()) {
      return this.getCustomNameTag();
    } else {
      return getAnimalName().getFormattedText();
    }
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity,
                            float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeHelper.isOceanicBiome(biome) && !BiomeHelper.isBeachBiome(biome) &&
        (biomeType == BiomeUtils.BiomeType.TEMPERATE_FOREST
         || biomeType == BiomeUtils.BiomeType.TROPICAL_FOREST)) {
      return ConfigAnimal.ENTITY.PARROT.rarity;
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
    return 4;
  }
}
