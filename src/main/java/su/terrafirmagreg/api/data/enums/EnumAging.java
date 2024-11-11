package su.terrafirmagreg.api.data.enums;

import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;

import lombok.Getter;

import static su.terrafirmagreg.api.data.Reference.MODID_FL;

@Getter
public enum EnumAging implements IStringSerializable {
  FRESH("fresh", 0, FoodTrait.FRESH, TextFormatting.GRAY),
  AGED("aged", 4, FoodTrait.AGED, TextFormatting.DARK_RED),
  VINTAGE("vintage", 8, FoodTrait.VINTAGE, TextFormatting.GOLD);
  
  private final int ID;
  private final String name;
  private final FoodTrait trait;
  private final TextFormatting format;

  EnumAging(String name, int ID, FoodTrait trait, TextFormatting format) {
    this.ID = ID;
    this.name = name;
    this.trait = trait;
    this.format = format;
  }


  public String getTranslationKey() {
    return "food_trait." + MODID_FL + "." + this.name;
  }
}
