package su.terrafirmagreg.modules.core.capabilities.worldtracker.spi;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.List;

public class CollapseData implements INBTSerializable<NBTTagCompound> {

    private static final String TAG_CENTER_POS = "centerPos";
    private static final String TAG_RADIUS_SQUARED = "radiusSquared";

    public final List<BlockPos> nextPositions;
    public BlockPos centerPos;
    public double radiusSquared;

    public CollapseData(BlockPos centerPos, List<BlockPos> nextPositions, double radiusSquared) {
        this.centerPos = centerPos;
        this.nextPositions = nextPositions;
        this.radiusSquared = radiusSquared;
    }

    public CollapseData(NBTTagCompound nbt) {
        this.nextPositions = new ArrayList<>();
        deserializeNBT(nbt);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong(TAG_CENTER_POS, centerPos.toLong());
        NBTTagList list = new NBTTagList();
        for (BlockPos pos : nextPositions) {
            NBTTagCompound posTag = new NBTTagCompound();
            posTag.setInteger("x", pos.getX());
            posTag.setInteger("y", pos.getY());
            posTag.setInteger("z", pos.getZ());
            list.appendTag(posTag);
        }
        nbt.setDouble(TAG_RADIUS_SQUARED, radiusSquared);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt != null) {
            centerPos = BlockPos.fromLong(nbt.getLong(TAG_CENTER_POS));
            NBTTagList list = nbt.getTagList("nextPositions", Constants.NBT.TAG_COMPOUND);
            nextPositions.clear();
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound posTag = list.getCompoundTagAt(i);
                nextPositions.add(
                        new BlockPos(
                                posTag.getInteger("x"),
                                posTag.getInteger("y"),
                                posTag.getInteger("z")
                        ));
            }
            radiusSquared = nbt.getDouble(TAG_RADIUS_SQUARED);
        }
    }
}
