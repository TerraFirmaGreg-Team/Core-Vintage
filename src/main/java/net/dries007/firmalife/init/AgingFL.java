package net.dries007.firmalife.init;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

import mcp.MethodsReturnNonnullByDefault;

import static net.dries007.firmalife.FirmaLife.MOD_ID;

@MethodsReturnNonnullByDefault
public enum AgingFL implements IStringSerializable {
  FRESH("fresh", 0, FoodTrait.FRESH, TextFormatting.GRAY),
  AGED("aged", 4, FoodTrait.AGED, TextFormatting.DARK_RED),
  VINTAGE("vintage", 8, FoodTrait.VINTAGE, TextFormatting.GOLD);

  private final int ID;
  private final String name;
  private final FoodTrait trait;
  private final TextFormatting format;

  AgingFL(String name, int ID, FoodTrait trait, TextFormatting format) {
    this.ID = ID;
    this.name = name;
    this.trait = trait;
    this.format = format;
  }

  @Override
  public String getName() {
    return this.name;
  }

  public int getID() {
    return this.ID;
  }

  public FoodTrait getTrait() {
    return this.trait;
  }

  public TextFormatting getFormat() {
    return this.format;
  }

  public String getTranslationKey() {
    return "food_trait." + MOD_ID + "." + this.name;
  }
}
