package net.dries007.tfc.module.animal.common.entities.livestock;

import net.dries007.tfc.common.objects.LootTablesTFC;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.animal.api.type.IAnimal;
import net.dries007.tfc.module.animal.api.type.ILivestock;
import net.dries007.tfc.module.animal.common.entities.TFCEntityAnimal;
import net.dries007.tfc.module.core.sound.TFCSounds;
import net.dries007.tfc.util.Constants;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
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

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EntityAnimalMuskOx extends EntityAnimalSheep implements ILivestock {
    @SuppressWarnings("unused")
    public EntityAnimalMuskOx(World worldIn) {
        this(worldIn, IAnimal.Gender.valueOf(Constants.RNG.nextBoolean()), TFCEntityAnimal.getRandomGrowth(ConfigTFC.Animals.MUSKOX.adulthood, ConfigTFC.Animals.MUSKOX.elder), EntitySheep.getRandomSheepColor(Constants.RNG));
    }

    public EntityAnimalMuskOx(World worldIn, IAnimal.Gender gender, int birthDay, EnumDyeColor dye) {
        super(worldIn, gender, birthDay, dye);
        this.setSize(1.4F, 1.6F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
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
            EntityAnimalMuskOx baby = new EntityAnimalMuskOx(world, IAnimal.Gender.valueOf(Constants.RNG.nextBoolean()), (int) CalendarTFC.PLAYER_TIME.getTotalDays(), getDyeColor());
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
        return TFCSounds.ANIMAL_MUSKOX_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return TFCSounds.ANIMAL_MUSKOX_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return TFCSounds.ANIMAL_MUSKOX_SAY;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesTFC.ANIMALS_MUSKOX;
    }

    @Override
    // Equivalent sound
    protected void playStepSound(BlockPos pos, Block blockIn) {
        playSound(SoundEvents.ENTITY_COW_STEP, 0.16F, 1.1F);
    }
}
