package su.terrafirmagreg.modules.animal.object.entity.huntable;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.IHuntable;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalMammal;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;


import net.dries007.tfc.util.climate.BiomeHelper;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static su.terrafirmagreg.data.MathConstants.RNG;

public class EntityAnimalMongoose extends EntityAnimalMammal implements IHuntable {

  private static final int DAYS_TO_ADULTHOOD = 64;

  @SuppressWarnings("unused")
  public EntityAnimalMongoose(World worldIn) {
    this(worldIn, Gender.valueOf(RNG.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
  }

  public EntityAnimalMongoose(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    this.setSize(0.8F, 0.8F);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity,
          float floraDiversity) {
    BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
            (biomeType == BiomeHelper.BiomeType.DESERT)) {
      return ConfigAnimal.ENTITIES.MONGOOSE.rarity;
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
    return 3;
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
  public long gestationDays() {
    return 0; // not farmable
  }

  @Override
  public void birthChildren() {
    // Not farmable
  }

  @Override
  public double getOldDeathChance() {
    return 0;
  }

  @Override
  public boolean canMateWith(EntityAnimal otherAnimal) {
    return false;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundsAnimal.ANIMAL_MONGOOSE_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundsAnimal.ANIMAL_MONGOOSE_DEATH;
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
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundsAnimal.ANIMAL_MONGOOSE_SAY;
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_MONGOOSE;
  }

  @Override
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_PIG_STEP, 0.14F, 0.9F);
  }
}
