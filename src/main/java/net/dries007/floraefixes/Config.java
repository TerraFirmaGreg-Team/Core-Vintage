package net.dries007.floraefixes;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

  public static Configuration config;
  public static boolean ricePlacing;
  public static boolean cropSupplier;
  public static boolean sandwichRecipes;
  public static boolean planterRecipes;

  public static void init(File configDirectory) {
    config = new Configuration(new File(configDirectory, "floraefixes.cfg"));
    ricePlacing = config.getBoolean("rice_placing", "general", true, "Fixes the rice placing in water");
    cropSupplier = config.getBoolean("crop_supplier", "general", true, "Fixes some crop drops not being registered");
    sandwichRecipes = config.getBoolean("sandwich_recipes", "general", true, "Removes the recipes for the sandwiches that don' word");
    planterRecipes = config.getBoolean("planter_recipes", "general", true, "Adds a and fixes a lot of missing or ill defines firmalife planter recipes");
    if (config.hasChanged()) {
      config.save();
    }
  }

}
