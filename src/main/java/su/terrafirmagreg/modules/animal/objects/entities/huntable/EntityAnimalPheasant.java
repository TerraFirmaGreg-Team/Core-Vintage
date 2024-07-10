package su.terrafirmagreg.modules.animal.objects.entities.huntable;

import su.terrafirmagreg.api.lib.MathConstants;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.IHuntable;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;


import net.dries007.tfc.util.climate.BiomeHelper;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class EntityAnimalPheasant extends EntityAnimalBase implements IHuntable {

    private static final int DAYS_TO_ADULTHOOD = 24;

    //Copy from vanilla's EntityChicken, used by renderer to properly handle wing flap
    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1.0F;

    @SuppressWarnings("unused")
    public EntityAnimalPheasant(World worldIn) {
        this(worldIn, Gender.valueOf(MathConstants.RNG.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
    }

    public EntityAnimalPheasant(World worldIn, Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(0.9F, 0.9F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TROPICAL_FOREST || biomeType == BiomeHelper.BiomeType.TAIGA)) {
            return ConfigAnimal.ENTITIES.PHEASANT.rarity;
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

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
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
    public double getOldDeathChance() {
        return 0;
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
    public Type getType() {
        return Type.OVIPAROUS;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundsAnimal.ANIMAL_PHEASANT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundsAnimal.ANIMAL_PHEASANT_DEATH;
    }

    @Override
    protected void initEntityAI() {
        double speedMult = 1.3D;
        addWildPreyAI(this, speedMult);
        addCommonPreyAI(this, speedMult);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsAnimal.ANIMAL_PHEASANT_SAY;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_PHEASANT;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.14F, 0.9F);
    }
}
