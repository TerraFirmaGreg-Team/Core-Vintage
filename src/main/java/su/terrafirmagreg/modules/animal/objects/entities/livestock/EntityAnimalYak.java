package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundAnimal;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;


import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.lib.MathConstants.RNG;

public class EntityAnimalYak extends EntityAnimalCow implements ILivestock {

    @SuppressWarnings("unused")
    public EntityAnimalYak(World worldIn) {
        this(worldIn, Gender.valueOf(RNG.nextBoolean()),
                getRandomGrowth(ConfigAnimal.ENTITIES.YAK.adulthood, ConfigAnimal.ENTITIES.YAK.elder));
    }

    public EntityAnimalYak(World worldIn, Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(1.4F, 1.4F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TAIGA)) {
            return ConfigAnimal.ENTITIES.YAK.rarity;
        }
        return 0;
    }

    @Override
    public void birthChildren() {
        int numberOfChildren = ConfigAnimal.ENTITIES.YAK.babies;
        for (int i = 0; i < numberOfChildren; i++) {
            EntityAnimalYak baby = new EntityAnimalYak(this.world, Gender.valueOf(RNG.nextBoolean()),
                    (int) CalendarTFC.PLAYER_TIME.getTotalDays());
            baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
            baby.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
            this.world.spawnEntity(baby);
        }
    }

    @Override
    public long gestationDays() {
        return ConfigAnimal.ENTITIES.YAK.gestation;
    }

    @Override
    public double getOldDeathChance() {
        return ConfigAnimal.ENTITIES.YAK.oldDeathChance;
    }

    @Override
    public float getAdultFamiliarityCap() {
        return 0.35F;
    }

    @Override
    public int getDaysToAdulthood() {
        return ConfigAnimal.ENTITIES.YAK.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ConfigAnimal.ENTITIES.YAK.elder;
    }

    @Override
    public long getProductsCooldown() {
        return Math.max(0, ConfigAnimal.ENTITIES.YAK.milkTicks + getMilkedTick() - CalendarTFC.PLAYER_TIME.getTicks());
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
