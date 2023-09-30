package net.dries007.tfc.module.animal.common.entities.predator;

import net.dries007.tfc.common.objects.LootTablesTFC;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.animal.api.type.IAnimal;
import net.dries007.tfc.module.animal.api.type.IPredator;
import net.dries007.tfc.module.animal.common.entities.TFCEntityAnimal;
import net.dries007.tfc.module.animal.common.entities.ai.EntityAnimalAIAttackMelee;
import net.dries007.tfc.module.animal.common.entities.ai.EntityAnimalAIWanderHuntArea;
import net.dries007.tfc.module.core.sound.TFCSounds;
import net.dries007.tfc.util.Constants;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EntityAnimalCougar extends EntityAnimalPanther implements IPredator {
    private static final int DAYS_TO_ADULTHOOD = 160;

    @SuppressWarnings("unused")
    public EntityAnimalCougar(World worldIn) {
        this(worldIn, IAnimal.Gender.valueOf(Constants.RNG.nextBoolean()),
                TFCEntityAnimal.getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
    }

    public EntityAnimalCougar(World worldIn, IAnimal.Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(1.1F, 1.1F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST)) {
            return ConfigTFC.Animals.COUGAR.rarity;
        }
        return 0;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return TFCSounds.ANIMAL_COUGAR_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return TFCSounds.ANIMAL_COUGAR_DEATH;
    }

    @Override
    protected void initEntityAI() {
        EntityAIWander wander = new EntityAnimalAIWanderHuntArea(this, 1.0D);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAnimalAIAttackMelee<>(this, 1.2D, 1.25D, EntityAnimalAIAttackMelee.AttackBehavior.NIGHTTIME_ONLY).setWanderAI(wander));
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
        return Constants.RNG.nextInt(100) < 5 ? TFCSounds.ANIMAL_COUGAR_CRY : TFCSounds.ANIMAL_COUGAR_SAY;
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {
        return LootTablesTFC.ANIMALS_COUGAR;
    }
}
