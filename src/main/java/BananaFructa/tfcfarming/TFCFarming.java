package BananaFructa.tfcfarming;

import BananaFructa.tfcfarming.firmalife.TEHangingPlanterN;
import BananaFructa.tfcfarming.firmalife.TEPlanterN;
import BananaFructa.tfcfarming.firmalife.TEStemCropN;
import BananaFructa.tfcfarming.network.PacketHandler;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import su.terrafirmagreg.Tags;

import static su.terrafirmagreg.api.lib.Constants.*;

@Mod(modid = MODID_TFCFARMING, name = TFCFarming.name, version = Tags.VERSION, dependencies = "required-after:tfc;after:tfcflorae;after:firmalife")
public class TFCFarming {

	public static final String name = "TFC Farming";

	public static TFCFarming INSTANCE;
	public static boolean tfcfloraeLoaded = false;
	public static boolean firmalifeLoaded = false;
	@SidedProxy(modId = MODID_TFCFARMING, clientSide = "BananaFructa.tfcfarming.ClientProxy", serverSide = "BananaFructa.tfcfarming.CommonProxy")
	public static CommonProxy proxy;
	public FarmingWorldStorage worldStorage;

	public TFCFarming() {
		tfcfloraeLoaded = Loader.isModLoaded(MODID_TFCF);
		firmalifeLoaded = Loader.isModLoaded(MODID_FL);
		INSTANCE = this;
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		Config.load(event.getModConfigurationDirectory());
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(proxy);
		PacketHandler.registerPackets();
		GameRegistry.registerTileEntity(TECropBaseN.class, new ResourceLocation(MODID_TFCFARMING, TECropBaseN.class.getSimpleName()));
		if (firmalifeLoaded) {
			GameRegistry.registerTileEntity(TEPlanterN.class, new ResourceLocation(MODID_TFCFARMING, TEPlanterN.class.getSimpleName()));
			GameRegistry.registerTileEntity(TEHangingPlanterN.class, new ResourceLocation(MODID_TFCFARMING, TEHangingPlanterN.class.getSimpleName()));
			GameRegistry.registerTileEntity(TEStemCropN.class, new ResourceLocation(MODID_TFCFARMING, TEStemCropN.class.getSimpleName()));
		}
		proxy.init();
	}

	@Mod.EventHandler
	public void serverStaring(FMLServerStartingEvent event) {
		worldStorage = FarmingWorldStorage.get(event.getServer().getWorld(0));
	}

}
