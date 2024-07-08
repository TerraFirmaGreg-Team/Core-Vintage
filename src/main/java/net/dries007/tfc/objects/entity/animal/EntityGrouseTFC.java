package net.dries007.tfc.objects.entity.animal;

import su.terrafirmagreg.api.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundAnimal;

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


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.api.lib.MathConstants.RNG;

public class EntityGrouseTFC extends EntityChickenTFC implements ILivestock {

    public EntityGrouseTFC(World worldIn) {
        this(worldIn, Gender.valueOf(RNG.nextBoolean()),
                getRandomGrowth(ConfigTFC.Animals.GROUSE.adulthood, ConfigTFC.Animals.GROUSE.elder));
    }

    public EntityGrouseTFC(World worldIn, Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(0.8F, 0.8F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.PLAINS || biomeType == BiomeHelper.BiomeType.SAVANNA)) {
            return ConfigTFC.Animals.GROUSE.rarity;
        }
        return 0;
    }

    @Override
    public int getDaysToAdulthood() {
        return ConfigTFC.Animals.GROUSE.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ConfigTFC.Animals.GROUSE.elder;
    }

    @Override
    public List<ItemStack> getProducts() {
        List<ItemStack> eggs = new ArrayList<>();
        ItemStack egg = new ItemStack(Items.EGG);
        if (this.isFertilized()) {
            var cap = CapabilityEgg.get(egg);
            if (cap != null) {
                EntityGrouseTFC chick = new EntityGrouseTFC(this.world);
                chick.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
                cap.setFertilized(chick, ConfigTFC.Animals.GROUSE.hatch + CalendarTFC.PLAYER_TIME.getTotalDays());
            }
        }
        eggs.add(egg);
        return eggs;
    }

    @Override
    public long getProductsCooldown() {
        return Math.max(0, ConfigTFC.Animals.GROUSE.eggTicks + getLaidTicks() - CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundAnimal.ANIMAL_GROUSE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundAnimal.ANIMAL_GROUSE_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundAnimal.ANIMAL_GROUSE_SAY;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_GROUSE;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        // Same sound, no need to create another
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }

    @Override
    public double getOldDeathChance() {
        return ConfigTFC.Animals.GROUSE.oldDeathChance;
    }
}
