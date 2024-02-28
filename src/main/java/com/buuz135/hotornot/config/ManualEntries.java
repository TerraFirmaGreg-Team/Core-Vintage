package com.buuz135.hotornot.config;


import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RequiresWorldRestart;

import static su.terrafirmagreg.api.lib.Constants.MODID_HOTORNOT;


public class ManualEntries {

	private static final String LANG_KEY = "config." + MODID_HOTORNOT + ".manual_entries.";

	@RequiresWorldRestart
	@Comment("Hot items that are included manually")
	@LangKey(LANG_KEY + "hot_item_additions")
	public String[] hotItemAdditions = {"minecraft:blaze_rod"};

	@RequiresWorldRestart
	@Comment("Cold items that are included manually")
	@LangKey(LANG_KEY + "cold_item_additions")
	public String[] coldItemAdditions = {"minecraft:ice", "minecraft:packed_ice", "tfc:sea_ice"};

	@RequiresWorldRestart
	@Comment("Gaseous items that are included manually")
	@LangKey(LANG_KEY + "gaseous_item_additions")
	public String[] gaseousItemAdditions = {};

	@RequiresWorldRestart
	@Comment("Items that are excluded")
	@LangKey(LANG_KEY + "item_removals")
	public String[] itemRemovals = {"immersiveengineering:drill", "immersiveengineering:chemthrower", "immersivepetroleum:fluid_diesel", "immersivepetroleum:fluid_gasoline"};
}
