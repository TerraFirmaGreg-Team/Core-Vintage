package su.terrafirmagreg.modules.core.capabilities.chunkdata;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.world.classic.DataLayerClassic;
import su.terrafirmagreg.modules.world.classic.objects.generator.vein.Vein;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;


import net.dries007.tfc.api.types.Tree;

import java.util.List;
import java.util.Set;

public interface ICapabilityChunkData extends ICapabilitySerializable<NBTTagCompound> {

  int[] getSoilLayer1();

  int[] getRockLayer1();

  int[] getRockLayer2();

  int[] getRockLayer3();

  int[] getSeaLevelOffset();

  DataLayerClassic[] getDrainageLayer(); // To be removed / replaced?

  DataLayerClassic[] getStabilityLayer(); // To be removed / replaced?

  boolean isInitialized();

  void setInitialized(boolean initialized);

  boolean isSpawnProtected();

  int getFishPopulation();

  void setFishPopulation(int fishPopulation);

  float getRainfall();

  void setRainfall(float rainfall);

  float getRegionalTemp();

  void setRegionalTemp(float regionalTemp);

  float getAverageTemp();

  void setAverageTemp(float averageTemp);

  float getFloraDensity();

  void setFloraDensity(float density);

  float getFloraDiversity();

  void setFloraDiversity(float diversity);

  int getChunkWorkage();

  void setChunkWorkage(int workage);

  long getSpawnProtection();

  long getProtectedTicks();

  void setProtectedTicks(long ticks);

  long getLastUpdateTick();

  void setLastUpdateTick(long tick);

  long getLastUpdateYear();

  void setLastUpdateYear(long year);

  boolean canWork(int amount);

  void addWork(int amount);

  void addWork();

  void setWork(int amount);

  void addSpawnProtection(int multiplier);

  void resetLastUpdateTick();

  void resetLastUpdateYear();

  void setGenerationData(int[] rockLayer1, int[] rockLayer2, int[] rockLayer3, int[] soilLayer1,
      DataLayerClassic[] stabilityLayer,
      DataLayerClassic[] drainageLayer,
      int[] seaLevelOffset, float rainfall, float regionalTemp, float avgTemp, float floraDensity,
      float floraDiversity);

  Set<Vein> getGeneratedVeins();

  DataLayerClassic getStabilityLayer(int x, int z);

  DataLayerClassic getDrainageLayer(int x, int z);

  int getSeaLevelOffset(BlockPos pos);

  int getSeaLevelOffset(int x, int z);

  SoilType getSoilLayer1(int x, int z);

  RockType getRockLayer1(int x, int z);

  RockType getRockLayer2(int x, int z);

  RockType getRockLayer3(int x, int z);

  RockType getRockLayerHeight(int x, int y, int z);

  SoilType getSoilLayerHeight(int x, int y, int z);

  RockType getRock1(BlockPos pos);

  RockType getRock1(int x, int z);

  RockType getRock2(BlockPos pos);

  RockType getRock2(int x, int z);

  RockType getRock3(BlockPos pos);

  RockType getRock3(int x, int z);

  RockType getRockHeight(BlockPos pos);

  List<Tree> getValidTrees();

  Tree getSparseGenTree();

}




