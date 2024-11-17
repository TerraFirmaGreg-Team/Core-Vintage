package su.terrafirmagreg.modules.animal.object.entity.livestock;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.network.datasync.DataSerializers;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.api.type.IRidable;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.init.PotionsCore;
import su.terrafirmagreg.modules.core.network.SCPacketSimple;

import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static su.terrafirmagreg.api.util.MathUtils.RNG;

public class EntityAnimalMule extends EntityMule implements IAnimal, ILivestock, IRidable {

  //Values that has a visual effect on client
  private static final DataParameter<Boolean> GENDER = EntityDataManager.createKey(
    EntityAnimalMule.class, DataSerializers.BOOLEAN);
  private static final DataParameter<Integer> BIRTHDAY = EntityDataManager.createKey(
    EntityAnimalMule.class, DataSerializers.VARINT);
  private static final DataParameter<Float> FAMILIARITY = EntityDataManager.createKey(
    EntityAnimalMule.class, DataSerializers.FLOAT);
  private static final DataParameter<Boolean> HALTER = EntityDataManager.createKey(
    EntityAnimalMule.class, DataSerializers.BOOLEAN);
  private long lastFed; //Last time(in days) this entity was fed
  private long lastFDecay; //Last time(in days) this entity's familiarity had decayed
  private long lastDeath; //Last time(in days) this entity checked for dying of old age

  public EntityAnimalMule(World world) {
    this(world, Gender.valueOf(RNG.nextBoolean()),
         EntityAnimalBase.getRandomGrowth(ConfigAnimal.ENTITY.MULE.adulthood,
                                          ConfigAnimal.ENTITY.MULE.elder));
  }

  public EntityAnimalMule(World world, Gender gender, int birthDay) {
    super(world);
    this.setGender(gender);
    this.setBirthDay(birthDay);
    this.setFamiliarity(0);
    this.setGrowingAge(0); //We don't use this
    this.lastDeath = Calendar.PLAYER_TIME.getTotalDays();
    this.lastFDecay = Calendar.PLAYER_TIME.getTotalDays();
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
  public boolean getCanSpawnHere() {
    return this.world.checkNoEntityCollision(getEntityBoundingBox())
           && this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()
           && !this.world.containsAnyLiquid(getEntityBoundingBox())
           && BlockHelper.isGround(this.world.getBlockState(this.getPosition().down()));
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
  public Gender getGender() {
    return Gender.valueOf(this.dataManager.get(GENDER));
  }

  @Override
  public void setGender(Gender gender) {
    this.dataManager.set(GENDER, gender.toBool());
  }

  @Override
  public float getAdultFamiliarityCap() {
    return 0.35f;
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
    return ConfigAnimal.ENTITY.MULE.adulthood;
  }

  @Override
  public boolean isReadyToMate() {
    return false; // Prevent mating, like vanilla and IRL
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
    return false;
  }

  @Override
  public void setFertilized(boolean value) {
  }

  @Override
  public boolean isHungry() {
    return lastFed < Calendar.PLAYER_TIME.getTotalDays();
  }

  @Override
  public int getDaysToElderly() {
    return ConfigAnimal.ENTITY.MULE.elder;
  }

  @Override
  public Type getType() {
    return Type.MAMMAL;
  }

  @Override
  public TextComponentTranslation getAnimalName() {
    String entityString = EntityList.getEntityString(this);
    return new TextComponentTranslation(
      ModUtils.localize("animal." + entityString + "." + this.getGender().name()));
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity,
                            float floraDiversity) {
    return ConfigAnimal.ENTITY.MULE.rarity; // Not naturally spawned, must be bred
  }

  @Override
  public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
    return AnimalGroupingRules.ELDER_AND_POPULATION;
  }

  @Override
  public int getMinGroupSize() {
    return 2;
  }

  @Override
  public int getMaxGroupSize() {
    return 5;
  }

  @Override
  protected void initEntityAI() {
    EntityAnimalBase.addCommonLivestockAI(this, 1.2D);
    EntityAnimalBase.addCommonPreyAI(this, 1.2);
    this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
    this.tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
  }

  @Override
  public void setScaleForAge(boolean child) {
    double ageScale = 1 / (2.0D - getPercentToAdulthood());
    this.setScale((float) ageScale);
  }

  @Override
  protected boolean handleEating(EntityPlayer player, ItemStack stack) {
    return false; // Stop exploits
  }

  @Override
  protected void mountTo(EntityPlayer player) {
    if (isHalter()) {
      super.mountTo(player);
    }
  }

  public boolean isHalter() {
    return dataManager.get(HALTER);
  }

  public void setHalter(boolean value) {
    dataManager.set(HALTER, value);
  }

  @Override
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (this.ticksExisted % 100 == 0) {
      setScaleForAge(false);
    }
    if (!this.world.isRemote) {
      if (this.hasChest() && this.ticksExisted % 20 == 0) {
        // Apply overburdened when carrying more than one heavy item
        int hugeHeavyCount = 0;
        for (int i = 2; i < this.horseChest.getSizeInventory(); ++i) {
          ItemStack stack = this.horseChest.getStackInSlot(i);
          if (CapabilitySize.checkItemSize(stack, Size.HUGE, Weight.VERY_HEAVY)) {
            hugeHeavyCount++;
            if (hugeHeavyCount >= 2) {
              break;
            }
          }
        }
        if (hugeHeavyCount >= 2) {
          // Does not work when ridden, mojang bug: https://bugs.mojang.com/browse/MC-121788
          this.addPotionEffect(new PotionEffect(PotionsCore.OVERBURDENED, 25, 125, false, false));
        }
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
      if (this.getAge() == Age.OLD && lastDeath < Calendar.PLAYER_TIME.getTotalDays()) {
        this.lastDeath = Calendar.PLAYER_TIME.getTotalDays();
        // Randomly die of old age, tied to entity UUID and calendar time
        final Random random = new Random(
          this.entityUniqueID.getMostSignificantBits() * Calendar.PLAYER_TIME.getTotalDays());
        if (random.nextDouble() < ConfigAnimal.ENTITY.MULE.oldDeathChance) {
          this.setDead();
        }
      }
    }
  }

  @Override
  public boolean canMateWith(EntityAnimal otherAnimal) {
    return false; // Prevent mating, like vanilla and IRL
  }

  @Nullable
  @Override
  public EntityAgeable createChild(@NotNull EntityAgeable other) {
    if (other == this) {
      // Only called if this animal is interacted with a spawn egg
      EntityAnimalMule baby = new EntityAnimalMule(this.world, Gender.valueOf(RNG.nextBoolean()),
                                                   (int) Calendar.PLAYER_TIME.getTotalDays());
      this.setOffspringAttributes(this, baby);
      return baby;
    }
    return null;
  }

  @Override
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_MULE;
  }

  @Override
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(GENDER, true);
    getDataManager().register(BIRTHDAY, 0);
    getDataManager().register(FAMILIARITY, 0f);
    getDataManager().register(HALTER, false);
  }

  @Override
  public void onDeath(DamageSource cause) {
    if (!world.isRemote) {
      setChested(false); // Don't drop chest
    }
    super.onDeath(cause);
  }

  @Override
  public void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
    super.writeEntityToNBT(nbt);
    NBTUtils.setGenericNBTValue(nbt, "gender", getGender().toBool());
    NBTUtils.setGenericNBTValue(nbt, "birth", getBirthDay());
    NBTUtils.setGenericNBTValue(nbt, "fed", lastFed);
    NBTUtils.setGenericNBTValue(nbt, "decay", lastFDecay);
    NBTUtils.setGenericNBTValue(nbt, "familiarity", getFamiliarity());
    NBTUtils.setGenericNBTValue(nbt, "lastDeath", lastDeath);
    NBTUtils.setGenericNBTValue(nbt, "halter", isHalter());
  }

  @Override
  public void readEntityFromNBT(@NotNull NBTTagCompound nbt) {
    super.readEntityFromNBT(nbt);
    this.setGender(Gender.valueOf(nbt.getBoolean("gender")));
    this.setBirthDay(nbt.getInteger("birth"));
    this.lastFed = nbt.getLong("fed");
    this.lastFDecay = nbt.getLong("decay");
    this.setFamiliarity(nbt.getFloat("familiarity"));
    this.lastDeath = nbt.getLong("lastDeath");
    this.setHalter(nbt.getBoolean("halter"));
  }

  @Override
  public boolean processInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);

    if (!stack.isEmpty()) {
      boolean holdingChest = false;
      if (stack.getItem() instanceof ItemBlock itemBlock) {
        holdingChest = itemBlock.getBlock() instanceof BlockChest;
      }
      if (stack.getItem() == Items.SPAWN_EGG) {
        return super.processInteract(player, hand); // Let vanilla spawn a baby
      } else if (!this.hasChest() && this.isTame() && holdingChest) {
        this.setChested(true);
        this.playChestEquipSound();
        this.initHorseChest();
        if (!player.capabilities.isCreativeMode) {
          stack.shrink(1);
        }
        return true;
      } else if (canAcceptHalter(stack)) {
        return attemptApplyHalter(this, this.world, player, stack);
      } else if (this.isFood(stack) && player.isSneaking() && getAdultFamiliarityCap() > 0.0F) {
        if (this.isHungry()) {
          // Refuses to eat rotten stuff
          IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
          if (cap != null) {
            if (cap.isRotten()) {
              return false;
            }
          }
          if (!this.world.isRemote) {
            lastFed = Calendar.PLAYER_TIME.getTotalDays();
            lastFDecay = lastFed; //No decay needed
            this.consumeItemFromStack(player, stack);
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
              ModuleAnimal.PACKET_SERVICE.sendTo(SCPacketSimple.translateMessage(
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
}
