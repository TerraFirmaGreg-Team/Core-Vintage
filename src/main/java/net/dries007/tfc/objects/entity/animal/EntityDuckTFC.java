package net.dries007.tfc.objects.entity.animal;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.core.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.modules.core.capabilities.egg.ICapabilityEgg;
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

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.types.ILivestock;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.objects.LootTablesTFC;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

/**
 * A Chicken of the colder regions! Actually, ducks takes longer to reach maturity, but hey, they are cute!
 */
@ParametersAreNonnullByDefault
public class EntityDuckTFC extends EntityChickenTFC implements ILivestock {

  public EntityDuckTFC(World worldIn) {
    this(worldIn, Gender.valueOf(Constants.RNG.nextBoolean()), getRandomGrowth(ConfigTFC.Animals.DUCK.adulthood, ConfigTFC.Animals.DUCK.elder));
  }

  public EntityDuckTFC(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    this.setSize(0.8F, 0.9F);
  }

  @Override
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
        (biomeType == BiomeUtils.BiomeType.PLAINS || biomeType == BiomeUtils.BiomeType.TROPICAL_FOREST)) {
      return ConfigTFC.Animals.DUCK.rarity;
    }
    return 0;
  }

  @Override
  public int getDaysToAdulthood() {
    return ConfigTFC.Animals.DUCK.adulthood;
  }

  @Override
  public int getDaysToElderly() {
    return ConfigTFC.Animals.DUCK.elder;
  }

  @Override
  public List<ItemStack> getProducts() {
    List<ItemStack> eggs = new ArrayList<>();
    ItemStack egg = new ItemStack(Items.EGG);
    if (this.isFertilized()) {
      ICapabilityEgg cap = egg.getCapability(CapabilityEgg.CAPABILITY, null);
      if (cap != null) {
        EntityDuckTFC chick = new EntityDuckTFC(this.world);
        chick.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
        cap.setFertilized(chick, ConfigTFC.Animals.DUCK.hatch + Calendar.PLAYER_TIME.getTotalDays());
      }
    }
    eggs.add(egg);
    return eggs;
  }

  @Override
  public long getProductsCooldown() {
    return Math.max(0, ConfigTFC.Animals.DUCK.eggTicks + getLaidTicks() - Calendar.PLAYER_TIME.getTicks());
  }

  @Override
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return TFCSounds.ANIMAL_DUCK_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return TFCSounds.ANIMAL_DUCK_DEATH;
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return Constants.RNG.nextInt(100) < 5 ? TFCSounds.ANIMAL_DUCK_CRY : TFCSounds.ANIMAL_DUCK_SAY;
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTablesTFC.ANIMALS_DUCK;
  }

  @Override
  protected void playStepSound(BlockPos pos, Block blockIn) {
    // Same sound, no need to create another
    this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
  }

  @Override
  public double getOldDeathChance() {
    return ConfigTFC.Animals.DUCK.oldDeathChance;
  }
}
