package net.dries007.tfc.world.classic.genlayers.ridge;

import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.world.classic.genlayers.GenLayerTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.DimensionManager;

import javax.annotation.Nonnull;

public class GenLayerRidgeTFC extends GenLayerTFC {
    public GenLayerRidgeTFC(long par1, GenLayer par3GenLayer) {
        super(par1);
        super.parent = par3GenLayer;
    }

    @Nonnull
    @Override
    public int[] getInts(int xCoord, int zCoord, int xSize, int zSize) {
        World world = null;
        if (Minecraft.getMinecraft().world != null) {
            world = Minecraft.getMinecraft().world;
        } else if (DimensionManager.getWorld(0) != null) {
            world = DimensionManager.getWorld(0);
        }

        int areaRadius = 14;
        int peakRadius = 2;
        int rangeRadius = 9;
        int parentXCoord = xCoord - areaRadius;
        int parentZCoord = zCoord - areaRadius;
        int parentXSize = xSize + 2 * areaRadius;
        int parentZSize = zSize + 2 * areaRadius;
        int[] parentCache = new int[parentXSize * parentZSize];
        int[] parentCache2 = this.parent.getInts(parentXCoord, parentZCoord, parentXSize, parentZSize);

        int z;
        for (z = 0; z < parentZSize; ++z) {
            for (z = 0; z < parentXSize; ++z) {
                if (world != null) {
                    BlockPos blockpos = new BlockPos(parentXCoord + z << 4, 0, parentZCoord + z << 4);
                    Chunk chunk = world.getChunk(blockpos);
                    ChunkDataTFC data = chunk.getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);
                    if (data.isInitialized()) {
                        parentCache[z + z * parentZSize] = data.getStability(blockpos.getX(), blockpos.getZ());
                    } else {
                        parentCache[z + z * parentZSize] = 0;
                    }
                } else {
                    parentCache[z + z * parentZSize] = 0;
                }
            }
        }

        int[] outCache = IntCache.getIntCache(xSize * zSize);

        for (z = 0; z < zSize; ++z) {
            for (int x = 0; x < xSize; ++x) {
                int index = areaRadius + x + (z + areaRadius) * parentXSize;
                boolean canDoPeak = parentCache2[index] == 3;
                boolean canDoEdge = parentCache2[index] >= 2;
                boolean canDoFoothills = parentCache2[index] >= 1;
                int var10000 = x + z * xSize;
                boolean same = canDoFoothills;
                boolean same2 = canDoPeak;
                boolean same3 = canDoEdge;
                boolean initialVal = false;
                int initialValue = -1;
                boolean initialVal2 = false;
                int initialValue2 = -1;
                boolean initialVal3 = false;
                int initialValue3 = -1;

                for (int rX = 0; rX < areaRadius * 2 + 1 && (same || same2 || same3); ++rX) {
                    for (int rZ = 0; rZ < areaRadius * 2 + 1 && (same || same2 || same3); ++rZ) {
                        if (Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= areaRadius && same) {
                            if (!initialVal) {
                                initialValue = this.calcWidth(parentCache[x + rX + (z + rZ) * parentXSize]);
                                initialVal = true;
                            } else {
                                same = this.calcWidth(parentCache[x + rX + (z + rZ) * parentXSize]) == initialValue;
                            }
                        }

                        if (Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= peakRadius && same2) {
                            if (!initialVal2) {
                                initialValue2 = this.calcWidth(parentCache[x + rX + (z + rZ) * parentXSize]);
                                initialVal2 = true;
                            } else {
                                same2 = this.calcWidth(parentCache[x + rX + (z + rZ) * parentXSize]) == initialValue2;
                            }
                        }

                        if (Math.abs(rX - areaRadius) + Math.abs(rZ - areaRadius) <= rangeRadius && same3) {
                            if (!initialVal3) {
                                initialValue3 = this.calcWidth(parentCache[x + rX + (z + rZ) * parentXSize]);
                                initialVal3 = true;
                            } else {
                                same3 = this.calcWidth(parentCache[x + rX + (z + rZ) * parentXSize]) == initialValue3;
                            }
                        }
                    }
                }

                if (!same && canDoFoothills) {
                    if (same3 && canDoFoothills && !same) {
                        outCache[x + z * xSize] = this.foothillsID;
                    } else if (same2 && canDoEdge && !same) {
                        outCache[x + z * xSize] = this.mountainRangeEdgeID;
                    } else if (canDoPeak && !same) {
                        outCache[x + z * xSize] = this.mountainRangeID;
                    } else {
                        outCache[x + z * xSize] = 0;
                    }
                } else {
                    outCache[x + z * xSize] = 0;
                }
            }
        }

        return outCache;
    }

    private int calcWidth(int i) {
        return i >= 2 ? 2 + (i & 1) : i;
    }
}
