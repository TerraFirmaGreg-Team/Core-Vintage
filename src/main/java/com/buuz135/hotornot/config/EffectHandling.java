package com.buuz135.hotornot.config;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeInt;

import static su.terrafirmagreg.Constants.MODID_HOTORNOT;

public final class EffectHandling {

	private static final String LANG_KEY = "config." + MODID_HOTORNOT + ".effect_handling.";
	@LangKey(LANG_KEY + "toss_item")
	@Comment("If true, hot items make the player yeet them")
	public boolean tossItems = false;
	@LangKey(LANG_KEY + "handle_hot_item")
	@Comment("If true, hot effects for items will be enabled")
	public boolean handleHotItems = true;
	@LangKey(LANG_KEY + "handle_hot_fluid")
	@Comment("If true, hot effects for fluids will be enabled")
	public boolean handleHotFluids = true;
	@LangKey(LANG_KEY + "handle_cold_fluid")
	@Comment("If true, cold effects for fluids will be enabled")
	public boolean handleColdFluids = true;
	@LangKey(LANG_KEY + "handle_gas")
	@Comment("If true, gaseous effects for fluids will be enabled")
	public boolean handleGaseousFluids = true;

	@LangKey(LANG_KEY + "replace_broken_hot_holder")
	@Comment("If broken Tongs or Mitts should be automatically replaced for you")
	public boolean replaceBrokenHotHolder = false;
	@RangeInt(min = 0)
	@LangKey(LANG_KEY + "damage_rate")
	@Comment("How often the tools should be damaged in ticks (there are 20 ticks per second). 0 will disable the damaging functionality")
	public int damageRate = 1;
	/**
	 * If we should check the contents of item containers inside player inventories
	 */
	@LangKey(LANG_KEY + "check_item_container_contents")
	@Comment("If we should also check the contents of an item container such as a vessel. This could be expensive!")
	public boolean checkItemContainerContents = false;
	@RangeInt(min = 0)
	@LangKey(LANG_KEY + "container_depth_limit")
	@Comment("The depth limit for the item container check. This could be expensive!")
	public int containerDepthLimit = 1;
}
