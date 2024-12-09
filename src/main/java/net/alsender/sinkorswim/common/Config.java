package net.alsender.sinkorswim.common;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alsender on 4/12/17.
 */

public class Config {

  public static Configuration config;

  public static List<String> armorWhiteList = new ArrayList<String>();
  public static List<String> biomeBlacklist = new ArrayList<String>();
  public static List<String> potionWhitelist = new ArrayList<String>();
  public static List<String> enchantWhitelist = new ArrayList<String>();
  public static List<String> baublesWhitelist = new ArrayList<String>();

  public static void init(File configFile) {
    if (config == null) {
      config = new Configuration(configFile);
      load();
    }
  }

  public static void load() {
    config.addCustomCategoryComment("general", "Sink or Swim Configuration Settings");

    String[] armor = config.getStringList("armorWhitelist", "general", new String[]{
      "minecraft:leather_boots",
      "minecraft:leather_leggings",
      "minecraft:leather_chestplate",
      "minecraft:leather_helmet"
    }, "Armor what will not weigh you down in water.");
    for (String sa : armor) {
      armorWhiteList.add(sa);
    }

    String[] biomes = config.getStringList("biomeBlacklist", "general", new String[]{
      "Ocean",
      "Beach",
      "River",
      "Swamp",
      }, "Biomes in which you can't swim.  Simply put down 'All' to disable swimming in all biomes.");
    for (String sb : biomes) {
      biomeBlacklist.add(sb);
    }

    String[] potions = config.getStringList("potionEffectWhitelist", "general", new String[]{
      "waterBreathing",
      "levitation"
    }, "Potion effects that will allow you to swim.");
    for (String ps : potions) {
      potionWhitelist.add(ps);
    }

    String[] enchants = config.getStringList("enchantmentsWhitelist", "general", new String[]{
      "minecraft:depth_strider",
      "minecraft:respiration"
    }, "Armor enchantments that will allow you to swim.");
    for (String es : enchants) {
      enchantWhitelist.add(es);
    }

    String[] baubles = config.getStringList("baublesWhitelist", "general", new String[]{
      "botania:waterring",
      }, "Baubles that will allow you to swim.");
    for (String bs : baubles) {
      baublesWhitelist.add(bs);
    }

    if (config.hasChanged()) {
      config.save();
    }
  }

  @SubscribeEvent
  public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (event.getModID().equalsIgnoreCase(SinkorSwim.modid)) {
      load();
    }
  }
}
