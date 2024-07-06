package su.terrafirmagreg.api.capabilities.chunkdata;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;


import net.dries007.tfc.world.classic.DataLayer;
import net.dries007.tfc.world.classic.worldgen.vein.Vein;

import java.util.Set;

public interface ICapabilityChunkData extends ICapabilitySerializable<NBTTagCompound> {

    int[] getRockLayer1();

    int[] getRockLayer2();

    int[] getRockLayer3();

    int[] getSeaLevelOffset();

    DataLayer[] getDrainageLayer(); // To be removed / replaced?

    DataLayer[] getStabilityLayer(); // To be removed / replaced?

    boolean isInitialized();

    int getFishPopulation();

    float getRainfall();

    float getRegionalTemp();

    float getAverageTemp();

    float getFloraDensity();

    float getFloraDiversity();

    int getChunkWorkage();

    long getProtectedTicks();

    long getLastUpdateTick();

    long getLastUpdateYear();

    void setInitialized(boolean initialized);

    void setFishPopulation(int fishPopulation);

    void setRainfall(float rainfall);

    void setRegionalTemp(float regionalTemp);

    void setAverageTemp(float averageTemp);

    void setFloraDensity(float density);

    void setFloraDiversity(float diversity);

    void setChunkWorkage(int workage);

    void setProtectedTicks(long ticks);

    void setLastUpdateTick(long tick);

    void setLastUpdateYear(long year);

    Set<Vein> getGeneratedVeins();

    DataLayer getStabilityLayer(int x, int z);

    DataLayer getDrainageLayer(int x, int z);

    int getSeaLevelOffset(BlockPos pos);

    int getSeaLevelOffset(int x, int z);

    RockType getRockLayer1(int x, int z);

    RockType getRockLayer2(int x, int z);

    RockType getRockLayer3(int x, int z);

    RockType getRockLayerHeight(int x, int y, int z);

    SoilType getSoilLayerHeight(int x, int y, int z);

}




