package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import su.terrafirmagreg.api.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.animal.ConfigAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;


import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.climate.BiomeHelper;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.api.lib.MathConstants.RNG;

/**
 * A Chicken of the colder regions! Actually, ducks takes longer to reach maturity, but hey, they are cute!
 */

public class EntityAnimalDuck extends EntityAnimalChicken implements ILivestock {

    public EntityAnimalDuck(World worldIn) {
        this(worldIn, Gender.valueOf(RNG.nextBoolean()),
                getRandomGrowth(ConfigAnimal.ENTITIES.DUCK.adulthood, ConfigAnimal.ENTITIES.DUCK.elder));
    }

    public EntityAnimalDuck(World worldIn, Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(0.8F, 0.9F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.PLAINS || biomeType == BiomeHelper.BiomeType.TROPICAL_FOREST)) {
            return ConfigAnimal.ENTITIES.DUCK.rarity;
        }
        return 0;
    }

    @Override
    public int getDaysToAdulthood() {
        return ConfigAnimal.ENTITIES.DUCK.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ConfigAnimal.ENTITIES.DUCK.elder;
    }

    @Override
    public List<ItemStack> getProducts() {
        List<ItemStack> eggs = new ArrayList<>();
        ItemStack egg = new ItemStack(Items.EGG);
        if (this.isFertilized()) {
            var cap = CapabilityEgg.get(egg);
            if (cap != null) {
                EntityAnimalDuck chick = new EntityAnimalDuck(this.world);
                chick.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
                cap.setFertilized(chick, ConfigAnimal.ENTITIES.DUCK.hatch + Calendar.PLAYER_TIME.getTotalDays());
            }
        }
        eggs.add(egg);
        return eggs;
    }

    @Override
    public long getProductsCooldown() {
        return Math.max(0, ConfigAnimal.ENTITIES.DUCK.eggTicks + getLaidTicks() - Calendar.PLAYER_TIME.getTicks());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundsAnimal.ANIMAL_DUCK_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundsAnimal.ANIMAL_DUCK_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return RNG.nextInt(100) < 5 ? SoundsAnimal.ANIMAL_DUCK_CRY : SoundsAnimal.ANIMAL_DUCK_SAY;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_DUCK;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        // Same sound, no need to create another
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    public double getOldDeathChance() {
        return ConfigAnimal.ENTITIES.DUCK.oldDeathChance;
    }
}
