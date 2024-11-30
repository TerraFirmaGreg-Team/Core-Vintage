package su.terrafirmagreg.modules.worldgen.classic.objects.layer.biome;

import su.terrafirmagreg.modules.worldgen.classic.objects.layer.GenLayerBase;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerBiomeEdge extends GenLayerBase {

  public GenLayerBiomeEdge(long seed, GenLayer parent) {
    super(seed);
    this.parent = parent;
  }

  public int[] getInts(int x, int z, int xSize, int zSize) {
    int[] inCache = this.parent.getInts(x - 1, z - 1, xSize + 2, zSize + 2);
    int[] outCache = IntCache.getIntCache(xSize * zSize);

    for (int zz = 0; zz < zSize; ++zz) {
      for (int xx = 0; xx < xSize; ++xx) {
        this.initChunkSeed(xx + x, zz + z);
        int thisID = inCache[xx + 1 + (zz + 1) * (xSize + 2)];
        int zn = inCache[xx + 1 + (zz + 1 - 1) * (xSize + 2)];
        int xp = inCache[xx + 1 + 1 + (zz + 1) * (xSize + 2)];
        int xn = inCache[xx + 1 - 1 + (zz + 1) * (xSize + 2)];
        int zp = inCache[xx + 1 + (zz + 1 + 1) * (xSize + 2)];
        if (thisID == this.highHillsID) {
          if (zn == this.highHillsID && xp == this.highHillsID && xn == this.highHillsID
              && zp == this.highHillsID) {
            outCache[xx + zz * xSize] = thisID;
          } else {
            outCache[xx + zz * xSize] = this.highHillsEdgeID;
          }
        } else if (thisID == this.mountainsID) {
          if (zn == this.mountainsID && xp == this.mountainsID && xn == this.mountainsID
              && zp == this.mountainsID) {
            outCache[xx + zz * xSize] = thisID;
          } else {
            outCache[xx + zz * xSize] = this.mountainsEdgeID;
          }
        } else if (thisID == this.swamplandID) {
          if (zn == this.swamplandID && xp == this.swamplandID && xn == this.swamplandID
              && zp == this.swamplandID) {
            outCache[xx + zz * xSize] = thisID;
          } else {
            outCache[xx + zz * xSize] = this.plainsID;
          }
        } else if (thisID == this.bayouID) {
          if (zn == this.bayouID && xp == this.bayouID && xn == this.bayouID
              && zp == this.bayouID) {
            outCache[xx + zz * xSize] = thisID;
          } else {
            outCache[xx + zz * xSize] = this.marshID;
          }
        } else if (thisID == this.marshID) {
          if (zn == this.marshID && xp == this.marshID && xn == this.marshID
              && zp == this.marshID) {
            outCache[xx + zz * xSize] = thisID;
          } else {
            outCache[xx + zz * xSize] = this.swamplandID;
          }
        } else if (thisID == this.highPlainsID) {
          if (zn == this.highPlainsID && xp == this.highPlainsID && xn == this.highPlainsID
              && zp == this.highPlainsID) {
            outCache[xx + zz * xSize] = thisID;
          } else {
            outCache[xx + zz * xSize] = this.plainsID;
          }
        } else if (thisID == this.plainsID) {
          if (zn == this.plainsID && xp == this.plainsID && xn == this.plainsID
              && zp == this.plainsID) {
            outCache[xx + zz * xSize] = thisID;
          } else {
            outCache[xx + zz * xSize] = this.fieldsID;
          }
        } else if (thisID == this.rollingHillsID) {
          if (zn == this.rollingHillsID && xp == this.rollingHillsID && xn == this.rollingHillsID
              && zp == this.rollingHillsID) {
            outCache[xx + zz * xSize] = thisID;
          } else {
            outCache[xx + zz * xSize] = this.flatlandsID;
          }
        } else if (thisID != this.flatlandsID && thisID != this.fieldsID) {
          if (thisID != this.mesaPlateauMID && thisID != this.mesaPlateauID
              && thisID != this.mesaBryceID) {
            outCache[xx + zz * xSize] = thisID;
          } else if (
            (zn != this.mesaPlateauMID || xp != this.mesaPlateauMID || xn != this.mesaPlateauMID
             || zp != this.mesaPlateauMID) &&
            (zn != this.mesaPlateauID || xp != this.mesaPlateauID || xn != this.mesaPlateauID
             || zp != this.mesaPlateauID) &&
            (zn != this.mesaBryceID || xp != this.mesaBryceID || xn != this.mesaBryceID
             || zp != this.mesaBryceID)) {
            outCache[xx + zz * xSize] = this.mesaID;
          } else {
            outCache[xx + zz * xSize] = thisID;
          }
        } else if ((zn != this.flatlandsID || xp != this.flatlandsID || xn != this.flatlandsID
                    || zp != this.flatlandsID) &&
                   (zn != this.fieldsID || xp != this.fieldsID || xn != this.fieldsID
                    || zp != this.fieldsID)) {
          outCache[xx + zz * xSize] = this.meadowsID;
        } else {
          outCache[xx + zz * xSize] = thisID;
        }
      }
    }

    return outCache;
  }
}
