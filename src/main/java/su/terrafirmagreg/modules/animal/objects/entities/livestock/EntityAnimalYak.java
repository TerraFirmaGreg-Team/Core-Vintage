package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
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
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.data.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.data.SoundAnimal;


public class EntityAnimalYak extends EntityAnimalCow implements ILivestock {
	@SuppressWarnings("unused")
	public EntityAnimalYak(World worldIn) {
		this(worldIn, Gender.valueOf(Constants.RANDOM.nextBoolean()), getRandomGrowth(ModuleAnimalConfig.ENTITIES.YAK.adulthood, ModuleAnimalConfig.ENTITIES.YAK.elder));
	}

	public EntityAnimalYak(World worldIn, Gender gender, int birthDay) {
		super(worldIn, gender, birthDay);
		this.setSize(1.4F, 1.4F);
	}

	@Override
	public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
		BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
		if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
				(biomeType == BiomeHelper.BiomeType.TAIGA)) {
			return ModuleAnimalConfig.ENTITIES.YAK.rarity;
		}
		return 0;
	}

	@Override
	public void birthChildren() {
		int numberOfChildren = ModuleAnimalConfig.ENTITIES.YAK.babies;
		for (int i = 0; i < numberOfChildren; i++) {
			EntityAnimalYak baby = new EntityAnimalYak(this.world, Gender.valueOf(Constants.RANDOM.nextBoolean()), (int) CalendarTFC.PLAYER_TIME.getTotalDays());
			baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
			baby.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
			this.world.spawnEntity(baby);
		}
	}

	@Override
	public long gestationDays() {
		return ModuleAnimalConfig.ENTITIES.YAK.gestation;
	}

	@Override
	public double getOldDeathChance() {
		return ModuleAnimalConfig.ENTITIES.YAK.oldDeathChance;
	}

	@Override
	public float getAdultFamiliarityCap() {
		return 0.35F;
	}

	@Override
	public int getDaysToAdulthood() {
		return ModuleAnimalConfig.ENTITIES.YAK.adulthood;
	}

	@Override
	public int getDaysToElderly() {
		return ModuleAnimalConfig.ENTITIES.YAK.elder;
	}

	@Override
	public long getProductsCooldown() {
		return Math.max(0, ModuleAnimalConfig.ENTITIES.YAK.milkTicks + getMilkedTick() - CalendarTFC.PLAYER_TIME.getTicks());
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundAnimal.ANIMAL_YAK_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundAnimal.ANIMAL_YAK_DEATH;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundAnimal.ANIMAL_YAK_SAY;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTablesAnimal.ANIMALS_YAK;
	}

	@Override
	// Equivalent sound
	protected void playStepSound(BlockPos pos, Block blockIn) {
		playSound(SoundEvents.ENTITY_COW_STEP, 0.14F, 0.9F);
	}

}
