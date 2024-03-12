package su.terrafirmagreg.modules.animal.objects.entities.predator;

import su.terrafirmagreg.modules.animal.ModuleAnimalConfig;
import su.terrafirmagreg.api.lib.Constants;
import su.terrafirmagreg.modules.animal.data.LootTablesAnimal;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.api.type.IPredator;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAIAttackMelee;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAIStandAttack;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAIWanderHuntArea;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EntityAnimalBlackBear extends EntityAnimalGrizzlyBear implements IPredator, EntityAnimalAIStandAttack.IEntityStandAttack {
	private static final int DAYS_TO_ADULTHOOD = 240;

	@SuppressWarnings("unused")
	public EntityAnimalBlackBear(World worldIn) {
		this(worldIn, IAnimal.Gender.valueOf(Constants.RANDOM.nextBoolean()), EntityAnimalBase.getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
	}

	public EntityAnimalBlackBear(World worldIn, IAnimal.Gender gender, int birthDay) {
		super(worldIn, gender, birthDay);
		this.setSize(1.4F, 1.7F);
	}

	@Override
	public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
		BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
		if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
				(biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST)) {
			return ModuleAnimalConfig.ENTITIES.BLACK_BEAR.rarity;
		}
		return 0;
	}

	@Override
	protected void initEntityAI() {
		EntityAIWander wander = new EntityAnimalAIWanderHuntArea(this, 1.0D);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAnimalAIStandAttack<>(this, 1.2D, 2.0D, EntityAnimalAIAttackMelee.AttackBehavior.DAYLIGHT_ONLY).setWanderAI(wander));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(5, wander);
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));


		int priority = 2;
		for (String input : ModuleAnimalConfig.ENTITIES.BLACK_BEAR.huntCreatures) {
			ResourceLocation key = new ResourceLocation(input);
			EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(key);
			if (entityEntry != null) {
				Class<? extends Entity> entityClass = entityEntry.getEntityClass();
				if (EntityLivingBase.class.isAssignableFrom(entityClass)) {
					//noinspection unchecked
					this.targetTasks.addTask(priority++, new EntityAINearestAttackableTarget<>(this, (Class<EntityLivingBase>) entityClass, false));
				}
			}
		}
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTablesAnimal.ANIMALS_BLACK_BEAR;
	}
}
