package su.terrafirmagreg.modules.metal;

import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.network.tile.ITileDataService;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.metal.data.BlocksMetal;
import su.terrafirmagreg.modules.metal.data.ItemsMetal;

//@ModuleTFG(moduleID = "Metal", name = "TFG Module Metal")
public final class ModuleMetal extends ModuleBase {
	public static final Logger LOGGER = LogManager.getLogger(ModuleMetal.class.getSimpleName());
	public static final CreativeTabs METAL_TAB = new CreativeTabBase("metal", "metal/anvil/red_steel");

	public static IPacketService PACKET_SERVICE;
	public static ITileDataService TILE_DATA_SERVICE;

	public ModuleMetal() {
		super(8);
		this.enableAutoRegistry(METAL_TAB);

		PACKET_SERVICE = this.enableNetwork();
		TILE_DATA_SERVICE = this.enableNetworkTileDataService(PACKET_SERVICE);
	}

	@Override
	public void onRegister() {
		BlocksMetal.onRegister(registryManager);
		ItemsMetal.onRegister(registryManager);
	}

	@Override
	public @NotNull Logger getLogger() {
		return LOGGER;
	}
}
