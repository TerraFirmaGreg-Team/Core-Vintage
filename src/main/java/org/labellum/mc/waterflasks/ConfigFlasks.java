package org.labellum.mc.waterflasks;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;

import static su.terrafirmagreg.api.lib.Constants.MODID_WATERFLASKS;

/**
 * Top level items must be static, the subclasses' fields must not be static.
 */
@Config(modid = MODID_WATERFLASKS, category = "")
@Mod.EventBusSubscriber(modid = MODID_WATERFLASKS)
@Config.LangKey("config." + MODID_WATERFLASKS)
//@SuppressWarnings("WeakerAccess")
public class ConfigFlasks {
	@Config.Comment("General settings")
	@Config.LangKey("config." + MODID_WATERFLASKS + ".general")
	public static final GeneralCFG GENERAL = new GeneralCFG();

	@SubscribeEvent
	public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(MODID_WATERFLASKS)) {
			LogManager.getLogger(MODID_WATERFLASKS).warn("Config changed");
			ConfigManager.sync(MODID_WATERFLASKS, Config.Type.INSTANCE);
		}
	}

	public static class GeneralCFG {
		@Config.Comment("Liquid Capacity of Leather Flask (500 = 1/2 bucket = 5 drinks or 2 water bars)")
		@Config.RangeInt(min = 100)
		@Config.LangKey("config." + MODID_WATERFLASKS + ".general.leatherCap")
		public int leatherCap = 500;

		@Config.Comment("Liquid Capacity of Iron Flask (1000 = 1 bucket = 10 drinks or 4 water bars)")
		@Config.RangeInt(min = 100)
		@Config.LangKey("config." + MODID_WATERFLASKS + ".general.ironCap")
		public int ironCap = 2000;

		@Config.Comment("Enable Iron Flask")
		@Config.LangKey("config." + MODID_WATERFLASKS + ".general.enableIron")
		public boolean enableIron = true;

		@Config.Comment("Damage Capability of Flasks are Capacity/(this value) 0 = MAXINT uses")
		@Config.RangeInt(min = 0)
		@Config.LangKey("config." + MODID_WATERFLASKS + ".general.damageFactor")
		public int damageFactor = 5;
	}
}
