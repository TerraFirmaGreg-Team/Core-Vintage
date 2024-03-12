package su.terrafirmagreg.modules.animal.objects.entities.predator;

import su.terrafirmagreg.modules.animal.ModuleAnimalConfig;
import su.terrafirmagreg.modules.animal.data.LootTablesAnimal;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import su.terrafirmagreg.api.lib.Constants;
import su.terrafirmagreg.modules.animal.api.type.IPredator;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.data.SoundAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalMammal;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAIAttackMelee;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAIWanderHuntArea;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

@ParametersAreNonnullByDefault
public class EntityAnimalLion extends EntityAnimalMammal implements IPredator {
	private static final int DAYS_TO_ADULTHOOD = 192;

	//Values that has a visual effect on client
	private static final DataParameter<Integer> MOUTH_TICKS = EntityDataManager.createKey(EntityAnimalLion.class, DataSerializers.VARINT);

	@SuppressWarnings("unused")
	public EntityAnimalLion(World worldIn) {
		this(worldIn, Gender.valueOf(Constants.RANDOM.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
	}

	public EntityAnimalLion(World worldIn, Gender gender, int birthDay) {
		super(worldIn, gender, birthDay);
		this.setSize(1.3F, 1.2F);
	}

	@Override
	public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
		BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
		if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
				(biomeType == BiomeHelper.BiomeType.SAVANNA)) {
			return ModuleAnimalConfig.ENTITIES.LION.rarity;
		}
		return 0;
	}

	@Override
	public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
		return AnimalGroupingRules.ELDER_AND_POPULATION;
	}

	@Override
	public int getMinGroupSize() {
		return 1;
	}

	@Override
	public int getMaxGroupSize() {
		return 5;
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
		int numberOfChildren = 1; //one always
		for (int i = 0; i < numberOfChildren; i++) {
			EntityAnimalLion baby = new EntityAnimalLion(this.world, Gender.valueOf(Constants.RANDOM.nextBoolean()), (int) CalendarTFC.PLAYER_TIME.getTotalDays());
			baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
			this.world.spawnEntity(baby);
		}
	}

	@Override
	public long gestationDays() {
		return 0;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		getDataManager().register(MOUTH_TICKS, 0);
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		return false;
	}

	@Override
	public double getOldDeathChance() {
		return 0;
	}

	public int getMouthTicks() {
		return this.dataManager.get(MOUTH_TICKS);
	}

	public void setMouthTicks(int value) {
		this.dataManager.set(MOUTH_TICKS, value);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundAnimal.ANIMAL_LION_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundAnimal.ANIMAL_LION_DEATH;
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		double attackDamage = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		if (this.isChild()) {
			attackDamage /= 2;
		}
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) attackDamage);
		if (flag) {
			this.applyEnchantments(this, entityIn);
		}
		return flag;
	}

	@Override
	protected void initEntityAI() {
		EntityAIWander wander = new EntityAnimalAIWanderHuntArea(this, 1.0D);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAnimalAILionAttack().setWanderAI(wander));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		this.tasks.addTask(5, wander);
		this.tasks.addTask(7, new EntityAILookIdle(this));
		// Avoid players at daytime
		this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 16.0F, 1.0D, 1.25D));

		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));

		int priority = 2;
		for (String input : ModuleAnimalConfig.ENTITIES.LION.huntCreatures) {
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

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.34D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return Constants.RANDOM.nextInt(100) < 5 ? SoundAnimal.ANIMAL_LION_CRY : SoundAnimal.ANIMAL_LION_SAY;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTablesAnimal.ANIMALS_LION;
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		if (!this.hasHome()) {
			this.setHomePosAndDistance(this.getPosition(), 80);
		}
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		playSound(SoundAnimal.ANIMAL_FELINE_STEP, 0.15F, 1.0F);
	}

	/**
	 * Adds a bit of animation to the attack
	 */
	protected class EntityAnimalAILionAttack extends EntityAnimalAIAttackMelee<EntityAnimalLion> {
		protected int attackTicks;

		public EntityAnimalAILionAttack() {
			super(EntityAnimalLion.this, 1.3D, 1.5D, AttackBehavior.NIGHTTIME_ONLY);
			this.attackTicks = 0;
		}

		@Override
		public void resetTask() {
			super.resetTask();
			this.attackTicks = 0;
			EntityAnimalLion.this.setMouthTicks(0);
		}

		@Override
		public void updateTask() {
			super.updateTask();
			++this.attackTicks;
			EntityAnimalLion.this.setMouthTicks(attackTicks);
		}
	}
}
