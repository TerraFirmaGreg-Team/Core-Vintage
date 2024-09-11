package com.buuz135.hotornot.config;

import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;


import static su.terrafirmagreg.data.Constants.MODID_HOTORNOT;

public final class TemperatureValues {

  private static final String LANG_KEY = "config." + MODID_HOTORNOT + ".temperature_values.";

  @LangKey(LANG_KEY + "hot_fluid_temp")
  @Comment("How hot a fluid should be to be considered too hot to hold without protection")
  public int hotFluidTemp = 480;

  @LangKey(LANG_KEY + "cold_fluid_temp")
  @Comment("How cold a fluid should be to be considered too cold to hold without protection")
  public int coldFluidTemp = 0;

  @LangKey(LANG_KEY + "hot_item_temp")
  @Comment("How hot an item should be to start burning the player")
  public int hotItemTemp = 480;
}
