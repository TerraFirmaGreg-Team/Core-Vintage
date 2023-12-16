package com.lumintorious.ambiental;

import com.lumintorious.ambiental.capability.TemperatureCapability;
import com.lumintorious.ambiental.capability.TemperaturePacket;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.DumbStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = TFCAmbiental.MODID,
		name = TFCAmbiental.NAME,
		version = TFCAmbiental.VERSION,
		dependencies = "required:tfc;required:gregtech;required:firmalife;required:cellars;required:tfctech;"
)
public class TFCAmbiental {
	public static final String MODID = "tfcambiental";
	public static final String NAME = "TFC Ambiental";
	public static final String VERSION = "2.1";

	public static final Logger logger = LogManager.getLogger(MODID);

	public static boolean isGTCEuLoaded = false;
	public static boolean isFirmaLifeLoaded = false;
	public static boolean isTFCTechLoaded = false;
	public static boolean isCellarsAddonLoaded = false;

	@EventHandler
	public void onConstruct(FMLConstructionEvent event) {
		if (Loader.isModLoaded("gregtech")) {
			isGTCEuLoaded = true;

			logger.info("GTCEu Compat Enabled!");
		}

		if (Loader.isModLoaded("firmalife")) {
			isFirmaLifeLoaded = true;

			logger.info("FirmaLife Compat Enabled!");
		}

		if (Loader.isModLoaded("tfctech")) {
			isTFCTechLoaded = true;

			logger.info("TFCTech Compat Enabled!");
		}

		if (Loader.isModLoaded("cellars")) {
			isCellarsAddonLoaded = true;

			logger.info("CellarAddon Compat Enabled!");
		}
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// Common Events
		MinecraftForge.EVENT_BUS.register(new TFCAmbientalEventHandler());

		// Client Events
		if (event.getSide() == Side.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new TFCAmbientalGuiRenderer());
		}

		// Capability Registry
		CapabilityManager.INSTANCE.register(TemperatureCapability.class, new DumbStorage(), () -> null);

		TerraFirmaCraft.getNetwork()
		               .registerMessage(new TemperaturePacket.Handler(), TemperaturePacket.class, 0, Side.CLIENT);
	}
}
