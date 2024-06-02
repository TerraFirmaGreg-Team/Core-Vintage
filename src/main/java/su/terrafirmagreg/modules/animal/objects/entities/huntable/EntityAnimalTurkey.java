package su.terrafirmagreg.modules.animal.objects.entities.huntable;

import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.IHuntable;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;


import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static su.terrafirmagreg.api.lib.MathConstants.RNG;

public class EntityAnimalTurkey extends EntityAnimalBase implements IHuntable {

    private static final int DAYS_TO_ADULTHOOD = 32;

    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1.0F;

    public EntityAnimalTurkey(World worldIn) {
        this(worldIn, Gender.valueOf(RNG.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
    }

    public EntityAnimalTurkey(World worldIn, Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(0.9F, 1.0F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.PLAINS || biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST)) {
            return ConfigAnimal.ENTITIES.TURKEY.rarity;
        }
        return 0;
    }

    @Override
    public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
        return AnimalGroupingRules.ELDER_AND_POPULATION;
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
        return SoundAnimal.ANIMAL_TURKEY_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundAnimal.ANIMAL_TURKEY_DEATH;
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
        return SoundAnimal.ANIMAL_TURKEY_SAY;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_TURKEY;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.16F, 1.1F);
    }
}
