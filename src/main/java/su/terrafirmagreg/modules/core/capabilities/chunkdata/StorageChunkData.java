package su.terrafirmagreg.modules.core.capabilities.chunkdata;

import su.terrafirmagreg.data.lib.NBTBuilder;
import su.terrafirmagreg.modules.world.classic.DataLayerClassic;
import su.terrafirmagreg.modules.world.classic.objects.generator.vein.Vein;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.util.Constants;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class StorageChunkData implements IStorage<ICapabilityChunkData> {

  public static NBTTagByteArray write(DataLayerClassic[] layers) {
    return new NBTTagByteArray(
      Arrays.stream(layers).map(x -> (byte) x.layerID).collect(Collectors.toList()));
  }

  public static void read(DataLayerClassic[] layers, byte[] bytes) {
    for (int i = bytes.length - 1; i >= 0; i--) {
      layers[i] = DataLayerClassic.get(bytes[i]);
    }
  }

  @Nullable
  @Override
  public NBTBase writeNBT(Capability<ICapabilityChunkData> capability,
                          ICapabilityChunkData instance, EnumFacing side) {
    if (instance == null || !instance.isInitialized()) {
      return new NBTBuilder().setBoolean("valid", false).build();
    }
    NBTTagCompound root = new NBTTagCompound();
    root.setBoolean("valid", true);

    root.setTag("soilLayer1", new NBTTagIntArray(instance.getSoilLayer1()));

    root.setTag("rockLayer1", new NBTTagIntArray(instance.getRockLayer1()));
    root.setTag("rockLayer2", new NBTTagIntArray(instance.getRockLayer2()));
    root.setTag("rockLayer3", new NBTTagIntArray(instance.getRockLayer3()));
    root.setTag("seaLevelOffset", new NBTTagIntArray(instance.getSeaLevelOffset()));

    root.setTag("stabilityLayer", write(instance.getStabilityLayer()));
    root.setTag("drainageLayer", write(instance.getDrainageLayer()));

    root.setInteger("fishPopulation", instance.getFishPopulation());

    root.setFloat("rainfall", instance.getRainfall());
    root.setFloat("regionalTemp", instance.getRegionalTemp());
    root.setFloat("avgTemp", instance.getAverageTemp());
    root.setFloat("floraDensity", instance.getFloraDensity());
    root.setFloat("floraDiversity", instance.getFloraDiversity());

    root.setInteger("chunkWorkage", instance.getChunkWorkage());
    root.setLong("protectedTicks", instance.getProtectedTicks());
    root.setLong("lastUpdateTick", instance.getLastUpdateTick());
    root.setLong("lastUpdateYear", instance.getLastUpdateYear());

    NBTTagList veinList = new NBTTagList();
    for (Vein vein : instance.getGeneratedVeins()) {
      veinList.appendTag(Vein.serialize(vein));
    }
    root.setTag("veins", veinList);

    return root;
  }

  @Override
  public void readNBT(Capability<ICapabilityChunkData> capability, ICapabilityChunkData instance,
                      EnumFacing side, NBTBase nbt) {
    NBTTagCompound root = (NBTTagCompound) nbt;
    if (nbt != null && root.getBoolean("valid")) {
      System.arraycopy(root.getIntArray("soilLayer1"), 0, instance.getSoilLayer1(), 0, 256);
      System.arraycopy(root.getIntArray("rockLayer1"), 0, instance.getRockLayer1(), 0, 256);
      System.arraycopy(root.getIntArray("rockLayer2"), 0, instance.getRockLayer2(), 0, 256);
      System.arraycopy(root.getIntArray("rockLayer3"), 0, instance.getRockLayer3(), 0, 256);
      System.arraycopy(root.getIntArray("seaLevelOffset"), 0, instance.getSeaLevelOffset(), 0, 256);

      read(instance.getStabilityLayer(), root.getByteArray("stabilityLayer"));
      read(instance.getDrainageLayer(), root.getByteArray("drainageLayer"));

      instance.setFishPopulation(root.getInteger("fishPopulation"));

      instance.setRainfall(root.getFloat("rainfall"));
      instance.setRegionalTemp(root.getFloat("regionalTemp"));
      instance.setAverageTemp(root.getFloat("avgTemp"));
      instance.setFloraDensity(root.getFloat("floraDensity"));
      instance.setFloraDiversity(root.getFloat("floraDiversity"));

      instance.setChunkWorkage(root.getInteger("chunkWorkage"));
      instance.setProtectedTicks(root.getLong("protectedTicks"));
      instance.setLastUpdateTick(root.getLong("lastUpdateTick"));
      instance.setLastUpdateYear(root.getLong("lastUpdateYear"));

      //            instance.getGeneratedVeins() = new HashSet<>();

      NBTTagList veinList = root.getTagList("veins", Constants.NBT.TAG_COMPOUND);
      for (int i = 0; i < veinList.tagCount(); i++) {
        instance.getGeneratedVeins().add(Vein.deserialize(veinList.getCompoundTagAt(i)));
      }

      instance.setInitialized(true);
    }
  }
}
