package net.dries007.tfc.objects.entity.animal;

import su.terrafirmagreg.api.lib.MathConstants;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundAnimal;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;

import org.jetbrains.annotations.Nullable;

public class EntityMuskOxTFC extends EntitySheepTFC implements ILivestock {

    @SuppressWarnings("unused")
    public EntityMuskOxTFC(World worldIn) {
        this(worldIn, Gender.valueOf(MathConstants.RNG.nextBoolean()),
                getRandomGrowth(ConfigTFC.Animals.MUSKOX.adulthood, ConfigTFC.Animals.MUSKOX.elder),
                EntitySheep.getRandomSheepColor(MathConstants.RNG));
    }

    public EntityMuskOxTFC(World worldIn, Gender gender, int birthDay, EnumDyeColor dye) {
        super(worldIn, gender, birthDay, dye);
        this.setSize(1.4F, 1.6F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TUNDRA)) {
            return ConfigTFC.Animals.MUSKOX.rarity;
        }
        return 0;
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
        int numberOfChildren = ConfigTFC.Animals.MUSKOX.babies;
        for (int i = 0; i < numberOfChildren; i++) {
            EntityMuskOxTFC baby = new EntityMuskOxTFC(world, Gender.valueOf(MathConstants.RNG.nextBoolean()),
                    (int) CalendarTFC.PLAYER_TIME.getTotalDays(), getDyeColor());
            baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
            baby.setFamiliarity(getFamiliarity() < 0.9F ? getFamiliarity() / 2.0F : getFamiliarity() * 0.9F);
            world.spawnEntity(baby);
        }
    }

    @Override
    public long gestationDays() {
        return ConfigTFC.Animals.MUSKOX.gestation;
    }

    @Override
    public double getOldDeathChance() {
        return ConfigTFC.Animals.MUSKOX.oldDeathChance;
    }

    @Override
    public float getAdultFamiliarityCap() {
        return 0.35F;
    }

    @Override
    public int getDaysToAdulthood() {
        return ConfigTFC.Animals.MUSKOX.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ConfigTFC.Animals.MUSKOX.elder;
    }

    @Override
    public long getProductsCooldown() {
        return Math.max(0, ConfigTFC.Animals.MUSKOX.woolTicks + getShearedTick() - CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundAnimal.ANIMAL_MUSKOX_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundAnimal.ANIMAL_MUSKOX_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundAnimal.ANIMAL_MUSKOX_SAY;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_MUSKOX;
    }

    @Override
    // Equivalent sound
    protected void playStepSound(BlockPos pos, Block blockIn) {
        playSound(SoundEvents.ENTITY_COW_STEP, 0.16F, 1.1F);
    }
}
