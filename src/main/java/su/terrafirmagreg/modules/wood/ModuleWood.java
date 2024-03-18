package su.terrafirmagreg.modules.wood;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleTFG;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.network.tile.ITileDataService;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.core.api.capabilities.pull.IPullCapability;
import su.terrafirmagreg.modules.core.api.capabilities.pull.PullCapability;
import su.terrafirmagreg.modules.core.api.capabilities.pull.PullStorage;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypeHandler;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariantHandler;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariantHandler;
import su.terrafirmagreg.modules.wood.data.*;
import su.terrafirmagreg.modules.wood.event.EntityJoinWorldEventHandler;
import su.terrafirmagreg.modules.wood.event.KeyEventHandler;
import su.terrafirmagreg.modules.wood.event.MissingMappingEventHandler;

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

		MinecraftForge.EVENT_BUS.register(new EntityJoinWorldEventHandler());
		MinecraftForge.EVENT_BUS.register(new MissingMappingEventHandler());
	}

	@Override
	public void onRegister() {
		WoodTypeHandler.init();
		WoodBlockVariantHandler.init();
		WoodItemVariantHandler.init();

		BlocksWood.onRegister(registryManager);
		ItemsWood.onRegister(registryManager);
		EntitiesWood.onRegister(registryManager);
	}

	@Override
	public void onClientRegister() {
		BlocksWood.onClientRegister(registryManager);
		EntitiesWood.onClientRegister(registryManager);
		KeybindingsWood.onClientRegister(registryManager);

	}

	@Override
	public void onNetworkRegister() {
		PacketWood.onRegister(packetRegistry);

	}

	@Override
	public void onPreInit(FMLPreInitializationEvent event) {
		CapabilityManager.INSTANCE.register(IPullCapability.class, new PullStorage(), PullCapability::new);
	}

	@Override
	public void onClientPreInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new KeyEventHandler());
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
