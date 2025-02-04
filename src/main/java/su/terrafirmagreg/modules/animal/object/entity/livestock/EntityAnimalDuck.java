package su.terrafirmagreg.modules.animal.object.entity.livestock;

import su.terrafirmagreg.api.helper.BiomeHelper;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;
import su.terrafirmagreg.modules.core.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.api.util.MathUtils.RNG;

/**
 * A Chicken of the colder regions! Actually, ducks takes longer to reach maturity, but hey, they are cute!
 */

public class EntityAnimalDuck extends EntityAnimalChicken implements ILivestock {

  public EntityAnimalDuck(World worldIn) {
    this(worldIn, Gender.valueOf(RNG.nextBoolean()),
      getRandomGrowth(ConfigAnimal.ENTITY.DUCK.adulthood, ConfigAnimal.ENTITY.DUCK.elder));
  }

  public EntityAnimalDuck(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    this.setSize(0.8F, 0.9F);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity,
                            float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeHelper.isOceanic(biome) && !BiomeHelper.isBeach(biome) &&
        (biomeType == BiomeUtils.BiomeType.PLAINS
         || biomeType == BiomeUtils.BiomeType.TROPICAL_FOREST)) {
      return ConfigAnimal.ENTITY.DUCK.rarity;
    }
    return 0;
  }

  @Override
  public int getDaysToAdulthood() {
    return ConfigAnimal.ENTITY.DUCK.adulthood;
  }

  @Override
  public int getDaysToElderly() {
    return ConfigAnimal.ENTITY.DUCK.elder;
  }

  @Override
  public List<ItemStack> getProducts() {
    List<ItemStack> eggs = new ArrayList<>();
    ItemStack egg = new ItemStack(Items.EGG);
    if (this.isFertilized()) {
      var cap = CapabilityEgg.get(egg);
      if (cap != null) {
        EntityAnimalDuck chick = new EntityAnimalDuck(this.world);
        chick.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F
                                                          : this.getFamiliarity() * 0.9F);
        cap.setFertilized(chick,
          ConfigAnimal.ENTITY.DUCK.hatch + Calendar.PLAYER_TIME.getTotalDays());
      }
    }
    eggs.add(egg);
    return eggs;
  }

  @Override
  public long getProductsCooldown() {
    return Math.max(0,
      ConfigAnimal.ENTITY.DUCK.eggTicks + getLaidTicks() - Calendar.PLAYER_TIME.getTicks());
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundsAnimal.ANIMAL_DUCK_HURT.get();
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundsAnimal.ANIMAL_DUCK_DEATH.get();
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return RNG.nextInt(100) < 5 ? SoundsAnimal.ANIMAL_DUCK_CRY.get() : SoundsAnimal.ANIMAL_DUCK_SAY.get();
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_DUCK;
  }

  @Override
  protected void playStepSound(BlockPos pos, Block blockIn) {
    // Same sound, no need to create another
    this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
  }

  @Override
  public double getOldDeathChance() {
    return ConfigAnimal.ENTITY.DUCK.oldDeathChance;
  }
}
