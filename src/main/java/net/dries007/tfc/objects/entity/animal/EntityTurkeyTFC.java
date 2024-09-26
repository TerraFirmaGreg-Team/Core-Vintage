package net.dries007.tfc.objects.entity.animal;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.util.climate.BiomeHelper;

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

import org.jetbrains.annotations.Nullable;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.data.MathConstants;
import su.terrafirmagreg.modules.animal.api.type.IHuntable;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class EntityTurkeyTFC extends su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase implements IHuntable {

  private static final int DAYS_TO_ADULTHOOD = 32;

  public float wingRotation;
  public float destPos;
  public float oFlapSpeed;
  public float oFlap;
  public float wingRotDelta = 1.0F;

  public EntityTurkeyTFC(World worldIn) {
    this(worldIn, Gender.valueOf(MathConstants.RNG.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
  }

  public EntityTurkeyTFC(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    this.setSize(0.9F, 1.0F);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
    BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
        (biomeType == BiomeHelper.BiomeType.PLAINS || biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST)) {
      return ConfigTFC.Animals.TURKEY.rarity;
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
    return SoundsAnimal.ANIMAL_TURKEY_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundsAnimal.ANIMAL_TURKEY_DEATH;
  }

  @Override
  protected void initEntityAI() {
    double speedMult = 1.3D;
    EntityAnimalBase.addWildPreyAI(this, speedMult);
    EntityAnimalBase.addCommonPreyAI(this, speedMult);
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundsAnimal.ANIMAL_TURKEY_SAY;
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
