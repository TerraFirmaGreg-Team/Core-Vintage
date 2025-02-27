package su.terrafirmagreg.modules.animal.object.entity.livestock;

import su.terrafirmagreg.api.helper.BiomeHelper;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.framework.network.spi.datasync.DataSerializers;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalMammal;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.network.SCPacketSimple;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.IShearable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static su.terrafirmagreg.api.util.MathUtils.RNG;

public class EntityAnimalSheep extends EntityAnimalMammal implements IShearable, ILivestock {

  private static final DataParameter<Integer> DYE_COLOR = EntityDataManager.createKey(EntityAnimalSheep.class, DataSerializers.VARINT);
  private static final DataParameter<Long> SHEARED = EntityDataManager.createKey(EntityAnimalSheep.class, DataSerializers.LONG);

  @SuppressWarnings("unused")
  public EntityAnimalSheep(World worldIn) {
    this(worldIn, Gender.valueOf(RNG.nextBoolean()),

      getRandomGrowth(ConfigAnimal.ENTITY.SHEEP.adulthood, ConfigAnimal.ENTITY.SHEEP.elder),
      EntitySheep.getRandomSheepColor(RNG));
  }

  public EntityAnimalSheep(World worldIn, Gender gender, int birthDay, EnumDyeColor dye) {
    super(worldIn, gender, birthDay);

    setSize(0.9F, 1.3F);
    setDyeColor(dye);
    setShearedTick(0);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeHelper.isOceanic(biome) && !BiomeHelper.isBeach(biome) &&
        (biomeType == BiomeUtils.BiomeType.PLAINS)) {
      return ConfigAnimal.ENTITY.SHEEP.rarity;
    }
    return 0;
  }

  @Override
  public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
    return AnimalGroupingRules.MALE_AND_FEMALES;
  }

  @Override
  public int getMinGroupSize() {
    return 3;
  }

  @Override
  public int getMaxGroupSize() {
    return 5;
  }

  @Override
  protected void entityInit() {
    super.entityInit();
    dataManager.register(DYE_COLOR, 0);
    dataManager.register(SHEARED, 0L);
  }

  @Override
  public long gestationDays() {
    return ConfigAnimal.ENTITY.SHEEP.gestation;
  }

  @Override
  public void birthChildren() {
    int numberOfChildren = ConfigAnimal.ENTITY.SHEEP.babies;
    for (int i = 0; i < numberOfChildren; i++) {
      EntityAnimalSheep baby = new EntityAnimalSheep(world, Gender.valueOf(RNG.nextBoolean()),
        (int) Calendar.PLAYER_TIME.getTotalDays(), getDyeColor());
      baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
      baby.setFamiliarity(
        getFamiliarity() < 0.9F ? getFamiliarity() / 2.0F : getFamiliarity() * 0.9F);
      world.spawnEntity(baby);
    }
  }

  @Override
  public void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
    super.writeEntityToNBT(nbt);
    NBTUtils.setGenericNBTValue(nbt, "shearedTick", getShearedTick());
    NBTUtils.setGenericNBTValue(nbt, "dyecolor", getDyeColor().getMetadata());
  }

  @Override
  public void readEntityFromNBT(@NotNull NBTTagCompound nbt) {
    super.readEntityFromNBT(nbt);
    setShearedTick(nbt.getLong("shearedTick"));
    setDyeColor(EnumDyeColor.byMetadata(nbt.getByte("dyecolor")));
  }

  public long getShearedTick() {
    return dataManager.get(SHEARED);
  }

  public void setShearedTick(long tick) {
    dataManager.set(SHEARED, tick);
  }

  public EnumDyeColor getDyeColor() {
    return EnumDyeColor.byMetadata(dataManager.get(DYE_COLOR));
  }

  public void setDyeColor(EnumDyeColor color) {
    dataManager.set(DYE_COLOR, color.getMetadata());
  }

  @Override
  public double getOldDeathChance() {
    return ConfigAnimal.ENTITY.SHEEP.oldDeathChance;
  }

  @Override
  public boolean processInteract(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (OreDictUtils.contains(stack, "knife")) {
      if (!world.isRemote) {
        if (isReadyForAnimalProduct()) {
          stack.damageItem(1, player);
          ItemStack woolStack = new ItemStack(ItemsAnimal.WOOL.get());
          StackUtils.spawnItemStack(player.world, new BlockPos(posX, posY, posZ), woolStack);
          playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
          setProductsCooldown();
        } else {
          TextComponentTranslation tooltip = getTooltip();
          if (tooltip != null) {
            ModuleAnimal.NETWORK.sendTo(
              new SCPacketSimple(SCPacketSimple.MessageCategory.ANIMAL, tooltip),
              (EntityPlayerMP) player);
          }
        }
      }
      return true;
    } else if (OreDictUtils.contains(stack, "shears")) {
      if (!world.isRemote) {
        if (!isReadyForAnimalProduct()) {
          TextComponentTranslation tooltip = getTooltip();
          if (tooltip != null) {
            ModuleAnimal.NETWORK.sendTo(
              new SCPacketSimple(SCPacketSimple.MessageCategory.ANIMAL, tooltip),
              (EntityPlayerMP) player);
          }
        }
      }
      return false; // Process done in #onSheared by vanilla
    } else {
      return super.processInteract(player, hand);
    }
  }

  @Override
  protected boolean eatFood(@NotNull ItemStack stack, EntityPlayer player) {
    // Refuses to eat rotten stuff
    ICapabilityFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
    if (cap != null) {
      if (cap.isRotten()) {
        return false;
      }
    }
    return super.eatFood(stack, player);
  }

  @Override
  public float getAdultFamiliarityCap() {
    return 0.35F;
  }

  @Override
  public int getDaysToAdulthood() {
    return ConfigAnimal.ENTITY.SHEEP.adulthood;
  }

  @Override
  public int getDaysToElderly() {
    return ConfigAnimal.ENTITY.SHEEP.elder;
  }

  @Override
  public boolean isReadyForAnimalProduct() {
    return getAge() != Age.CHILD && hasWool() && getFamiliarity() > 0.15f;
  }

  @Override
  public List<ItemStack> getProducts() {
    // Only white for now
    return Collections.singletonList(new ItemStack(ItemsAnimal.WOOL.get()));
  }

  @Override
  public void setProductsCooldown() {
    setShearedTick(Calendar.PLAYER_TIME.getTicks());
  }

  @Override
  public long getProductsCooldown() {
    return Math.max(0,
      ConfigAnimal.ENTITY.SHEEP.woolTicks + getShearedTick() - Calendar.PLAYER_TIME.getTicks());
  }

  @Override
  public @Nullable TextComponentTranslation getTooltip() {
    if (getAge() == Age.CHILD) {
      return new TextComponentTranslation(ModUtils.localize("tooltip", "animal.product.young"),
        getAnimalName());
    } else if (getFamiliarity() <= 0.15f) {
      return new TextComponentTranslation(
        ModUtils.localize("tooltip", "animal.product.low_familiarity"), getAnimalName());
    } else if (!hasWool()) {
      return new TextComponentTranslation(ModUtils.localize("tooltip", "animal.product.no_wool"),
        getAnimalName());
    }
    return null;
  }

  @Override
  public boolean isShearable(@NotNull ItemStack item, IBlockAccess world, BlockPos pos) {
    return isReadyForAnimalProduct();
  }

  @NotNull
  @Override
  public List<ItemStack> onSheared(@NotNull ItemStack item, IBlockAccess world, BlockPos pos,
                                   int fortune) {
    setProductsCooldown();
    List<ItemStack> products = getProducts();
    // Fortune makes this less random and more towards the maximum (3) amount.
    int i = 1 + fortune + rand.nextInt(3 - Math.min(2, fortune));

    List<ItemStack> ret = new ArrayList<>();
    for (ItemStack stack : products) {
      stack.setCount(i);
      ret.add(stack);
    }
    playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
    return ret;
  }

  public boolean hasWool() {
    return getShearedTick() <= 0 || getProductsCooldown() <= 0;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundEvents.ENTITY_SHEEP_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_SHEEP_DEATH;
  }

  @Override
  protected void initEntityAI() {
    addCommonLivestockAI(this, 1.2D);
    addCommonPreyAI(this, 1.2D);

    tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23D);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_SHEEP_AMBIENT;
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_SHEEP;
  }

  @Override
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
  }

}
