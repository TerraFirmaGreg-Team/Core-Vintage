package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import su.terrafirmagreg.Tags;
import su.terrafirmagreg.api.lib.Constants;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.animal.ModuleAnimalConfig;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.data.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.data.SoundAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.objects.entities.TFCEntities;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAIFindNest;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import net.dries007.tfc.api.capability.egg.CapabilityEgg;
import net.dries007.tfc.api.capability.egg.IEgg;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class EntityAnimalChicken extends EntityAnimalBase implements ILivestock {

    //The last time(in ticks) this chicken has laid eggs
    private static final DataParameter<Long> LAID = EntityDataManager.createKey(EntityAnimalChicken.class, TFCEntities.getLongDataSerializer());
    //Copy from vanilla's EntityChicken, used by renderer to properly handle wing flap
    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1.0F;

    public EntityAnimalChicken(World worldIn) {
        this(worldIn, Gender.valueOf(Constants.RANDOM.nextBoolean()),
                getRandomGrowth(ModuleAnimalConfig.ENTITIES.CHICKEN.adulthood, ModuleAnimalConfig.ENTITIES.CHICKEN.elder));
    }

    public EntityAnimalChicken(World worldIn, Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(0.6F, 0.8F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.PLAINS)) {
            return ModuleAnimalConfig.ENTITIES.CHICKEN.rarity;
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
    public float getAdultFamiliarityCap() {
        return 0.45F;
    }

    @Override
    public int getDaysToAdulthood() {
        return ModuleAnimalConfig.ENTITIES.CHICKEN.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ModuleAnimalConfig.ENTITIES.CHICKEN.elder;
    }

    @Override
    public Type getType() {
        return Type.OVIPAROUS;
    }

    @Override
    public boolean isReadyForAnimalProduct() {
        // Is ready for laying eggs?
        return this.getFamiliarity() > 0.15f && hasEggs();
    }

    @Override
    public List<ItemStack> getProducts() {
        List<ItemStack> eggs = new ArrayList<>();
        ItemStack egg = new ItemStack(Items.EGG);
        if (this.isFertilized()) {
            IEgg cap = egg.getCapability(CapabilityEgg.CAPABILITY, null);
            if (cap != null) {
                EntityAnimalChicken chick = new EntityAnimalChicken(this.world);
                chick.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
                cap.setFertilized(chick, ModuleAnimalConfig.ENTITIES.CHICKEN.hatch + CalendarTFC.PLAYER_TIME.getTotalDays());
            }
        }
        eggs.add(egg);
        return eggs;
    }

    @Override
    public void setProductsCooldown() {
        this.setLaidTicks(CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    public long getProductsCooldown() {
        return Math.max(0, ModuleAnimalConfig.ENTITIES.CHICKEN.eggTicks + getLaidTicks() - CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    public TextComponentTranslation getTooltip() {
        if (this.getGender() == Gender.MALE) {
            return new TextComponentTranslation(Tags.MOD_ID + ".tooltip.animal.product.male_egg");
        } else if (this.getAge() == Age.OLD) {
            return new TextComponentTranslation(Tags.MOD_ID + ".tooltip.animal.product.old", getAnimalName());
        } else if (this.getAge() == Age.CHILD) {
            return new TextComponentTranslation(Tags.MOD_ID + ".tooltip.animal.product.young", getAnimalName());
        } else if (getFamiliarity() <= 0.15f) {
            return new TextComponentTranslation(Tags.MOD_ID + ".tooltip.animal.product.low_familiarity", getAnimalName());
        } else if (!hasEggs()) {
            return new TextComponentTranslation(Tags.MOD_ID + ".tooltip.animal.product.no_egg", getAnimalName());
        }
        return null;
    }

    public long getLaidTicks() {
        return dataManager.get(LAID);
    }

    protected void setLaidTicks(long ticks) {
        dataManager.set(LAID, ticks);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }

    @Override
    protected void initEntityAI() {
        addCommonLivestockAI(this, 1.3D);
        addCommonPreyAI(this, 1.3D);

        this.tasks.addTask(5, new EntityAnimalAIFindNest(this, 1D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_CHICKEN;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    protected boolean hasEggs() {
        return this.getGender() == Gender.FEMALE && this.getAge() == Age.ADULT && (getLaidTicks() <= 0 || getProductsCooldown() <= 0);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        getDataManager().register(LAID, 0L);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getClass() == EntityAnimalChicken.class && this.getGender() == Gender.MALE && !this.world.isRemote && !this.isChild() &&
                CalendarTFC.CALENDAR_TIME.getHourOfDay() == 6 && rand.nextInt(600) == 0) {
            this.world.playSound(null, this.getPosition(), SoundAnimal.ANIMAL_ROOSTER_CRY, SoundCategory.AMBIENT, 0.8f, 1.0f);
        }
        this.oFlap = this.wingRotation;
        this.oFlapSpeed = this.destPos;
        this.destPos = (float) ((double) this.destPos + (double) (this.onGround ? -1 : 4) * 0.3D);
        this.destPos = MathHelper.clamp(this.destPos, 0.0F, 1.0F);

        if (!this.onGround && this.wingRotDelta < 1.0F) {
            this.wingRotDelta = 1.0F;
        }

        this.wingRotDelta = (float) ((double) this.wingRotDelta * 0.9D);

        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.6D;
        }

        this.wingRotation += this.wingRotDelta * 2.0F;
    }

    @Override
    public void writeEntityToNBT(@NotNull NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        NBTUtils.setGenericNBTValue(nbt, "laidTicks", getLaidTicks());
    }

    @Override
    public void readEntityFromNBT(@NotNull NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.setLaidTicks(nbt.getLong("laidTicks"));
    }

    @Override
    public double getOldDeathChance() {
        return ModuleAnimalConfig.ENTITIES.CHICKEN.oldDeathChance;
    }
}
