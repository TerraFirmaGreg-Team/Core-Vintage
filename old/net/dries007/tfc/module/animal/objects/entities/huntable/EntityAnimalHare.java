package net.dries007.tfc.module.animal.objects.entities.huntable;

import net.dries007.tfc.module.animal.objects.LootTablesTFC;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.animal.api.type.IAnimal;
import net.dries007.tfc.module.animal.api.type.IHuntable;
import net.dries007.tfc.module.animal.objects.entities.TFCEntityAnimal;
import net.dries007.tfc.util.Constants;
import net.dries007.tfc.util.climate.BiomeHelper;
import net.dries007.tfc.world.classic.biomes.BiomesTFC;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class EntityAnimalHare extends EntityAnimalRabbit implements IHuntable {
    private static final int DAYS_TO_ADULTHOOD = 16;
    private static final DataParameter<Integer> HARE_TYPE = EntityDataManager.createKey(EntityAnimalHare.class, DataSerializers.VARINT);

    @SuppressWarnings("unused")
    public EntityAnimalHare(World worldIn) {
        this(worldIn, IAnimal.Gender.valueOf(Constants.RNG.nextBoolean()), TFCEntityAnimal.getRandomGrowth(DAYS_TO_ADULTHOOD, 0));
    }

    public EntityAnimalHare(World worldIn, IAnimal.Gender gender, int birthDay) {
        super(worldIn, gender, birthDay);
        this.setSize(0.4F, 0.6F);
        this.jumpHelper = new RabbitJumpHelper(this);
        this.moveHelper = new EntityAnimalHare.RabbitMoveHelper(this);
        this.setMovementSpeed(0.0D);
    }

    @Override
    public int getSpawnWeight(Biome biome, float temperature, float rainfall, float floraDensity, float floraDiversity) {
        BiomeHelper.BiomeType biomeType = BiomeHelper.getBiomeType(temperature, rainfall, floraDensity);
        if (!BiomesTFC.isOceanicBiome(biome) && !BiomesTFC.isBeachBiome(biome) &&
                (biomeType == BiomeHelper.BiomeType.TROPICAL_FOREST || biomeType == BiomeHelper.BiomeType.TEMPERATE_FOREST || biomeType == BiomeHelper.BiomeType.SAVANNA ||
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

    public void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("HareType", this.getHareType());
    }

    public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setHareType(compound.getInteger("HareType"));
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTablesTFC.ANIMALS_HARE;
    }

    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        int i = this.getRandomHareType();

        if (livingdata instanceof EntityAnimalHare.HareTypeData) {
            i = ((EntityAnimalHare.HareTypeData) livingdata).typeData;
        } else {
            livingdata = new EntityAnimalHare.HareTypeData(i);
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
