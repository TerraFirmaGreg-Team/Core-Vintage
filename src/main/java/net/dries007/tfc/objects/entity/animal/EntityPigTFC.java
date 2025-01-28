package net.dries007.tfc.objects.entity.animal;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.types.ILivestock;
import net.dries007.tfc.objects.LootTablesTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

@ParametersAreNonnullByDefault
public class EntityPigTFC extends EntityAnimalMammal implements ILivestock {

  @SuppressWarnings("unused")
  public EntityPigTFC(World worldIn) {
    this(worldIn, Gender.valueOf(Constants.RNG.nextBoolean()), getRandomGrowth(ConfigTFC.Animals.PIG.adulthood, ConfigTFC.Animals.PIG.elder));
  }

  public EntityPigTFC(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    setSize(0.9F, 0.9F);
  }

  @Override
  public double getOldDeathChance() {
    return ConfigTFC.Animals.PIG.oldDeathChance;
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
        (biomeType == BiomeUtils.BiomeType.PLAINS || biomeType == BiomeUtils.BiomeType.TROPICAL_FOREST)) {
      return ConfigTFC.Animals.PIG.rarity;
    }
    return 0;
  }

  @Override
  public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
    return AnimalGroupingRules.MALE_AND_FEMALES;
  }

  @Override
  public int getMinGroupSize() {
    return 4;
  }

  @Override
  public int getMaxGroupSize() {
    return 5;
  }

  @Override
  public void birthChildren() {
    int numberOfChildren = ConfigTFC.Animals.PIG.babies;
    for (int i = 0; i < numberOfChildren; i++) {
      EntityPigTFC baby = new EntityPigTFC(world, Gender.valueOf(Constants.RNG.nextBoolean()), (int) Calendar.PLAYER_TIME.getTotalDays());
      baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
      baby.setFamiliarity(getFamiliarity() < 0.9F ? getFamiliarity() / 2.0F : getFamiliarity() * 0.9F);
      world.spawnEntity(baby);
    }
  }

  @Override
  public long gestationDays() {
    return ConfigTFC.Animals.PIG.gestation;
  }

  @Override
  public float getAdultFamiliarityCap() {
    return 0.35F;
  }

  @Override
  public int getDaysToAdulthood() {
    return ConfigTFC.Animals.PIG.adulthood;
  }

  @Override
  public int getDaysToElderly() {
    return ConfigTFC.Animals.PIG.elder;
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundEvents.ENTITY_PIG_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_PIG_DEATH;
  }

  @Override
  protected void initEntityAI() {
    EntityAnimalTFC.addCommonLivestockAI(this, 1.3D);
    EntityAnimalTFC.addCommonPreyAI(this, 1.3D);

    tasks.addTask(5, new EntityAIFollowParent(this, 1.1D));
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_PIG_AMBIENT;
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTablesTFC.ANIMALS_PIG;
  }

  @Override
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
  }
}
