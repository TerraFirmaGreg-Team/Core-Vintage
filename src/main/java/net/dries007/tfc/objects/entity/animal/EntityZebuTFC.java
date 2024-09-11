package net.dries007.tfc.objects.entity.animal;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.data.MathConstants;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.climate.BiomeHelper;

import org.jetbrains.annotations.Nullable;

public class EntityZebuTFC extends EntityCowTFC implements ILivestock {

  @SuppressWarnings("unused")
  public EntityZebuTFC(World worldIn) {
    this(worldIn, Gender.valueOf(MathConstants.RNG.nextBoolean()),
            getRandomGrowth(ConfigTFC.Animals.ZEBU.adulthood, ConfigTFC.Animals.ZEBU.elder));
  }

  public EntityZebuTFC(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    this.setSize(1.4F, 1.4F);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
    BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
            (biomeType == BiomeHelper.BiomeType.TROPICAL_FOREST)) {
      return ConfigTFC.Animals.ZEBU.rarity;
    }
    return 0;
  }

  @Override
  public long gestationDays() {
    return ConfigTFC.Animals.ZEBU.gestation;
  }

  @Override
  public void birthChildren() {
    int numberOfChildren = ConfigTFC.Animals.ZEBU.babies;
    for (int i = 0; i < numberOfChildren; i++) {
      EntityZebuTFC baby = new EntityZebuTFC(this.world, Gender.valueOf(MathConstants.RNG.nextBoolean()),
              (int) Calendar.PLAYER_TIME.getTotalDays());
      baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
      baby.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
      this.world.spawnEntity(baby);
    }
  }

  @Override
  public double getOldDeathChance() {
    return ConfigTFC.Animals.ZEBU.oldDeathChance;
  }

  @Override
  public float getAdultFamiliarityCap() {
    return 0.35F;
  }

  @Override
  public int getDaysToAdulthood() {
    return ConfigTFC.Animals.ZEBU.adulthood;
  }

  @Override
  public int getDaysToElderly() {
    return ConfigTFC.Animals.ZEBU.elder;
  }

  @Override
  public long getProductsCooldown() {
    return Math.max(0, ConfigTFC.Animals.ZEBU.milkTicks + getMilkedTick() - Calendar.PLAYER_TIME.getTicks());
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundsAnimal.ANIMAL_ZEBU_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundsAnimal.ANIMAL_ZEBU_DEATH;
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundsAnimal.ANIMAL_ZEBU_SAY;
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_ZEBU;
  }

  @Override
  // Equivalent sound
  protected void playStepSound(BlockPos pos, Block blockIn) {
    playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
  }
}
