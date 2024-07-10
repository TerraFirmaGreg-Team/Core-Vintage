package net.dries007.tfc.objects.entity.animal;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.animal.api.type.IPredator;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.objects.entity.ai.EntityAIAttackMeleeTFC;
import net.dries007.tfc.objects.entity.ai.EntityAIWanderHuntArea;
import net.dries007.tfc.util.climate.BiomeHelper;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.lib.MathConstants.RNG;

public class EntityCougarTFC extends EntityPantherTFC implements IPredator {

    private static final int DAYS_TO_ADULTHOOD = 160;

    @SuppressWarnings("unused")
    public EntityCougarTFC(World worldIn) {
        this(worldIn, Gender.valueOf(RNG.nextBoolean()),
                getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
    }

    public EntityCougarTFC(World worldIn, Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(1.1F, 1.1F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST)) {
            return ConfigTFC.Animals.COUGAR.rarity;
        }
        return 0;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {return SoundsAnimal.ANIMAL_COUGAR_HURT;}

    @Override
    protected SoundEvent getDeathSound() {return SoundsAnimal.ANIMAL_COUGAR_DEATH;}

    @Override
    protected void initEntityAI() {
        EntityAIWander wander = new EntityAIWanderHuntArea(this, 1.0D);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3,
                new EntityAIAttackMeleeTFC<>(this, 1.2D, 1.25D, EntityAIAttackMeleeTFC.AttackBehavior.NIGHTTIME_ONLY).setWanderAI(wander));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(5, wander); // Move within hunt area
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        // Avoid players at daytime
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 16.0F, 1.0D, 1.25D));

        int priority = 2;
        for (String input : ConfigTFC.Animals.COUGAR.huntCreatures) {
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
    protected SoundEvent getAmbientSound() {
        return RNG.nextInt(100) < 5 ? SoundsAnimal.ANIMAL_COUGAR_CRY : SoundsAnimal.ANIMAL_COUGAR_SAY;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_COUGAR;
    }
}
