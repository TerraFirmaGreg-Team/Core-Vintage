package su.terrafirmagreg.modules.animal.objects.entities.livestock;

import su.terrafirmagreg.api.lib.Constants;
import su.terrafirmagreg.modules.animal.ModuleAnimalConfig;
import su.terrafirmagreg.modules.animal.api.type.ILivestock;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.SoundAnimal;
import su.terrafirmagreg.modules.animal.objects.entities.ai.EntityAnimalAILawnmower;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;

import org.jetbrains.annotations.Nullable;

/**
 * A Cow of the colder regions! Actually, goats also reach maturity + finish gestation faster than cows, and even give birth to more than one
 * individual, but produce milk once every 3 days
 */

public class EntityAnimalGoat extends EntityAnimalCow implements ILivestock {

    public int sheepTimer;
    private EntityAnimalAILawnmower entityAnimalAILawnmower;

    @SuppressWarnings("unused")
    public EntityAnimalGoat(World worldIn) {
        this(worldIn, Gender.valueOf(Constants.RANDOM.nextBoolean()),
                getRandomGrowth(ModuleAnimalConfig.ENTITIES.GOAT.adulthood, ModuleAnimalConfig.ENTITIES.GOAT.elder));
    }

    public EntityAnimalGoat(World worldIn, Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
    }

    @Override
    protected void updateAITasks() {
        sheepTimer = entityAnimalAILawnmower.getTimer();
        super.updateAITasks();
    }

    @Override
    public void onLivingUpdate() {
        if (world.isRemote) {
            sheepTimer = Math.max(0, this.sheepTimer - 1);
        }
        super.onLivingUpdate();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == (byte) 10) {
            sheepTimer = 40;
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        tasks.taskEntries.removeIf(task -> task.action instanceof EntityAIEatGrass);
        entityAnimalAILawnmower = new EntityAnimalAILawnmower(this);
        tasks.addTask(6, entityAnimalAILawnmower);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST)) {
            return ModuleAnimalConfig.ENTITIES.GOAT.rarity;
        }
        return 0;
    }

    @Override
    public void birthChildren() {
        int numberOfChildren = ModuleAnimalConfig.ENTITIES.GOAT.babies;
        for (int i = 0; i < numberOfChildren; i++) {
            EntityAnimalGoat baby = new EntityAnimalGoat(this.world, Gender.valueOf(Constants.RANDOM.nextBoolean()),
                    (int) CalendarTFC.PLAYER_TIME.getTotalDays());
            baby.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
            baby.setFamiliarity(this.getFamiliarity() < 0.9F ? this.getFamiliarity() / 2.0F : this.getFamiliarity() * 0.9F);
            this.world.spawnEntity(baby);
        }
    }

    @Override
    public long gestationDays() {
        return ModuleAnimalConfig.ENTITIES.GOAT.gestation;
    }

    @Override
    public double getOldDeathChance() {
        return ModuleAnimalConfig.ENTITIES.GOAT.oldDeathChance;
    }

    @Override
    public float getAdultFamiliarityCap() {
        return 0.35F;
    }

    @Override
    public int getDaysToAdulthood() {
        return ModuleAnimalConfig.ENTITIES.GOAT.adulthood;
    }

    @Override
    public int getDaysToElderly() {
        return ModuleAnimalConfig.ENTITIES.GOAT.elder;
    }

    @Override
    public long getProductsCooldown() {
        return Math.max(0, ModuleAnimalConfig.ENTITIES.GOAT.milkTicks + getMilkedTick() - CalendarTFC.PLAYER_TIME.getTicks());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundAnimal.ANIMAL_GOAT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundAnimal.ANIMAL_GOAT_DEATH;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return Constants.RANDOM.nextInt(100) < 5 ? SoundAnimal.ANIMAL_GOAT_CRY : SoundAnimal.ANIMAL_GOAT_SAY;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_GOAT;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        // Equivalent sound
        this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationAngleX(float ticks) {
        if (this.sheepTimer > 4 && this.sheepTimer <= 36) {
            float f = ((float) (this.sheepTimer - 4) - ticks) / 32.0F;
            return 0.62831855F + 0.2199115F * MathHelper.sin(f * 28.7F);
        } else {
            return this.sheepTimer > 0 ? 0.62831855F : this.rotationPitch * 0.017453292F;
        }
    }
}
