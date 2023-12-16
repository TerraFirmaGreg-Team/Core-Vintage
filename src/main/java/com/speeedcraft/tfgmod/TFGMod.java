package com.speeedcraft.tfgmod;

import com.speeedcraft.tfgmod.gregtech.items.TFGModMetaItem;
import com.speeedcraft.tfgmod.gregtech.items.tools.TFGToolItems;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = TFGMod.MOD_ID,
		name = TFGMod.MOD_NAME,
		version = TFGMod.VERSION,
		dependencies = TFGMod.DEPENDENCIES
)
public class TFGMod {

	public static final String MOD_ID = "tfgmod";
	public static final String MOD_NAME = "TerraFirmaGreg";
	public static final String VERSION = "@VERSION@";
	public static final String DEPENDENCIES =
			"required:forge@[14.23.5.2847,);" +
					"required:mixinbooter;" +
					"required:tfc;" +
					"required:gregtech;";

	public static final Logger LOGGER = LogManager.getLogger("tfgmod");

	@EventHandler
	public void onConstruct(FMLConstructionEvent event) {
		LOGGER.info("TerraFirmaGreg Mod by SpeeeDCraft and Xikaro is working :)");
	}

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		TFGToolItems.init();
		TFGModMetaItem.init();
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event) {

	}

	@EventHandler
	public void onPostInit(FMLPostInitializationEvent event) {
		TFGRecipes.register();
	}
}
