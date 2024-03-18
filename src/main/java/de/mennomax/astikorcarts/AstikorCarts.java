package de.mennomax.astikorcarts;

import de.mennomax.astikorcarts.handler.PacketHandler;
import de.mennomax.astikorcarts.proxy.IProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import su.terrafirmagreg.Tags;

import static su.terrafirmagreg.api.lib.Constants.MODID_ASTIKORCARTS;

@Mod(modid = MODID_ASTIKORCARTS, version = Tags.VERSION)
public class AstikorCarts {

	@SidedProxy(clientSide = "de.mennomax.astikorcarts.proxy.ClientProxy", serverSide = "de.mennomax.astikorcarts.proxy.ServerProxy")
	public static IProxy proxy;

	@Instance(MODID_ASTIKORCARTS)
	public static AstikorCarts instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PacketHandler.registerPackets();
//		CapabilityManager.INSTANCE.register(IPullCapability.class, new PullStorage(), PullCapability::new);
		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit();
	}
}
