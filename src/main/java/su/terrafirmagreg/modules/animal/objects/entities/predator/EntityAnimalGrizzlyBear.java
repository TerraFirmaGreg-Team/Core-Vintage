package su.terrafirmagreg.modules.animal.objects.entities.predator;

import su.terrafirmagreg.modules.animal.ModuleAnimalConfig;
import su.terrafirmagreg.modules.animal.data.LootTablesAnimal;
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
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.lib.Constants;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.api.type.IPredator;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.data.SoundAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalMammal;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAIAttackMelee;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAIStandAttack;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAIWanderHuntArea;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

@ParametersAreNonnullByDefault
public class EntityAnimalGrizzlyBear extends EntityAnimalMammal implements IPredator, EntityAnimalAIStandAttack.IEntityStandAttack {
	private static final int DAYS_TO_ADULTHOOD = 240;
	private static final DataParameter<Boolean> IS_STANDING;

	static {
		IS_STANDING = EntityDataManager.createKey(EntityAnimalGrizzlyBear.class, DataSerializers.BOOLEAN);
	}

	private float clientSideStandAnimation0;
	private float clientSideStandAnimation;
	private int warningSoundTicks;

	@SuppressWarnings("unused")
	public EntityAnimalGrizzlyBear(World worldIn) {
		this(worldIn, IAnimal.Gender.valueOf(Constants.RANDOM.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
	}

	public EntityAnimalGrizzlyBear(World worldIn, Gender gender, int birthDay) {
		super(worldIn, gender, birthDay);
		this.setSize(1.4F, 1.7F);
	}

	@Override
	public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
		BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
		if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
				(biomeType == BiomeHelper.BiomeType.TAIGA)) {
			return ModuleAnimalConfig.ENTITIES.GRIZZLY_BEAR.rarity;
		}
		return 0;
	}

	@Override
	public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
		return AnimalGroupingRules.MOTHER_AND_CHILDREN_OR_SOLO_MALE;
	}

	@Override
	public int getMinGroupSize() {
		return 1;
	}

	@Override
	public int getMaxGroupSize() {
		return 3;
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
		// Unused
	}

	@Override
	public long gestationDays() {
		return 0;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(IS_STANDING, false);
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		return false;
	}

	@Override
	public double getOldDeathChance() {
		return 0;
	}

	public boolean isStanding() {
		return this.dataManager.get(IS_STANDING);
	}

	@Override
	public void setStand(boolean standing) {
		this.dataManager.set(IS_STANDING, standing);
	}

	@Override
	public void playWarning() {
		if (this.warningSoundTicks <= 0) {
			this.playSound(SoundEvents.ENTITY_POLAR_BEAR_WARNING, 1.0F, 1.0F);
			this.warningSoundTicks = 40;
		}

	}

	@SideOnly(Side.CLIENT)
	public float getStandingAnimationScale(float partialTicks) {
		return (this.clientSideStandAnimation0 + (this.clientSideStandAnimation - this.clientSideStandAnimation0) * partialTicks) / 6.0F;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundAnimal.ANIMAL_BEAR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundAnimal.ANIMAL_BEAR_DEATH;
	}

	@Override
	public boolean attackEntityAsMob(@Nonnull Entity entityIn) {
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
	protected void playStepSound(BlockPos pos, Block blockIn) {
		this.playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.16F, 1.1F);
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		if (!this.hasHome()) {
			this.setHomePosAndDistance(this.getPosition(), 80);
		}
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
		// Avoid players at daytime
		this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 16.0F, 1.0D, 1.25D));

		int priority = 2;
		for (String input : ModuleAnimalConfig.ENTITIES.GRIZZLY_BEAR.huntCreatures) {
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
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		if (this.world.isRemote) {
			this.clientSideStandAnimation0 = this.clientSideStandAnimation;
			if (this.isStanding()) {
				this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
			} else {
				this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
			}
		}

		if (this.warningSoundTicks > 0) {
			--this.warningSoundTicks;
		}

	}

	@Override
	protected SoundEvent getAmbientSound() {
		return Constants.RANDOM.nextInt(100) < 5 ? SoundAnimal.ANIMAL_BEAR_CRY : SoundAnimal.ANIMAL_BEAR_SAY;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTablesAnimal.ANIMALS_GRIZZLY_BEAR;
	}
}
