package su.terrafirmagreg.modules.animal.object.entity.livestock;

import su.terrafirmagreg.api.helper.BiomeHelper;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.framework.network.spi.datasync.DataSerializers;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.api.type.IRidable;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static su.terrafirmagreg.api.util.MathUtils.RNG;

public class EntityAnimalCamel extends EntityAnimalLlama implements IAnimal, ILivestock, IRidable {

  private static final DataParameter<Integer> DATA_COLOR_ID = EntityDataManager.createKey(
    EntityAnimalCamel.class, DataSerializers.VARINT);
  private static final DataParameter<Boolean> HALTER = EntityDataManager.createKey(
    EntityAnimalCamel.class, DataSerializers.BOOLEAN);

  public EntityAnimalCamel(World world) {
    this(world, Gender.valueOf(RNG.nextBoolean()),
      EntityAnimalBase.getRandomGrowth(ConfigAnimal.ENTITY.CAMEL.adulthood,
        ConfigAnimal.ENTITY.CAMEL.elder));
    this.setSize(0.9F, 2.0F);
  }

  public EntityAnimalCamel(World world, Gender gender, int birthDay) {
    super(world, gender, birthDay);
  }

  public boolean canBeSteered() {
    return this.getControllingPassenger() instanceof EntityLivingBase;
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return RNG.nextInt(100) < 5 ? SoundsAnimal.ANIMAL_CAMEL_CRY.get() : SoundsAnimal.ANIMAL_CAMEL_SAY.get();
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundsAnimal.ANIMAL_CAMEL_HURT.get();
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundsAnimal.ANIMAL_CAMEL_DEATH.get();
  }

  @SuppressWarnings("deprecation")
  protected void playStepSound(BlockPos pos, Block blockIn) {
    if (!blockIn.getDefaultState().getMaterial().isLiquid()) {
      SoundType soundtype = blockIn.getSoundType();
      if (this.world.getBlockState(pos.up()).getBlock() == Blocks.SNOW_LAYER) {
        soundtype = Blocks.SNOW_LAYER.getSoundType();
      }

      if (this.isBeingRidden() && this.canGallop) {
        ++this.gallopTime;
        if (this.gallopTime > 5 && this.gallopTime % 3 == 0) {
          this.playGallopSound(soundtype);
        } else if (this.gallopTime <= 5) {
          this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F,
            soundtype.getPitch());
        }
      } else if (soundtype == SoundType.WOOD) {
        this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F,
          soundtype.getPitch());
      } else {
        this.playSound(SoundEvents.ENTITY_HORSE_STEP, soundtype.getVolume() * 0.15F,
          soundtype.getPitch());
      }
    }
  }

  public boolean wearsArmor() {
    return true;
  }

  public boolean isArmor(ItemStack stack) {
    return stack.getItem() == Item.getItemFromBlock(Blocks.CARPET);
  }

  public boolean canBeSaddled() {
    return true;
  }

  public void onInventoryChanged(IInventory invBasic) {
    EnumDyeColor enumdyecolor = this.getColor();
    super.onInventoryChanged(invBasic);
    EnumDyeColor enumdyecolor1 = this.getColor();
    if (this.ticksExisted > 20 && enumdyecolor1 != null && enumdyecolor1 != enumdyecolor) {
      this.playSound(SoundEvents.ENTITY_LLAMA_SWAG, 0.5F, 1.0F);
    }
    boolean flag = this.isHorseSaddled();
    this.updateHorseSlots();
    if (this.ticksExisted > 20 && !flag && this.isHorseSaddled()) {
      this.playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
    }
  }

  protected void updateHorseSlots() {
    if (!this.world.isRemote) {
      super.updateHorseSlots();
      this.setColorByItem(this.horseChest.getStackInSlot(1));
    }
  }

  @Nullable
  public EnumDyeColor getColor() {
    int i = this.dataManager.get(DATA_COLOR_ID);
    return i == -1 ? null : EnumDyeColor.byMetadata(i);
  }

  private void setColor(@Nullable EnumDyeColor color) {
    this.dataManager.set(DATA_COLOR_ID, color == null ? -1 : color.getMetadata());
  }

  private void setColorByItem(ItemStack stack) {
    if (this.isArmor(stack)) {
      this.setColor(EnumDyeColor.byMetadata(stack.getMetadata()));
    } else {
      this.setColor(null);
    }
  }

  protected void playGallopSound(SoundType p_190680_1_) {
    this.playSound(SoundEvents.ENTITY_HORSE_GALLOP, p_190680_1_.getVolume() * 0.15F,
      p_190680_1_.getPitch());
  }

  @Nullable
  public Entity getControllingPassenger() {
    return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
  }

  @Override
  public boolean processInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (canAcceptHalter(stack)) {
      return attemptApplyHalter(this, this.world, player, stack);
    }
    return super.processInteract(player, hand);
  }

  @Override
  public void onFertilized(@NotNull IAnimal male) {
    this.setPregnantTime(Calendar.PLAYER_TIME.getTotalDays());
    int selection = this.rand.nextInt(9);
    int i;
    if (selection < 4) {
      i = this.getVariant();
    } else if (selection < 8) {
      i = ((EntityAnimalCamel) male).getVariant();
    } else {
      // Mutation
      i = this.rand.nextInt(4);
    }
    this.geneVariant = i;
    EntityAnimalCamel father = (EntityAnimalCamel) male;
    this.geneHealth = (float) ((father.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
                                  .getBaseValue() + this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
                                  .getBaseValue() + this.getModifiedMaxHealth()) / 3.0D);
    this.geneSpeed = (float) ((father.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                                 .getBaseValue() + this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                                 .getBaseValue() + this.getModifiedMovementSpeed()) / 3.0D);
    this.geneJump = (float) ((father.getEntityAttribute(JUMP_STRENGTH)
                                .getBaseValue() + this.getEntityAttribute(JUMP_STRENGTH)
                                .getBaseValue() + this.getModifiedJumpStrength()) / 3.0D);

    this.geneStrength = this.rand.nextInt(Math.max(this.getStrength(), father.getStrength())) + 1;
    if (this.rand.nextFloat() < 0.03F) {
      this.geneStrength++;
    }
  }

  @Override
  public int getDaysToAdulthood() {
    return ConfigAnimal.ENTITY.CAMEL.adulthood;
  }

  @Override
  public int getDaysToElderly() {
    return ConfigAnimal.ENTITY.CAMEL.elder;
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity,
                            float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeHelper.isOceanic(biome) && !BiomeHelper.isBeach(biome) &&
        (biomeType == BiomeUtils.BiomeType.DESERT || biomeType == BiomeUtils.BiomeType.SAVANNA)) {
      return ConfigAnimal.ENTITY.CAMEL.rarity;
    }
    return 0;
  }

  @Override
  public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
    return AnimalGroupingRules.MALE_AND_FEMALES;
  }

  @Override
  public int getMinGroupSize() {
    return 1;
  }

  @Override
  public int getMaxGroupSize() {
    return 2;
  }

  @Override
  public void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
    super.writeEntityToNBT(nbt);
    NBTUtils.setGenericNBTValue(nbt, "Variant", this.getVariant());
    NBTUtils.setGenericNBTValue(nbt, "Strength", this.getStrength());
    if (!this.horseChest.getStackInSlot(1).isEmpty()) {
      NBTUtils.setGenericNBTValue(nbt, "DecorItem", this.horseChest.getStackInSlot(1));
    }
    NBTUtils.setGenericNBTValue(nbt, "halter", isHalter());
  }

  @Override
  public void readEntityFromNBT(@NotNull NBTTagCompound nbt) {
    super.readEntityFromNBT(nbt);
    this.setStrength(nbt.getInteger("Strength"));
    this.setVariant(nbt.getInteger("Variant"));
    if (nbt.hasKey("DecorItem", 10)) {
      this.horseChest.setInventorySlotContents(1, new ItemStack(nbt.getCompoundTag("DecorItem")));
    }
    this.updateHorseSlots();
    this.setHalter(nbt.getBoolean("halter"));
  }

  @Override
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(DATA_COLOR_ID, -1);
    getDataManager().register(HALTER, false);
  }

  @Override
  protected void mountTo(EntityPlayer player) {
    if (isHalter()) {
      super.mountTo(player);
    }
  }

  @Override
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_CAMEL;
  }

  @Override
  public boolean canMateWith(EntityAnimal otherAnimal) {
    if (otherAnimal.getClass() != this.getClass()) {
      return false;
    }
    EntityAnimalCamel other = (EntityAnimalCamel) otherAnimal;
    return this.getGender() != other.getGender() && this.isInLove() && other.isInLove();
  }

  @Nullable
  @Override
  public EntityAnimalCamel createChild(@NotNull EntityAgeable other) {
    // Cancel default vanilla behaviour (immediately spawns children of this animal) and set this female as fertilized
    if (other != this && this.getGender() == Gender.FEMALE && other instanceof IAnimal) {
      super.setFertilized(true);
      this.resetInLove();
      this.onFertilized((IAnimal) other);
    } else if (other == this) {
      // Only called if this animal is interacted with a spawn egg
      // Try to return to vanilla's default method a baby of this animal, as if bred normally
      return new EntityAnimalCamel(this.world, Gender.valueOf(RNG.nextBoolean()),
        (int) Calendar.PLAYER_TIME.getTotalDays());
    }
    return null;
  }

  @Override
  public long gestationDays() {
    return ConfigAnimal.ENTITY.CAMEL.gestation;
  }

  @Override
  public void birthChildren() {
    int numberOfChildren = ConfigAnimal.ENTITY.CAMEL.babies; //one always
    for (int i = 0; i < numberOfChildren; i++) {
      EntityAnimalCamel baby = new EntityAnimalCamel(this.world, Gender.valueOf(RNG.nextBoolean()),
        (int) Calendar.PLAYER_TIME.getTotalDays());
      baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
      if (this.geneHealth > 0) {
        baby.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.geneHealth);
      }
      if (this.geneSpeed > 0) {
        baby.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
          .setBaseValue(this.geneSpeed);
      }
      if (this.geneJump > 0) {
        baby.getEntityAttribute(JUMP_STRENGTH).setBaseValue(this.geneJump);
      }
      if (this.geneStrength > 0) {
        this.setStrength((int) this.geneStrength);
      }
      baby.setVariant(geneVariant);
      this.world.spawnEntity(baby);
    }
    geneJump = 0;
    geneSpeed = 0;
    geneJump = 0;
    geneStrength = 0;
    geneVariant = 0;
  }

  public boolean isHalter() {
    return dataManager.get(HALTER);
  }

  public void setHalter(boolean value) {
    dataManager.set(HALTER, value);
  }
}
