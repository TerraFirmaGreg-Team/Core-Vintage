package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.api.type.IRidable;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.objects.entities.TFCEntities;
import su.terrafirmagreg.modules.core.network.SCPacketSimpleMessage;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
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


import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static su.terrafirmagreg.api.lib.MathConstants.RNG;

@MethodsReturnNonnullByDefault
public class EntityAnimalHorse extends EntityHorse implements IAnimal, ILivestock, IRidable {

    //Values that has a visual effect on client
    private static final DataParameter<Boolean> GENDER = EntityDataManager.createKey(EntityAnimalHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BIRTHDAY = EntityDataManager.createKey(EntityAnimalHorse.class, DataSerializers.VARINT);
    private static final DataParameter<Float> FAMILIARITY = EntityDataManager.createKey(EntityAnimalHorse.class, DataSerializers.FLOAT);
    //Is this female fertilized?
    private static final DataParameter<Boolean> FERTILIZED = EntityDataManager.createKey(EntityAnimalHorse.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HALTER = EntityDataManager.createKey(EntityAnimalHorse.class, DataSerializers.BOOLEAN);
    // The time(in days) this entity became pregnant
    private static final DataParameter<Long> PREGNANT_TIME = EntityDataManager.createKey(EntityAnimalHorse.class,
            TFCEntities.getLongDataSerializer());
    private long lastFed; //Last time(in days) this entity was fed
    private long lastFDecay; //Last time(in days) this entity's familiarity had decayed
    private long matingTime; //The last time(in ticks) this male tried fertilizing females
    private long lastDeath; //Last time(in days) this entity checked for dying of old age
    private boolean birthMule;
    private float geneJump, geneHealth, geneSpeed; // Basic genetic selection based on vanilla's horse offspring
    private int geneHorseVariant;

    public EntityAnimalHorse(World world) {
        this(world, Gender.valueOf(RNG.nextBoolean()),
                EntityAnimalBase.getRandomGrowth(ConfigAnimal.ENTITIES.HORSE.adulthood, ConfigAnimal.ENTITIES.HORSE.elder));
    }

    public EntityAnimalHorse(World world, Gender gender, int birthDay) {
        super(world);
        this.setGender(gender);
        this.setBirthDay(birthDay);
        this.setFamiliarity(0);
        this.setGrowingAge(0); //We don't use this
        this.matingTime = CalendarTFC.PLAYER_TIME.getTicks();
        this.lastDeath = CalendarTFC.PLAYER_TIME.getTotalDays();
        this.lastFDecay = CalendarTFC.PLAYER_TIME.getTotalDays();
        this.setFertilized(false);
        this.birthMule = false;
        this.geneHealth = 0;
        this.geneJump = 0;
        this.geneSpeed = 0;
        this.geneHorseVariant = 0;
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
        return 0.35f;
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
        this.setPregnantTime(CalendarTFC.PLAYER_TIME.getTotalDays());
        // If mating with other types of horse, mark children to be mules
        if (male.getClass() != this.getClass()) {
            this.birthMule = true;
        } else {
            int selection = this.rand.nextInt(9);
            int i;
            if (selection < 4) {
                i = this.getHorseVariant();
            } else if (selection < 8) {
                i = ((EntityHorse) male).getHorseVariant();
            } else {
                // Mutation
                i = this.rand.nextInt(7);
            }
            this.geneHorseVariant = i;
        }
        EntityAnimal father = (EntityAnimal) male;
        this.geneHealth = (float) ((father.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
                .getBaseValue() + this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH)
                .getBaseValue() + this.getModifiedMaxHealth()) / 3.0D);
        this.geneSpeed = (float) ((father.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                .getBaseValue() + this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                .getBaseValue() + this.getModifiedMovementSpeed()) / 3.0D);
        this.geneJump = (float) ((father.getEntityAttribute(AbstractHorse.JUMP_STRENGTH)
                .getBaseValue() + this.getEntityAttribute(AbstractHorse.JUMP_STRENGTH)
                .getBaseValue() + this.getModifiedJumpStrength()) / 3.0D);
    }

    @Override
    public int getDaysToAdulthood() {
        return ConfigAnimal.ENTITIES.HORSE.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ConfigAnimal.ENTITIES.HORSE.elder;
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

    public boolean isHalter() {
        return dataManager.get(HALTER);
    }

    public void setHalter(boolean value) {
        dataManager.set(HALTER, value);
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
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST || biomeType == BiomeHelper.BiomeType.PLAINS)) {
            return ConfigAnimal.ENTITIES.HORSE.rarity;
        }
        return 0;
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

    public long getPregnantTime() {
        return dataManager.get(PREGNANT_TIME);
    }

    public void setPregnantTime(long pregnantTime) {
        dataManager.set(PREGNANT_TIME, pregnantTime);
    }

    public long gestationDays() {
        return ConfigAnimal.ENTITIES.HORSE.gestation;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        getDataManager().register(GENDER, true);
        getDataManager().register(BIRTHDAY, 0);
        getDataManager().register(FAMILIARITY, 0f);
        getDataManager().register(FERTILIZED, false);
        getDataManager().register(PREGNANT_TIME, -1L);
        getDataManager().register(HALTER, false);
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
        NBTUtils.setGenericNBTValue(nbt, "birthMule", birthMule);
        NBTUtils.setGenericNBTValue(nbt, "geneSpeed", geneSpeed);
        NBTUtils.setGenericNBTValue(nbt, "geneJump", geneJump);
        NBTUtils.setGenericNBTValue(nbt, "geneHealth", geneHealth);
        NBTUtils.setGenericNBTValue(nbt, "geneHorseVariant", geneHorseVariant);
        NBTUtils.setGenericNBTValue(nbt, "halter", isHalter());
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
        this.birthMule = nbt.getBoolean("birthMule");
        this.geneSpeed = nbt.getFloat("geneSpeed");
        this.geneJump = nbt.getFloat("geneJump");
        this.geneHealth = nbt.getFloat("geneHealth");
        this.geneHorseVariant = nbt.getInteger("geneHorseVariant");
        this.setHalter(nbt.getBoolean("halter"));
    }

    @Override
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_HORSE;
    }

    @Override
    public boolean processInteract(@NotNull EntityPlayer player, @NotNull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!stack.isEmpty()) {
            if (stack.getItem() == Items.SPAWN_EGG) {
                return super.processInteract(player, hand); // Let vanilla spawn a baby
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
                        setScaleForAge(this.isChild());
                        lastFed = CalendarTFC.PLAYER_TIME.getTotalDays();
                        lastFDecay = lastFed; //No decay needed
                        this.consumeItemFromStack(player, stack);
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
                } else {
                    if (!this.world.isRemote) {
                        //Show tooltips
                        if (this.isFertilized() && this.getType() == Type.MAMMAL) {
                            ModuleAnimal.PACKET_SERVICE.sendTo(SCPacketSimpleMessage.translateMessage(
                                    SCPacketSimpleMessage.MessageCategory.ANIMAL,
                                    ModUtils.localize("tooltip", "animal.mating.pregnant"), getAnimalName()), (EntityPlayerMP) player);
                        }
                    }
                }
            }
        }
        return super.processInteract(player, hand);
    }

    @Override
    public boolean canMateWith(EntityAnimal otherAnimal) {
        if (otherAnimal instanceof EntityAnimalHorse || otherAnimal instanceof EntityAnimalDonkey) {
            IAnimal other = (IAnimal) otherAnimal;
            return this.getGender() != other.getGender() && this.isInLove() && otherAnimal.isInLove();
        }
        return false;
    }

    @Nullable
    @Override
    public EntityAgeable createChild(@NotNull EntityAgeable other) {
        // Cancel default vanilla behaviour (immediately spawns children of this animal) and set this female as fertilized
        if (other != this && this.getGender() == Gender.FEMALE && other instanceof IAnimal) {
            this.setFertilized(true);
            this.resetInLove();
            this.onFertilized((IAnimal) other);
        } else if (other == this) {
            // Only called if this animal is interacted with a spawn egg
            EntityAnimalHorse baby = new EntityAnimalHorse(this.world, Gender.valueOf(RNG.nextBoolean()),
                    (int) CalendarTFC.PLAYER_TIME.getTotalDays());
            this.setOffspringAttributes(this, baby);
            baby.setHorseVariant(this.getHorseVariant());
            return baby;
        }
        return null;
    }

    @Override
    protected void initEntityAI() {
        EntityAnimalBase.addCommonLivestockAI(this, 1.2D);
        EntityAnimalBase.addCommonPreyAI(this, 1.2);
        tasks.addTask(2, new EntityAIMate(this, 1.0D, EntityAnimalDonkey.class)); // Missing donkeys (for mules)
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
        if (this.isHalter()) {
            super.mountTo(player);
        }
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
                if (findFemaleMate()) {
                    this.setInLove(null);
                }
            }
            if (this.getAge() == Age.OLD && lastDeath < CalendarTFC.PLAYER_TIME.getTotalDays()) {
                this.lastDeath = CalendarTFC.PLAYER_TIME.getTotalDays();
                // Randomly die of old age, tied to entity UUID and calendar time
                final Random random = new Random(
                        this.entityUniqueID.getMostSignificantBits() * CalendarTFC.PLAYER_TIME.getTotalDays());
                if (random.nextDouble() < ConfigAnimal.ENTITIES.HORSE.oldDeathChance) {
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

    /**
     * Find and charms a near female horse/donkey Used by males to try mating with females
     *
     * @return true if found and charmed a female
     */
    private boolean findFemaleMate() {
        List<AbstractHorse> list = this.world.getEntitiesWithinAABB(AbstractHorse.class, this.getEntityBoundingBox()
                .grow(8.0D));
        for (AbstractHorse ent : list) {
            if (ent instanceof EntityAnimalHorse || ent instanceof EntityAnimalDonkey) {
                IAnimal animal = (IAnimal) ent;
                if (animal.getGender() == Gender.FEMALE && animal.isReadyToMate() && !ent.isInLove()) {
                    ent.setInLove(null);
                    return true;
                }
            }
        }
        return false;
    }

    private void birthChildren() {
        int numberOfChildren = ConfigAnimal.ENTITIES.HORSE.babies;
        for (int i = 0; i < numberOfChildren; i++) {
            // Birth one animal
            IAnimal baby;
            if (birthMule) {
                baby = new EntityAnimalMule(world);
            } else {
                baby = new EntityAnimalHorse(world);
                ((EntityAnimalHorse) baby).setHorseVariant(geneHorseVariant);
            }
            baby.setBirthDay((int) CalendarTFC.PLAYER_TIME.getTotalDays());
            baby.setFamiliarity(getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : getFamiliarity() * 0.9F);
            EntityAnimal animal = (EntityAnimal) baby;
            animal.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
            if (geneHealth > 0) {
                animal.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(geneHealth);
            }
            if (geneSpeed > 0) {
                animal.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(geneSpeed);
            }
            if (geneJump > 0) {
                animal.getEntityAttribute(AbstractHorse.JUMP_STRENGTH).setBaseValue(geneJump);
            }
            world.spawnEntity(animal);
        }
        geneJump = 0;
        geneSpeed = 0;
        geneJump = 0;
        birthMule = false;
    }
}
