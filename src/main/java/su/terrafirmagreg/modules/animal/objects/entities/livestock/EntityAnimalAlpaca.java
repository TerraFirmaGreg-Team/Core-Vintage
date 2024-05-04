package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import su.terrafirmagreg.api.lib.MathConstants;
import su.terrafirmagreg.modules.animal.ModuleAnimalConfig;
import su.terrafirmagreg.modules.animal.api.type.IAnimal;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.api.util.AnimalGroupingRules;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
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

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * A Sheep of the colder regions! Actually, they produce wool faster, but takes longer to reach maturity, have long gestation periods and only give birth to one individual
 */

public class EntityAnimalAlpaca extends EntityAnimalSheep implements ILivestock {

    @SuppressWarnings("unused")
    public EntityAnimalAlpaca(World worldIn) {
        this(worldIn, IAnimal.Gender.valueOf(MathConstants.RNG.nextBoolean()),
                EntityAnimalBase.getRandomGrowth(ModuleAnimalConfig.ENTITIES.ALPACA.adulthood, ModuleAnimalConfig.ENTITIES.ALPACA.elder),
                EntitySheep.getRandomSheepColor(MathConstants.RNG));
    }

    public EntityAnimalAlpaca(World worldIn, IAnimal.Gender gender, int birthDay, EnumDyeColor dye) {
        super(worldIn, gender, birthDay, dye);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TAIGA)) {
            return ModuleAnimalConfig.ENTITIES.ALPACA.rarity;
        }
        return 0;
    }

    @Override
    public BiConsumer<List<EntityLiving>, Random> getGroupingRules() {
        return AnimalGroupingRules.MALE_AND_FEMALES;
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
        int numberOfChildren = ModuleAnimalConfig.ENTITIES.ALPACA.babies;
        for (int i = 0; i < numberOfChildren; i++) {
            EntityAnimalAlpaca baby = new EntityAnimalAlpaca(world, Gender.valueOf(MathConstants.RNG.nextBoolean()),
                    (int) CalendarTFC.PLAYER_TIME.getTotalDays(), getDyeColor());
            baby.setLocationAndAngles(posX, posY, posZ, 0.0F, 0.0F);
            baby.setFamiliarity(getFamiliarity() < 0.9F ? getFamiliarity() / 2.0F : getFamiliarity() * 0.9F);
            world.spawnEntity(baby);
        }
    }

    @Override
    public long gestationDays() {
        return ModuleAnimalConfig.ENTITIES.ALPACA.gestation;
    }

    @Override
    public double getOldDeathChance() {
        return ModuleAnimalConfig.ENTITIES.ALPACA.oldDeathChance;
    }

    @Override
    public float getAdultFamiliarityCap() {
        return 0.35F;
    }

    @Override
    public int getDaysToAdulthood() {
        return ModuleAnimalConfig.ENTITIES.ALPACA.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ModuleAnimalConfig.ENTITIES.ALPACA.elder;
    }

    @Override
    public long getProductsCooldown() {
        return Math.max(0, ModuleAnimalConfig.ENTITIES.ALPACA.woolTicks + getShearedTick() - CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundAnimal.ANIMAL_ALPACA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundAnimal.ANIMAL_ALPACA_DEATH;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MathConstants.RNG.nextInt(100) < 5 ? SoundAnimal.ANIMAL_ALPACA_CRY : SoundAnimal.ANIMAL_ALPACA_SAY;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_ALPACA;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_LLAMA_STEP, 0.15F, 1.0F);
    }
}
