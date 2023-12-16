package com.buuz135.hotornot;

import net.minecraftforge.fml.common.Mod;

@Mod(
		modid = HotOrNot.MOD_ID,
		name = HotOrNot.MOD_NAME,
		version = HotOrNot.VERSION,
		dependencies =
				"required-after:tfc;" + "required-after:tfgmod;"
)
public class HotOrNot {
	public static final String MOD_ID = "hotornot";
	public static final String MOD_NAME = "Hot Or Not - TFC";
	public static final String VERSION = "1.7";

}