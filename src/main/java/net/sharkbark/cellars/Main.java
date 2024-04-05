package net.sharkbark.cellars;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.sharkbark.cellars.proxy.CommonProxy;
import net.sharkbark.cellars.util.Reference;
import su.terrafirmagreg.Tags;

import static su.terrafirmagreg.api.lib.Constants.MODID_CELLARS;

@Mod(modid = MODID_CELLARS, name = Reference.NAME, version = Tags.VERSION, dependencies = "required-after:tfc")
@Mod.EventBusSubscriber(modid = MODID_CELLARS)
public class Main {

	@Mod.Instance
	public static Main INSTANCE;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	

	@Mod.EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		ModConfig.loadConfig(event);
	}


}
