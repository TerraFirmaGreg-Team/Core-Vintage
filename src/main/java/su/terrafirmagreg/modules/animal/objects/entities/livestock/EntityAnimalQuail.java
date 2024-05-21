package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import su.terrafirmagreg.api.lib.MathConstants;
import su.terrafirmagreg.modules.animal.ModuleAnimalConfig;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundAnimal;
import su.terrafirmagreg.api.capabilities.egg.CapabilityEgg;

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


import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EntityAnimalQuail extends EntityAnimalChicken implements ILivestock {

    public EntityAnimalQuail(World worldIn) {
        this(worldIn, Gender.valueOf(MathConstants.RNG.nextBoolean()),
                getRandomGrowth(ModuleAnimalConfig.ENTITIES.QUAIL.adulthood, ModuleAnimalConfig.ENTITIES.QUAIL.elder));
    }

    public EntityAnimalQuail(World worldIn, Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(0.7F, 0.7F);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST)) {
            return ModuleAnimalConfig.ENTITIES.QUAIL.rarity;
        }
        return 0;
    }

    @Override
    public int getDaysToAdulthood() {
        return ModuleAnimalConfig.ENTITIES.QUAIL.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ModuleAnimalConfig.ENTITIES.QUAIL.elder;
    }

    @Override
    public List<ItemStack> getProducts() {
        List<ItemStack> eggs = new ArrayList<>();
        ItemStack egg = new ItemStack(Items.EGG);
        if (this.isFertilized()) {
            var cap = CapabilityEgg.get(egg);
            if (cap != null) {
                EntityAnimalQuail chick = new EntityAnimalQuail(this.world);
                chick.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
                cap.setFertilized(chick, ModuleAnimalConfig.ENTITIES.QUAIL.hatch + CalendarTFC.PLAYER_TIME.getTotalDays());
            }
        }
        eggs.add(egg);
        return eggs;
    }

    @Override
    public long getProductsCooldown() {
        return Math.max(0, ModuleAnimalConfig.ENTITIES.QUAIL.eggTicks + getLaidTicks() - CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundAnimal.ANIMAL_QUAIL_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundAnimal.ANIMAL_QUAIL_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundAnimal.ANIMAL_QUAIL_SAY;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_QUAIL;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        // Same sound, no need to create another
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.14F, 0.9F);
    }

    @Override
    public double getOldDeathChance() {
        return ModuleAnimalConfig.ENTITIES.QUAIL.oldDeathChance;
    }
}
