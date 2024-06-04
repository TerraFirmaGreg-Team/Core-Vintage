package com.eerussianguy.firmalife.init;

import su.terrafirmagreg.api.capabilities.food.spi.FoodTrait;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextFormatting;


import mcp.MethodsReturnNonnullByDefault;

import lombok.Getter;

import static su.terrafirmagreg.api.data.Constants.MODID_FL;

@MethodsReturnNonnullByDefault
public enum AgingFL implements IStringSerializable {
    FRESH("fresh", 0, FoodTrait.FRESH, TextFormatting.GRAY),
    AGED("aged", 4, FoodTrait.AGED, TextFormatting.DARK_RED),
    VINTAGE("vintage", 8, FoodTrait.VINTAGE, TextFormatting.GOLD);

    @Getter
    private final int ID;
    private final String name;
    @Getter
    private final FoodTrait trait;
    @Getter
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

    public String getTranslationKey() {
        return "food_trait." + MODID_FL + "." + this.name;
    }
}
