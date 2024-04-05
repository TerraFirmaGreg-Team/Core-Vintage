package net.dries007.tfc.objects.entity.animal;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.modules.animal.api.type.IHuntable;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.data.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.data.SoundAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalMammal;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;


public class EntityWildebeestTFC extends EntityAnimalMammal implements IHuntable {
	private static final int DAYS_TO_ADULTHOOD = 128;

	@SuppressWarnings("unused")
	public EntityWildebeestTFC(World worldIn) {
		this(worldIn, Gender.valueOf(Constants.RNG.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
	}

	public EntityWildebeestTFC(World worldIn, Gender gender, int birthDay) {
		super(worldIn, gender, birthDay);
		this.setSize(1.1F, 1.5F);
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		return false;
	}

	@Override
	public double getOldDeathChance() {
		return 0;
	}

	@Override
	public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
		BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
		if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
				(biomeType == BiomeHelper.BiomeType.SAVANNA)) {
			return ConfigTFC.Animals.WILDEBEEST.rarity;
		}
		return 0;
	}

	@Override
	public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {return AnimalGroupingRules.ELDER_AND_POPULATION;}

	@Override
	public int getMinGroupSize() {
		return 3;
	}

	@Override
	public int getMaxGroupSize() {
		return 5;
	}

	@Override
	public void birthChildren() {
		// Not farmable
	}

	@Override
	public long gestationDays() {
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
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundAnimal.ANIMAL_WILDEBEEST_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundAnimal.ANIMAL_WILDEBEEST_DEATH;
	}

	@Override
	protected void initEntityAI() {
		double speedMult = 1.4D;
		EntityAnimalBase.addWildPreyAI(this, speedMult);
		EntityAnimalBase.addCommonPreyAI(this, speedMult);

		this.tasks.addTask(3, new EntityAITempt(this, 1.1D, ItemsTFC.SALT, false));

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
		return SoundAnimal.ANIMAL_WILDEBEEST_SAY;
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
