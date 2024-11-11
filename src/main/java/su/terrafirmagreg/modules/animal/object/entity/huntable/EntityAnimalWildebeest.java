package su.terrafirmagreg.modules.animal.object.entity.huntable;

import su.terrafirmagreg.api.helper.BiomeHelper;
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
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.oredict.OreDictionary;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

import static su.terrafirmagreg.api.util.MathUtils.RNG;

public class EntityAnimalWildebeest extends EntityAnimalMammal implements IHuntable {

  private static final int DAYS_TO_ADULTHOOD = 128;

  @SuppressWarnings("unused")
  public EntityAnimalWildebeest(World worldIn) {
    this(worldIn, Gender.valueOf(RNG.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
  }

  public EntityAnimalWildebeest(World worldIn, Gender gender, int birthDay) {
    super(worldIn, gender, birthDay);
    this.setSize(1.1F, 1.5F);
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
  public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity,
                            float floraDiversity) {
    BiomeUtils.BiomeType biomeType = BiomeUtils.getBiomeType(temperature, rainfall, floraDensity);
    if (!BiomeHelper.isOceanicBiome(biome) && !BiomeHelper.isBeachBiome(biome) &&
        (biomeType == BiomeUtils.BiomeType.SAVANNA)) {
      return ConfigAnimal.ENTITY.WILDEBEEST.rarity;
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
  public long gestationDays() {
    return 0;
  }

  @Override
  public void birthChildren() {
    // Not farmable
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
  protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
    return SoundsAnimal.ANIMAL_WILDEBEEST_HURT;
  }

  @Override
  protected SoundEvent getDeathSound() {
    return SoundsAnimal.ANIMAL_WILDEBEEST_DEATH;
  }

  @Override
  protected void initEntityAI() {
    double speedMult = 1.4D;
    addWildPreyAI(this, speedMult);
    addCommonPreyAI(this, speedMult);
    for (ItemStack is : OreDictionary.getOres("dustSult")) {
      Item item = is.getItem();
      this.tasks.addTask(3, new EntityAITempt(this, 1.1D, item, false));
    }
    this.tasks.addTask(5, new EntityAIFollowParent(this, 1.0D));
    this.tasks.addTask(6, new EntityAIEatGrass(this));
  }

  @Override
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.33D);
  }

  @Override
  protected SoundEvent getAmbientSound() {
    return SoundsAnimal.ANIMAL_WILDEBEEST_SAY;
  }

  @Nullable
  protected ResourceLocation getLootTable() {
    return LootTablesAnimal.ANIMALS_WILDEBEEST;
  }

  @Override
  protected void playStepSound(BlockPos pos, Block blockIn) {
    this.playSound(SoundEvents.ENTITY_HORSE_STEP, 0.15F, 0.9F);
  }
}
