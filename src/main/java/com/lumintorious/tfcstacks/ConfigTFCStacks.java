package com.lumintorious.tfcstacks;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Constants.MODID_TFCSTACKS;

@Config(modid = MODID_TFCSTACKS, category = "")
@Mod.EventBusSubscriber(modid = MODID_TFCSTACKS)
@Config.LangKey("config." + MODID_TFCSTACKS)
public class ConfigTFCStacks {
	@Config.Comment("General settings")
	@Config.LangKey("config." + MODID_TFCSTACKS + ".general")
	public static final GeneralCFG GENERAL = new GeneralCFG();

	@SubscribeEvent
	public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(MODID_TFCSTACKS)) {
			ConfigManager.sync(MODID_TFCSTACKS, Config.Type.INSTANCE);
		}
	}

	public static class GeneralCFG {
		@Config.RequiresMcRestart
		@Config.Comment("Stack size of Very Heavy items. I wouldn't change this one. Default = 1")
		@Config.LangKey("config." + MODID_TFCSTACKS + ".general.VERY_HEAVY")
		public int VERY_HEAVY = 1;

		@Config.RequiresMcRestart
		@Config.Comment("Stack size of Heavy items. Default = 4")
		@Config.LangKey("config." + MODID_TFCSTACKS + ".general.HEAVY")
		public int HEAVY = 16;

		@Config.RequiresMcRestart
		@Config.Comment("Stack size of Medium items. Default = 16")
		@Config.LangKey("config." + MODID_TFCSTACKS + ".general.MEDIUM")
		public int MEDIUM = 32;

		@Config.RequiresMcRestart
		@Config.Comment("Stack size of Light items. Default = 32")
		@Config.LangKey("config." + MODID_TFCSTACKS + ".general.LIGHT")
		public int LIGHT = 64;

		@Config.RequiresMcRestart
		@Config.Comment("Stack size of Very Light items. Default = 64")
		@Config.LangKey("config." + MODID_TFCSTACKS + ".general.VERY_LIGHT")
		public int VERY_LIGHT = 64;

	}
}
