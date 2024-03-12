package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import su.terrafirmagreg.modules.animal.ModuleAnimalConfig;
import net.dries007.tfc.api.capability.egg.CapabilityEgg;
import net.dries007.tfc.api.capability.egg.IEgg;
import su.terrafirmagreg.modules.animal.data.LootTablesAnimal;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
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
import su.terrafirmagreg.api.lib.Constants;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.data.SoundAnimal;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;


@ParametersAreNonnullByDefault
public class EntityAnimalQuail extends EntityAnimalChicken implements ILivestock {
	public EntityAnimalQuail(World worldIn) {
		this(worldIn, Gender.valueOf(Constants.RANDOM.nextBoolean()), getRandomGrowth(ModuleAnimalConfig.ENTITIES.QUAIL.adulthood, ModuleAnimalConfig.ENTITIES.QUAIL.elder));
	}

	public EntityAnimalQuail(World worldIn, Gender gender, int birthDay) {
		super(worldIn, gender, birthDay);
		this.setSize(0.7F, 0.7F);
	}

	@Override
	public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
		BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
		if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
				(biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST)) {
			return ModuleAnimalConfig.ENTITIES.QUAIL.rarity;
		}
		return 0;
	}

	@Override
	public int getDaysToAdulthood() {
		return ModuleAnimalConfig.ENTITIES.QUAIL.adulthood;
	}

	@Override
	public int getDaysToElderly() {
		return ModuleAnimalConfig.ENTITIES.QUAIL.elder;
	}

	@Override
	public List<ItemStack> getProducts() {
		List<ItemStack> eggs = new ArrayList<>();
		ItemStack egg = new ItemStack(Items.EGG);
		if (this.isFertilized()) {
			IEgg cap = egg.getCapability(CapabilityEgg.CAPABILITY, null);
			if (cap != null) {
				EntityAnimalQuail chick = new EntityAnimalQuail(this.world);
				chick.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
				cap.setFertilized(chick, ModuleAnimalConfig.ENTITIES.QUAIL.hatch + CalendarTFC.PLAYER_TIME.getTotalDays());
			}
		}
		eggs.add(egg);
		return eggs;
	}

	@Override
	public long getProductsCooldown() {
		return Math.max(0, ModuleAnimalConfig.ENTITIES.QUAIL.eggTicks + getLaidTicks() - CalendarTFC.PLAYER_TIME.getTicks());
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundAnimal.ANIMAL_QUAIL_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundAnimal.ANIMAL_QUAIL_DEATH;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundAnimal.ANIMAL_QUAIL_SAY;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTablesAnimal.ANIMALS_QUAIL;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		// Same sound, no need to create another
		this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.14F, 0.9F);
	}

	@Override
	public double getOldDeathChance() {
		return ModuleAnimalConfig.ENTITIES.QUAIL.oldDeathChance;
	}
}
