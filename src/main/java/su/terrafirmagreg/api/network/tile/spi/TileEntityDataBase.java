package su.terrafirmagreg.api.network.tile.spi;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.network.tile.ITileDataService;
import su.terrafirmagreg.api.util.BlockUtils;

/**
 * This provides a default implementation of the packet update methods.
 * <p>
 * <p>
 * Call {@link TileEntityDataBase#registerTileDataForNetwork(ITileData[])}
 * in the subclass' constructor to register tile data.
 */
public abstract class TileEntityDataBase extends TileEntityDataContainerBase {

	protected final ITileDataService tileDataService;

	protected TileEntityDataBase(ITileDataService tileDataService) {

		this.tileDataService = tileDataService;
	}

	// ---------------------------------------------------------------------------
	// - Network
	// ---------------------------------------------------------------------------

	protected void registerTileDataForNetwork(ITileData[] data) {

		this.tileDataService.register(this, data);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onTileDataUpdate() {
		//
	}

	@NotNull
	@Override
	public NBTTagCompound getUpdateTag() {

		return this.writeToNBT(new NBTTagCompound());
	}

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {

		return new SPacketUpdateTileEntity(this.pos, -1, this.getUpdateTag());
	}

	@Override
	public void onDataPacket(@NotNull NetworkManager networkManager, SPacketUpdateTileEntity packet) {

		this.readFromNBT(packet.getNbtCompound());
		BlockUtils.notifyBlockUpdate(this.world, this.pos);
	}
}
