package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.base.object.tile.spi.BaseTile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

public class TileInfectedAir extends BaseTile implements ITickable {

  public TileInfectedAir() {
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

