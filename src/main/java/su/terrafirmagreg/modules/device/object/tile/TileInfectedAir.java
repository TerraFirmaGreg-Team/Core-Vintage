package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.base.tile.BaseTileTickable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.math.BlockPos;

import org.jetbrains.annotations.Nullable;

public class TileInfectedAir extends BaseTileTickable {

  public TileInfectedAir() {
  }

  @Override
  public void update() {
  }

  @Nullable
  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {
    NBTTagCompound nbt = new NBTTagCompound();
    writeToNBT(nbt);
    return new SPacketUpdateTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), 1, nbt);
  }


}

