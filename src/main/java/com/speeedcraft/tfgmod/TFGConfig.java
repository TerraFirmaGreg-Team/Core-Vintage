package com.speeedcraft.tfgmod;

import net.minecraftforge.common.config.Config;

@Config(modid = TFGMod.MOD_ID)
public class TFGConfig {

	@Config.Comment("What kind of liquid should a primitive pump extract? If the liquid you specify is not detected, salt water will be extracted. Default: fresh_water")
	@Config.RequiresMcRestart
	public static String fluidForPrimitivePump = "fresh_water";
}
