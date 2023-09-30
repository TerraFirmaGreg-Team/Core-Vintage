package net.dries007.tfc.module.animal.common.entities.livestock;

import net.dries007.tfc.common.objects.LootTablesTFC;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.animal.api.type.ILivestock;
import net.dries007.tfc.module.core.sound.TFCSounds;
import net.dries007.tfc.util.Constants;
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

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EntityAnimalZebu extends EntityAnimalCow implements ILivestock {
    @SuppressWarnings("unused")
    public EntityAnimalZebu(World worldIn) {
        this(worldIn, Gender.valueOf(Constants.RNG.nextBoolean()), getRandomGrowth(ConfigTFC.Animals.ZEBU.adulthood, ConfigTFC.Animals.ZEBU.elder));
    }

    public EntityAnimalZebu(World worldIn, Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(1.4F, 1.4F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TROPICAL_FOREST)) {
            return ConfigTFC.Animals.ZEBU.rarity;
        }
        return 0;
    }

    @Override
    public void birthChildren() {
        int numberOfChildren = ConfigTFC.Animals.ZEBU.babies;
        for (int i = 0; i < numberOfChildren; i++) {
            EntityAnimalZebu baby = new EntityAnimalZebu(this.world, Gender.valueOf(Constants.RNG.nextBoolean()), (int) CalendarTFC.PLAYER_TIME.getTotalDays());
            baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
            baby.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
            this.world.spawnEntity(baby);
        }
    }

    @Override
    public long gestationDays() {
        return ConfigTFC.Animals.ZEBU.gestation;
    }

    @Override
    public double getOldDeathChance() {
        return ConfigTFC.Animals.ZEBU.oldDeathChance;
    }

    @Override
    public float getAdultFamiliarityCap() {
        return 0.35F;
    }

    @Override
    public int getDaysToAdulthood() {
        return ConfigTFC.Animals.ZEBU.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ConfigTFC.Animals.ZEBU.elder;
    }

    @Override
    public long getProductsCooldown() {
        return Math.max(0, ConfigTFC.Animals.ZEBU.milkTicks + getMilkedTick() - CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return TFCSounds.ANIMAL_ZEBU_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return TFCSounds.ANIMAL_ZEBU_DEATH;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return TFCSounds.ANIMAL_ZEBU_SAY;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesTFC.ANIMALS_ZEBU;
    }

    @Override
    // Equivalent sound
    protected void playStepSound(BlockPos pos, Block blockIn) {
        playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
    }
}
