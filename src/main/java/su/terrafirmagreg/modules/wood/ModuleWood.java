package su.terrafirmagreg.modules.wood;


import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleTFG;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.network.tile.ITileDataService;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypeHandler;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariantHandler;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariantHandler;
import su.terrafirmagreg.modules.wood.data.BlocksWood;
import su.terrafirmagreg.modules.wood.data.EntitiesWood;
import su.terrafirmagreg.modules.wood.data.ItemsWood;
import su.terrafirmagreg.modules.wood.data.PacketWood;

import java.util.Collections;
import java.util.List;

@ModuleTFG(moduleID = "Wood", name = "TFG Module Wood")
public class ModuleWood extends ModuleBase {

	public static final Logger LOGGER = LogManager.getLogger(ModuleWood.class.getSimpleName());
	public static final CreativeTabs WOOD_TAB = new CreativeTabBase("wood", "wood/planks/pine");

	public static IPacketService PACKET_SERVICE;
	public static ITileDataService TILE_DATA_SERVICE;

	public ModuleWood() {
		super(4);
		this.enableAutoRegistry(WOOD_TAB);

		PACKET_SERVICE = this.enableNetwork();
		TILE_DATA_SERVICE = this.enableNetworkTileDataService(PACKET_SERVICE);
	}

	@Override
	public void onRegister() {
		WoodTypeHandler.init();
		WoodBlockVariantHandler.init();
		WoodItemVariantHandler.init();
		//        WoodTreeVariantHandler.init();

		BlocksWood.onRegister(registryManager);
		ItemsWood.onRegister(registryManager);
		EntitiesWood.onRegister(registryManager);
	}

	@Override
	public void onClientRegister() {
		BlocksWood.onClientRegister(registryManager);
		EntitiesWood.onClientRegister(registryManager);

	}

	@Override
	public void onNetworkRegister() {
		PacketWood.onRegister(packetRegistry);

	}

	@Override
	public @NotNull Logger getLogger() {
		return LOGGER;
	}

	@NotNull
	@Override
	public List<Class<?>> getEventBusSubscribers() {
		return Collections.singletonList(ModuleWood.class);
	}
}
