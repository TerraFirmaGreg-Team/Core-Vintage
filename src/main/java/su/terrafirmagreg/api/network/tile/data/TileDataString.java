package su.terrafirmagreg.api.network.tile.data;

import net.minecraft.network.PacketBuffer;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.network.tile.spi.TileDataBase;

public class TileDataString extends TileDataBase {

	private String value = "";

	public TileDataString(@NotNull String initialValue) {

		this(initialValue, 1);
	}

	public TileDataString(@NotNull String initialValue, int updateInterval) {

		super(updateInterval);
		this.set(initialValue);
	}

	public void set(@NotNull String value) {

		if (!this.value.equals(value)) {
			this.value = value;
			this.setDirty(true);
		}
	}

	public String get() {

		return this.value;
	}

	@Override
	public void read(PacketBuffer buffer) {

		int length = buffer.readInt();
		this.value = buffer.readString(length);
	}

	@Override
	public void write(PacketBuffer buffer) {

		buffer.writeInt(this.value.length());
		buffer.writeString(this.value);
	}

}
