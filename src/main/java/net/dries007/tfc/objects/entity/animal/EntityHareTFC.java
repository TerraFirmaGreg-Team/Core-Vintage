package net.dries007.tfc.objects.entity.animal;

import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.modules.animal.api.type.IHuntable;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;

import su.terrafirmagreg.api.network.datasync.DataSerializers;

import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.util.climate.BiomeHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.MathConstants.RNG;

public class EntityHareTFC extends EntityRabbitTFC implements IHuntable {

    private static final int DAYS_TO_ADULTHOOD = 16;
    private static final DataParameter<Integer> HARE_TYPE = EntityDataManager.createKey(EntityHareTFC.class, DataSerializers.VARINT);

    @SuppressWarnings("unused")
    public EntityHareTFC(World worldIn) {
        this(worldIn, Gender.valueOf(RNG.nextBoolean()), getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
    }

    public EntityHareTFC(World worldIn, Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(0.4F, 0.6F);
        this.jumpHelper = new RabbitJumpHelper(this);
        this.moveHelper = new EntityHareTFC.RabbitMoveHelper(this);
        this.setMovementSpeed(0.0D);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomeUtils.isOceanicBiome(biome) && !BiomeUtils.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TROPICAL_FOREST || biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST ||
                        biomeType == BiomeHelper.BiomeType.SAVANNA ||
                        biomeType == BiomeHelper.BiomeType.DESERT)) {
            return ConfigTFC.Animals.HARE.rarity;
        }
        return 0;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(HARE_TYPE, 0);
    }

    public void writeEntityToNBT(@NotNull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("HareType", this.getHareType());
    }

    public void readEntityFromNBT(@NotNull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setHareType(compound.getInteger("HareType"));
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesAnimal.ANIMALS_HARE;
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        int i = this.getRandomHareType();

        if (livingdata instanceof EntityHareTFC.HareTypeData) {
            i = ((EntityHareTFC.HareTypeData) livingdata).typeData;
        } else {
            livingdata = new EntityHareTFC.HareTypeData(i);
        }

        this.setHareType(i);

        return livingdata;
    }

    public int getHareType() {
        return this.dataManager.get(HARE_TYPE);
    }

    public void setHareType(int hareTypeId) {
        this.dataManager.set(HARE_TYPE, hareTypeId);
    }

    private int getRandomHareType() {
        float temperature = 0;
        float rainfall = 0;
        float floraDensity = 0;
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        int i = this.rand.nextInt(100);

        if (biomeType == BiomeHelper.BiomeType.SAVANNA) {
            return i < 50 ? 1 : (i < 90 ? 1 : 3);
        }
        if (biomeType == BiomeHelper.BiomeType.DESERT) {
            return i < 10 ? 3 : (i < 90 ? 0 : 1);
        } else {
            return i < 50 ? 0 : (i < 90 ? 1 : 2);
        }
    }

    public static class HareTypeData implements IEntityLivingData {

        public int typeData;

        public HareTypeData(int type) {
            this.typeData = type;
        }
    }
}
