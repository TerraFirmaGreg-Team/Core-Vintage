package net.dries007.sharkbark.cellars.blocks.tileentity;

import net.dries007.tfc.objects.te.TEBase;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TEInfectedAir extends TEBase implements ITickable {

  public TEInfectedAir() {
  }

  @Override
  public void update() {

  }

  private void writeSyncData(NBTTagCompound tagCompound) {
  }

  private void readSyncData(NBTTagCompound tagCompound) {
  }

  @Nullable
  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {
    NBTTagCompound tagCompound = new NBTTagCompound();
    writeToNBT(tagCompound);
    writeSyncData(tagCompound);
    return new SPacketUpdateTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), 1, tagCompound);
  }

  @Override
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
    readFromNBT(packet.getNbtCompound());
    readSyncData(packet.getNbtCompound());
  }
}

