package su.terrafirmagreg.modules.device.objects.tiles;

import net.dries007.tfc.objects.te.TEBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TEInfectedAir extends TEBase implements ITickable {

	public TEInfectedAir() {}

	@Override
	public void update() {}

	private void writeSyncData(NBTTagCompound nbt) {}

	private void readSyncData(NBTTagCompound nbt) {}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		writeSyncData(nbt);
		return new SPacketUpdateTileEntity(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), 1, nbt);
	}

	@Override
	public void onDataPacket(@NotNull NetworkManager net, SPacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
		readSyncData(packet.getNbtCompound());
	}
}

