package su.terrafirmagreg.modules.animal;

import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleTFG;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.network.tile.ITileDataService;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.animal.data.BlocksAnimal;
import su.terrafirmagreg.modules.animal.data.EntitiesAnimal;
import su.terrafirmagreg.modules.animal.data.ItemsAnimal;
import su.terrafirmagreg.modules.animal.data.LootTablesAnimal;

import javax.annotation.Nonnull;

@ModuleTFG(moduleID = "Animal", name = "TFG Module Animal")
public class ModuleAnimal extends ModuleBase {
	public static final Logger LOGGER = LogManager.getLogger(ModuleAnimal.class.getSimpleName());

	public static final CreativeTabs ANIMAL_TAB = new CreativeTabBase("misc", "animal/product/wool");


	public static IPacketService PACKET_SERVICE;
	public static ITileDataService TILE_DATA_SERVICE;

	public ModuleAnimal() {
		super(8);
		this.enableAutoRegistry(ANIMAL_TAB);

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

	public void onClientRegister() {
		EntitiesAnimal.onClientRegister(registryManager);
	}

	@Nonnull
	@Override
	public Logger getLogger() {
		return LOGGER;
	}
}
