package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;

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


import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.climate.BiomeHelper;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.lib.MathConstants.RNG;

public class EntityAnimalMuskOx extends EntityAnimalSheep implements ILivestock {

    @SuppressWarnings("unused")
    public EntityAnimalMuskOx(World worldIn) {
        this(worldIn, IAnimal.Gender.valueOf(RNG.nextBoolean()),
                getRandomGrowth(ConfigAnimal.ENTITIES.MUSKOX.adulthood, ConfigAnimal.ENTITIES.MUSKOX.elder),
                EntitySheep.getRandomSheepColor(RNG));
    }

    public EntityAnimalMuskOx(World worldIn, IAnimal.Gender gender, int birthDay, EnumDyeColor dye) {
        super(worldIn, gender, birthDay, dye);
        this.setSize(1.4F, 1.6F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TUNDRA)) {
            return ConfigAnimal.ENTITIES.MUSKOX.rarity;
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
        int numberOfChildren = ConfigAnimal.ENTITIES.MUSKOX.babies;
        for (int i = 0; i < numberOfChildren; i++) {
            EntityAnimalMuskOx baby = new EntityAnimalMuskOx(world, IAnimal.Gender.valueOf(RNG.nextBoolean()),
                    (int) Calendar.PLAYER_TIME.getTotalDays(), getDyeColor());
            baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
            baby.setFamiliarity(getFamiliarity() < 0.9F ? getFamiliarity() / 2.0F : getFamiliarity() * 0.9F);
            world.spawnEntity(baby);
        }
    }

    @Override
    public long gestationDays() {
        return ConfigAnimal.ENTITIES.MUSKOX.gestation;
    }

    @Override
    public double getOldDeathChance() {
        return ConfigAnimal.ENTITIES.MUSKOX.oldDeathChance;
    }

    @Override
    public float getAdultFamiliarityCap() {
        return 0.35F;
    }

    @Override
    public int getDaysToAdulthood() {
        return ConfigAnimal.ENTITIES.MUSKOX.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ConfigAnimal.ENTITIES.MUSKOX.elder;
    }

    @Override
    public long getProductsCooldown() {
        return Math.max(0, ConfigAnimal.ENTITIES.MUSKOX.woolTicks + getShearedTick() - Calendar.PLAYER_TIME.getTicks());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundsAnimal.ANIMAL_MUSKOX_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundsAnimal.ANIMAL_MUSKOX_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsAnimal.ANIMAL_MUSKOX_SAY;
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
