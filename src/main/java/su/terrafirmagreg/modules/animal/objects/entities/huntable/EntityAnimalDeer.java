package su.terrafirmagreg.modules.animal.objects.entities.huntable;

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


public class EntityAnimalDeer extends EntityAnimalMammal implements IHuntable {
	private static final int DAYS_TO_ADULTHOOD = 128;

	@SuppressWarnings("unused")
	public EntityAnimalDeer(World worldIn) {
		this(worldIn, Gender.valueOf(Constants.RANDOM.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
	}

	public EntityAnimalDeer(World worldIn, Gender gender, int birthDay) {
		super(worldIn, gender, birthDay);
		this.setSize(1.0F, 1.4F);
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
				(biomeType == BiomeHelper.BiomeType.TAIGA || biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST)) {
			return ModuleAnimalConfig.ENTITIES.DEER.rarity;
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
		return SoundAnimal.ANIMAL_DEER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundAnimal.ANIMAL_DEER_DEATH;
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
		return Constants.RANDOM.nextInt(100) < 5 ? SoundAnimal.ANIMAL_DEER_CRY : SoundAnimal.ANIMAL_DEER_SAY;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTablesAnimal.ANIMALS_DEER;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		this.playSound(SoundEvents.ENTITY_HORSE_STEP, 0.14F, 0.9F);
	}
}
