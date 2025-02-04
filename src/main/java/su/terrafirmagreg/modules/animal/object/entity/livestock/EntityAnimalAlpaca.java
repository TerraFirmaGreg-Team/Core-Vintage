package su.terrafirmagreg.modules.animal.object.entity.livestock;

import su.terrafirmagreg.api.helper.BiomeHelper;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;
import su.terrafirmagreg.modules.animal.object.entity.EntityAnimalBase;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * A Sheep of the colder regions! Actually, they produce wool faster, but takes longer to reach maturity, have long gestation periods and only give birth to one
 * individual
 */

public class EntityAnimalAlpaca extends EntityAnimalSheep implements ILivestock {

  @SuppressWarnings("unused")
  public EntityAnimalAlpaca(World worldIn) {
    this(worldIn, Gender.valueOf(MathUtils.RNG.nextBoolean()),

      EntityAnimalBase.getRandomGrowth(
        ConfigAnimal.ENTITY.ALPACA.adulthood,
        ConfigAnimal.ENTITY.ALPACA.elder),
      EntitySheep.getRandomSheepColor(MathUtils.RNG)
    );
  }

  public EntityAnimalAlpaca(World worldIn, Gender gender, int birthDay, EnumDyeColor dye) {
    super(worldIn, gender, birthDay, dye);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeHelper.isOceanic(biome) && !BiomeHelper.isBeach(biome) &&
        (biomeType == BiomeUtils.BiomeType.TAIGA)) {
      return ConfigAnimal.ENTITY.ALPACA.rarity;
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
  public long gestationDays() {
    return ConfigAnimal.ENTITY.ALPACA.gestation;
  }

  @Override
  public void birthChildren() {
    int numberOfChildren = ConfigAnimal.ENTITY.ALPACA.babies;
    for (int i = 0; i < numberOfChildren; i++) {
      EntityAnimalAlpaca baby = new EntityAnimalAlpaca(world,
        Gender.valueOf(MathUtils.RNG.nextBoolean()),
        (int) Calendar.PLAYER_TIME.getTotalDays(), getDyeColor());
      baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
      baby.setFamiliarity(
        getFamiliarity() < 0.9F ? getFamiliarity() / 2.0F : getFamiliarity() * 0.9F);
      world.spawnEntity(baby);
    }
  }

  @Override
  public double getOldDeathChance() {
    return ConfigAnimal.ENTITY.ALPACA.oldDeathChance;
  }

  @Override
  public float getAdultFamiliarityCap() {
    return 0.35F;
  }

  @Override
  public int getDaysToAdulthood() {
    return ConfigAnimal.ENTITY.ALPACA.adulthood;
  }

  @Override
  public int getDaysToElderly() {
    return ConfigAnimal.ENTITY.ALPACA.elder;
  }

  @Override
  public long getProductsCooldown() {
    return Math.max(0, ConfigAnimal.ENTITY.ALPACA.woolTicks + getShearedTick()
                       - Calendar.PLAYER_TIME.getTicks());
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundsAnimal.ANIMAL_ALPACA_HURT.get();
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundsAnimal.ANIMAL_ALPACA_DEATH.get();
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return MathUtils.RNG.nextInt(100) < 5 ? SoundsAnimal.ANIMAL_ALPACA_CRY.get()
                                          : SoundsAnimal.ANIMAL_ALPACA_SAY.get();
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_ALPACA;
  }

  @Override
  protected void playStepSound(BlockPos pos, Block blockIn) {
    this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15F, 1.0F);
  }
}
