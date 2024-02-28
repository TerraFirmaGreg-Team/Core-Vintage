package com.lumintorious.ambiental;

import com.lumintorious.ambiental.capability.ITemperatureCapability;
import com.lumintorious.ambiental.capability.TemperaturePacket;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.DumbStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import su.terrafirmagreg.Tags;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFCAMBIENTAL;

@Mod(modid = MODID_TFCAMBIENTAL, name = TFCAmbiental.NAME, version = Tags.VERSION)
public class TFCAmbiental {
	public static final String NAME = "TFC Ambiental";

	@Mod.Instance
	public static TFCAmbiental INSTANCE;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new AmbientalHandler());
		if (event.getSide() == Side.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new GuiRenderer());
		}
		CapabilityManager.INSTANCE.register(ITemperatureCapability.class, new DumbStorage(), () -> null);
//    	CapabilityManager.INSTANCE.register(TimeExtensionCapability.class, new DumbStorage(), () -> null);


		TerraFirmaCraft.getNetwork()
		               .registerMessage(new TemperaturePacket.Handler(), TemperaturePacket.class, 0, Side.CLIENT);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

	}

}
