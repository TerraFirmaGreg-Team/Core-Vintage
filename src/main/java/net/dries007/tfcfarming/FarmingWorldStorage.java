package net.dries007.tfcfarming;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.BlockSnowBlock;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropDead;
import net.dries007.tfc.objects.blocks.agriculture.BlockCropTFC;
import net.dries007.tfc.objects.blocks.stone.BlockFarmlandTFC;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.objects.blocks.blocktype.farmland.FarmlandTFCF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FarmingWorldStorage extends WorldSavedData {

  public static final String dataName = TFCFarming.modId + "_WORLD_STORAGE";
  private static long ft = 0;

  static {
    for (int i = 7 * 4 - 1; i >= 0; i--) {
      ft >>= i;
      ft |= 1;
      ft <<= i;
    }
  }

  private final int[] maximumNaturalNPK = {255, 255, 255};
  public TETickCounter teTickCounter = new TETickCounter();
  public HashMap<Long, Integer> nutrientMap;

  public FarmingWorldStorage(String name) {
    super(name);
  }

  public FarmingWorldStorage() {
    super(dataName);
    nutrientMap = new HashMap<>();
  }

  public static FarmingWorldStorage get(World world) {
    MapStorage storage = world.getMapStorage();
    FarmingWorldStorage farmingWorldStorage = (FarmingWorldStorage) storage.getOrLoadData(FarmingWorldStorage.class, dataName);
    if (farmingWorldStorage == null) {
      farmingWorldStorage = new FarmingWorldStorage();
      storage.setData(dataName, farmingWorldStorage);
    }
    return farmingWorldStorage;
  }

  private long convertPosition(int x, int z) {
    long v = 0;
    v |= x + 30000000;
    v <<= 7 * 4;
    v |= z + 30000000;
    return v;
  }

  private Tuple<Integer, Integer> getPosition(long v) {
    int z = (int) (v & ft) - 30000000;
    v >>= 7 * 4;
    int x = (int) (v & ft) - 30000000;
    return new Tuple<>(x, z);
  }

  public void performCleanup() {
    synchronized (nutrientMap) {
      List<Long> toRemove = new ArrayList<>();
      Set<Long> keys = nutrientMap.keySet();
      for (long l : keys) {
        if (nutrientMap.get(l) == 0xffffff) {
          toRemove.add(l);
        }
      }
      for (long l : toRemove) {
        Tuple<Integer, Integer> p = getPosition(l);
        nutrientMap.remove(l);
      }
      if (!toRemove.isEmpty()) {markDirty();}
    }

  }

  private NutrientValues getDefNutrients() {
    return new NutrientValues(maximumNaturalNPK);
  }

  public void globalIncreaseUpdate(World world, NutrientClass nutrientClass, int amount) {
    synchronized (nutrientMap) {

      Set<Long> keySet = nutrientMap.keySet();

      for (long l : keySet) {

        boolean deadCrop = false;

        // check if there is a block obstructing the farmed land
        Tuple<Integer, Integer> pos = getPosition(l);
        boolean found = false;

        for (BlockPos p = new BlockPos(pos.getFirst(), 255, pos.getSecond()); p.getY() > -1; p = p.down()) {
          Block b = world.getBlockState(p).getBlock();
          if (b != Blocks.AIR) {
            if (b instanceof BlockSnow || b instanceof BlockSnowBlock || world.getBlockState(p).getMaterial() == Material.WATER) {continue;}
            if (b instanceof BlockCropDead) {
              deadCrop = true;
              continue;
            }
            if (b instanceof BlockCropTFC cropTFC) {
              NutrientClass nc = CropNutrients.getCropNValues(cropTFC.getCrop()).favouriteNutrient;
              if (nc == nutrientClass) {
                break; // the top crop is already absorbing that nutrient
              } else {continue;}
            }
            if (b instanceof BlockFarmlandTFC || b instanceof FarmlandTFCF) {
              found = true;
            }
            break; // obstructing block in the way
          }
        }
        if (!found) {continue;}
        fertilizerBlock(l, nutrientClass, (int) (amount * (deadCrop ? Config.growthDead : 1))); // the block is equivalent to default, remove

      }
    }
  }

  public void resetCounter() {
    teTickCounter.resetCounter();
    markDirty();
  }

  public NutrientValues getNutrientValues(int x, int z) {
    synchronized (nutrientMap) {
      long pos = convertPosition(x, z);
      if (!nutrientMap.containsKey(pos)) {
        return getDefNutrients();
      } else {
        return new NutrientValues(nutrientMap.get(pos));
      }
    }
  }

  public void setNutrientValues(int x, int z, NutrientValues values) {
    synchronized (nutrientMap) {
      long pos = convertPosition(x, z);
      nutrientMap.put(pos, values.packToInt());
    }
    markDirty();
  }

  public boolean fertilizerBlock(long l, NutrientClass nutrientClass, int amount) {
    synchronized (nutrientMap) {
      if (nutrientMap.containsKey(l)) {
        NutrientValues nutrientValues = new NutrientValues(nutrientMap.get(l));
        boolean success = nutrientValues.addNutrient(nutrientClass, amount);
        if (success) {
          nutrientMap.put(l, nutrientValues.packToInt());
          markDirty();
          return true;
        } else {return false;}

      } else {return false;}
    }
  }

  public boolean fertilizerBlock(int x, int y, NutrientClass nutrientClass, int amount) {
    long l = convertPosition(x, y);
    return fertilizerBlock(l, nutrientClass, amount);
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    teTickCounter.readFromNBT(nbt);
    Gson gson = new Gson();

    try {
      nutrientMap = gson.fromJson(nbt.getString("nutrientMap"), new TypeToken<HashMap<Long, Integer>>() {}.getType());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    teTickCounter.writeToNBT(compound);

    Gson gson = new Gson();

    try {
      compound.setString("nutrientMap", gson.toJson(nutrientMap));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return compound;
  }
}
