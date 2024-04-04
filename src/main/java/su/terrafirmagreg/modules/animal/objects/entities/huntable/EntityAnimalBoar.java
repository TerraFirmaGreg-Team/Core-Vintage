package su.terrafirmagreg.modules.animal.objects.entities.huntable;

import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
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
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.lib.Constants;
import su.terrafirmagreg.modules.animal.ModuleAnimalConfig;
import su.terrafirmagreg.modules.animal.api.type.IHuntable;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.data.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.data.SoundAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalMammal;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;


public class EntityAnimalBoar extends EntityAnimalMammal implements IHuntable {
	private static final int DAYS_TO_ADULTHOOD = 104;

	@SuppressWarnings("unused")
	public EntityAnimalBoar(World worldIn) {
		this(worldIn, Gender.valueOf(Constants.RANDOM.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
	}

	public EntityAnimalBoar(World worldIn, Gender gender, int birthDay) {
		super(worldIn, gender, birthDay);
		this.setSize(0.9F, 1.0F);
	}

	@Override
	public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
		BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
		if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
				(biomeType == BiomeHelper.BiomeType.PLAINS || biomeType == BiomeHelper.BiomeType.SAVANNA
						|| biomeType == BiomeHelper.BiomeType.TROPICAL_FOREST)) {
			return ModuleAnimalConfig.ENTITIES.BOAR.rarity;
		}
		return 0;
	}

	@Override
	public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
		return AnimalGroupingRules.ELDER_AND_POPULATION;
	}

	@Override
	public int getMinGroupSize() {
		return 2;
	}

	@Override
	public int getMaxGroupSize() {
		return 4;
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
	public void birthChildren() {
		// Not farmable
	}

	@Override
	public long gestationDays() {
		return 0; // not farmable
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
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundAnimal.ANIMAL_BOAR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundAnimal.ANIMAL_BOAR_DEATH;
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
		return SoundAnimal.ANIMAL_BOAR_SAY;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTablesAnimal.ANIMALS_BOAR;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		playSound(SoundEvents.ENTITY_PIG_STEP, 0.16F, 0.9F);
	}
}
