package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.animal.ModuleAnimalConfig;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.objects.entities.TFCEntities;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAITamableAvoidPlayer;
import su.terrafirmagreg.modules.core.network.SCPacketSimpleMessage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
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


import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.BiConsumer;

import static su.terrafirmagreg.api.lib.MathConstants.RNG;

// Changes in config allow placing this animal in livestock and still respawn
public class EntityAnimalWolf extends EntityWolf implements IAnimal, ILivestock {

    //Values that has a visual effect on client
    private static final DataParameter<Boolean> GENDER = EntityDataManager.createKey(EntityAnimalWolf.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BIRTHDAY = EntityDataManager.createKey(EntityAnimalWolf.class, DataSerializers.VARINT);
    private static final DataParameter<Float> FAMILIARITY = EntityDataManager.createKey(EntityAnimalWolf.class, DataSerializers.FLOAT);
    //Is this female fertilized?
    private static final DataParameter<Boolean> FERTILIZED = EntityDataManager.createKey(EntityAnimalWolf.class, DataSerializers.BOOLEAN);
    // The time(in days) this entity became pregnant
    private static final DataParameter<Long> PREGNANT_TIME = EntityDataManager.createKey(EntityAnimalWolf.class, TFCEntities.getLongDataSerializer());
    private long lastFed; //Last time(in days) this entity was fed
    private long lastFDecay; //Last time(in days) this entity's familiarity had decayed
    private long matingTime; //The last time(in ticks) this male tried fertilizing females
    private long lastDeath; //Last time(in days) this entity checked for dying of old age

    @SuppressWarnings("unused")
    public EntityAnimalWolf(World worldIn) {
        this(worldIn, Gender.valueOf(RNG.nextBoolean()),
                EntityAnimalBase.getRandomGrowth(ModuleAnimalConfig.ENTITIES.WOLF.adulthood, ModuleAnimalConfig.ENTITIES.WOLF.elder));
    }

    public EntityAnimalWolf(World worldIn, Gender gender, int birthDay) {
        super(worldIn);
        this.setGender(gender);
        this.setBirthDay(birthDay);
        this.setFamiliarity(0);
        this.setGrowingAge(0); //We don't use this
        this.matingTime = CalendarTFC.PLAYER_TIME.getTicks();
        this.lastDeath = CalendarTFC.PLAYER_TIME.getTotalDays();
        this.lastFDecay = CalendarTFC.PLAYER_TIME.getTotalDays();
        this.setFertilized(false);
        this.setPregnantTime(-1);
        this.setSize(0.6F, 0.85F);
        this.setTamed(false);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.PLAINS || biomeType == BiomeHelper.BiomeType.TAIGA)) {
            return ModuleAnimalConfig.ENTITIES.WOLF.rarity;
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

    public void birthChildren() {
        int numberOfChildren = ModuleAnimalConfig.ENTITIES.WOLF.babies;
        for (int i = 0; i < numberOfChildren; i++) {
            EntityAnimalWolf baby = new EntityAnimalWolf(this.world, Gender.valueOf(RNG.nextBoolean()),
                    (int) CalendarTFC.PLAYER_TIME.getTotalDays());
            baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
            baby.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
            UUID uuid = this.getOwnerId();
            if (uuid != null) {
                baby.setOwnerId(uuid);
                baby.setTamed(true);
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
    public int getBirthDay() {
        return this.dataManager.get(BIRTHDAY);
    }

    @Override
    public void setBirthDay(int value) {
        this.dataManager.set(BIRTHDAY, value);
    }

    @Override
    public float getAdultFamiliarityCap() {
        return 0.35F;
    }

    @Override
    public float getFamiliarity() {
        return this.dataManager.get(FAMILIARITY);
    }

    @Override
    public void setFamiliarity(float value) {
        if (value < 0f) value = 0f;
        if (value > 1f) value = 1f;
        this.dataManager.set(FAMILIARITY, value);
    }

    @Override
    public boolean isFertilized() {
        return dataManager.get(FERTILIZED);
    }

    @Override
    public void setFertilized(boolean value) {
        dataManager.set(FERTILIZED, value);
    }

    @Override
    public void onFertilized(@NotNull IAnimal male) {
        //Mark the day this female became pregnant
        this.setPregnantTime(CalendarTFC.PLAYER_TIME.getTotalDays());
    }

    @Override
    public int getDaysToAdulthood() {
        return ModuleAnimalConfig.ENTITIES.WOLF.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ModuleAnimalConfig.ENTITIES.WOLF.elder;
    }

    @Override
    public boolean isReadyToMate() {
        if (this.getAge() != Age.ADULT || this.getFamiliarity() < 0.3f || this.isFertilized() || this.isHungry())
            return false;
        return this.matingTime + EntityAnimalBase.MATING_COOLDOWN_DEFAULT_TICKS <= CalendarTFC.PLAYER_TIME.getTicks();
    }

    @Override
    public boolean isHungry() {
        return lastFed < CalendarTFC.PLAYER_TIME.getTotalDays();
    }

    @Override
    public Type getType() {
        return Type.MAMMAL;
    }

    @Override
    public TextComponentTranslation getAnimalName() {
        String entityString = EntityList.getEntityString(this);
        return new TextComponentTranslation(ModUtils.localize("animal." + entityString + "." + this.getGender().name()));
    }

    @Override
    public boolean getCanSpawnHere() {
        return this.world.checkNoEntityCollision(getEntityBoundingBox())
                && this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()
                && !this.world.containsAnyLiquid(getEntityBoundingBox())
                && BlockUtils.isGround(this.world.getBlockState(this.getPosition().down()));
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

    @NotNull
    @Override
    public String getName() {
        if (this.hasCustomName()) {
            return this.getCustomNameTag();
        } else {
            return getAnimalName().getFormattedText();
        }
    }

    public long getPregnantTime() {
        return dataManager.get(PREGNANT_TIME);
    }

    public void setPregnantTime(long pregnantTime) {
        dataManager.set(PREGNANT_TIME, pregnantTime);
    }

    public long gestationDays() {
        return ModuleAnimalConfig.ENTITIES.WOLF.gestation;
    }

    @Override
    protected void initEntityAI() {
        //AIBeg task at priority 9 and distance 8 will disable this
        this.tasks.addTask(10, new EntityAnimalAITamableAvoidPlayer<>(this, 7F, 1D, 1.5D));
        super.initEntityAI();

        int priority = 1;
        for (String input : ModuleAnimalConfig.ENTITIES.WOLF.huntCreatures) {
            ResourceLocation key = new ResourceLocation(input);
            EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(key);
            if (entityEntry != null) {
                Class<? extends Entity> entityClass = entityEntry.getEntityClass();
                if (EntityLivingBase.class.isAssignableFrom(entityClass)) {
                    //noinspection unchecked
                    this.targetTasks.addTask(priority++,
                            new EntityAITargetNonTamed<>(this, (Class<EntityLivingBase>) entityClass, false, ent -> true));
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
        getDataManager().register(PREGNANT_TIME, -1L);
        getDataManager().register(FERTILIZED, false);
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        NBTUtils.setGenericNBTValue(nbt, "gender", getGender().toBool());
        NBTUtils.setGenericNBTValue(nbt, "birth", getBirthDay());
        NBTUtils.setGenericNBTValue(nbt, "fed", lastFed);
        NBTUtils.setGenericNBTValue(nbt, "decay", lastFDecay);
        NBTUtils.setGenericNBTValue(nbt, "fertilized", this.isFertilized());
        NBTUtils.setGenericNBTValue(nbt, "mating", matingTime);
        NBTUtils.setGenericNBTValue(nbt, "familiarity", getFamiliarity());
        NBTUtils.setGenericNBTValue(nbt, "lastDeath", lastDeath);
        NBTUtils.setGenericNBTValue(nbt, "pregnant", getPregnantTime());
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setGender(Gender.valueOf(nbt.getBoolean("gender")));
        this.setBirthDay(nbt.getInteger("birth"));
        this.lastFed = nbt.getLong("fed");
        this.lastFDecay = nbt.getLong("decay");
        this.matingTime = nbt.getLong("mating");
        this.setFertilized(nbt.getBoolean("fertilized"));
        this.setFamiliarity(nbt.getFloat("familiarity"));
        this.lastDeath = nbt.getLong("lastDeath");
        this.setPregnantTime(nbt.getLong("pregnant"));
    }

    @Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_WOLF;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.ticksExisted % 100 == 0) {
            setScaleForAge(false);
        }
        if (!this.world.isRemote) {
            if (this.isFertilized() && CalendarTFC.PLAYER_TIME.getTotalDays() >= getPregnantTime() + gestationDays()) {
                birthChildren();
                this.setFertilized(false);
            }

            // Is it time to decay familiarity?
            // If this entity was never fed(eg: new born, wild)
            // or wasn't fed yesterday(this is the starting of the second day)
            if (this.lastFDecay > -1 && this.lastFDecay + 1 < CalendarTFC.PLAYER_TIME.getTotalDays()) {
                float familiarity = getFamiliarity();
                if (familiarity < 0.3f) {
                    familiarity -= 0.02 * (CalendarTFC.PLAYER_TIME.getTotalDays() - this.lastFDecay);
                    this.lastFDecay = CalendarTFC.PLAYER_TIME.getTotalDays();
                    this.setFamiliarity(familiarity);
                }
            }
            if (this.getGender() == Gender.MALE && this.isReadyToMate()) {
                this.matingTime = CalendarTFC.PLAYER_TIME.getTicks();
                EntityAnimalBase.findFemaleMate(this);
            }
            if (this.getAge() == Age.OLD && lastDeath < CalendarTFC.PLAYER_TIME.getTotalDays()) {
                this.lastDeath = CalendarTFC.PLAYER_TIME.getTotalDays();
                // Randomly die of old age, tied to entity UUID and calendar time
                final Random random = new Random(
                        this.entityUniqueID.getMostSignificantBits() * CalendarTFC.PLAYER_TIME.getTotalDays());
                if (random.nextDouble() < ModuleAnimalConfig.ENTITIES.WOLF.oldDeathChance) {
                    this.setDead();
                }
            }
            // Wild animals disappear after 125% lifespan
            if (this.getDaysToElderly() > 0 && this.getFamiliarity() < 0.10F &&
                    (this.getDaysToElderly() + this.getDaysToAdulthood()) * 1.25F <= CalendarTFC.PLAYER_TIME.getTotalDays() - this.getBirthDay()) {
                this.setDead();
            }
        }
    }

    @Override
    public boolean processInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!itemstack.isEmpty()) {
            if (itemstack.getItem() == Items.SPAWN_EGG) {
                return super.processInteract(player, hand); // Let vanilla spawn a baby
            }
            if (this.isFood(itemstack)) {
                if (!this.isAngry() && player.isSneaking() && getAdultFamiliarityCap() > 0.0F) {
                    // Refuses to eat rotten stuff
                    IFood cap = itemstack.getCapability(CapabilityFood.CAPABILITY, null);
                    if (cap != null) {
                        if (cap.isRotten()) {
                            return false;
                        }
                    }
                    if (this.isHungry()) {
                        if (!this.world.isRemote) {
                            lastFed = CalendarTFC.PLAYER_TIME.getTotalDays();
                            lastFDecay = lastFed; //No decay needed
                            this.consumeItemFromStack(player, itemstack);
                            if (this.getAge() == Age.CHILD || this.getFamiliarity() < getAdultFamiliarityCap()) {
                                float familiarity = this.getFamiliarity() + 0.06f;
                                if (this.getAge() != Age.CHILD) {
                                    familiarity = Math.min(familiarity, getAdultFamiliarityCap());
                                }
                                this.setFamiliarity(familiarity);
                            }
                            world.playSound(null, this.getPosition(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.AMBIENT, 1.0F, 1.0F);
                        }
                        return true;
                    } else if (!this.isTamed() && getFamiliarity() >= 0.3f) {
                        if (!this.world.isRemote) {
                            this.consumeItemFromStack(player, itemstack);
                            if (this.rand.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                                this.setTamedBy(player);
                                this.navigator.clearPath();
                                this.setAttackTarget(null);
                                this.aiSit.setSitting(true);
                                this.playTameEffect(true);
                                this.world.setEntityState(this, (byte) 7);
                            } else {
                                this.playTameEffect(false);
                                this.world.setEntityState(this, (byte) 6);
                            }
                        }
                        return true;
                    } else {
                        if (!this.world.isRemote) {
                            //Show tooltips
                            if (this.isFertilized() && this.getType() == Type.MAMMAL) {
                                ModuleAnimal.PACKET_SERVICE.sendTo(
                                        SCPacketSimpleMessage.translateMessage(SCPacketSimpleMessage.MessageCategory.ANIMAL,
                                                ModUtils.localize("tooltip", "animal.mating.pregnant"), getAnimalName()), (EntityPlayerMP) player);
                            }
                        }
                    }
                }
                return false;
            }
        }
        return super.processInteract(player, hand);
    }

    @Nullable
    @Override
    public EntityAnimalWolf createChild(@NotNull EntityAgeable other) {
        // Cancel default vanilla behaviour (immediately spawns children of this animal) and set this female as fertilized
        if (other != this && this.getGender() == Gender.FEMALE && other instanceof IAnimal) {
            this.setFertilized(true);
            this.resetInLove();
            this.onFertilized((IAnimal) other);
        } else if (other == this) {
            // Only called if this animal is interacted with a spawn egg
            // Try to return to vanilla's default method a baby of this animal, as if bred normally
            try {
                EntityAnimalWolf baby = new EntityAnimalWolf(this.world);
                baby.setGender(Gender.valueOf(RNG.nextBoolean()));
                baby.setBirthDay((int) CalendarTFC.PLAYER_TIME.getTotalDays());
                baby.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
                if (this.isTamed()) {
                    baby.setOwnerId(this.getOwnerId());
                    baby.setTamed(true);
                }
                return baby;
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    @Override
    public boolean canMateWith(EntityAnimal otherAnimal) {
        if (otherAnimal.getClass() != this.getClass()) return false;
        EntityAnimalWolf other = (EntityAnimalWolf) otherAnimal;
        return this.getGender() != other.getGender() && this.isInLove() && other.isInLove();
    }
}
