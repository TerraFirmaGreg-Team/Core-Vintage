package su.terrafirmagreg.modules.animal;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleTFG;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.network.tile.ITileDataService;
import su.terrafirmagreg.modules.animal.data.BlocksAnimal;
import su.terrafirmagreg.modules.animal.data.EntitiesAnimal;
import su.terrafirmagreg.modules.animal.data.ItemsAnimal;
import su.terrafirmagreg.modules.animal.data.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.event.EasyBreedingEventHandler;

import static su.terrafirmagreg.modules.core.ModuleCore.CORE_TAB;

@ModuleTFG(moduleID = "Animal", name = "TFG Module Animal")
public final class ModuleAnimal extends ModuleBase {
	public static final Logger LOGGER = LogManager.getLogger(ModuleAnimal.class.getSimpleName());


	public static IPacketService PACKET_SERVICE;
	public static ITileDataService TILE_DATA_SERVICE;

	public ModuleAnimal() {
		super(8);
		this.enableAutoRegistry(CORE_TAB);

		PACKET_SERVICE = this.enableNetwork();
		TILE_DATA_SERVICE = this.enableNetworkTileDataService(PACKET_SERVICE);
	}

	@Override
	public void onRegister() {
		BlocksAnimal.onRegister(registryManager);
		ItemsAnimal.onRegister(registryManager);
		EntitiesAnimal.onRegister(registryManager);
		LootTablesAnimal.onRegister(registryManager);
	}

	@Override
	public void onClientRegister() {
		EntitiesAnimal.onClientRegister(registryManager);

	}

	@Override
	public void onInit(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new EasyBreedingEventHandler());
	}

	@Override
	public @NotNull Logger getLogger() {
		return LOGGER;
	}
}
