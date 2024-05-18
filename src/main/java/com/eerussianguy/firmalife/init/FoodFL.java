package com.eerussianguy.firmalife.init;

import net.dries007.tfc.api.capability.food.FoodData;

public enum FoodFL {
    DARK_CHOCOLATE(FoodData.CHOCOLATE, new String[] { "chocolate" }, false),
    MILK_CHOCOLATE(FoodData.CHOCOLATE, new String[] { "chocolate" }, false),
    WHITE_CHOCOLATE(FoodData.CHOCOLATE, new String[] { "chocolate" }, false),
    COCOA_BEANS(FoodData.COCOA_BEANS),
    PUMPKIN_SCOOPED(FoodData.PUMPKIN),
    PUMPKIN_CHUNKS(FoodData.PUMPKIN),
    PICKLED_EGG(FoodData.PICKLED_EGG),
    DRIED_COCOA_BEANS(FoodData.DRIED_COCOA_BEANS),
    TOAST(FoodData.TOAST),
    GARLIC_BREAD(FoodData.GARLIC_BREAD),
    TOMATO_SAUCE(FoodData.DOUGH),
    CHESTNUT_BREAD(FoodData.FLATBREAD, new String[] { "categoryGrain", "categoryBread" }, true),
    PINEAPPLE(FoodData.COCOA_BEANS),
    PINEAPPLE_CHUNKS(FoodData.PINEAPPLE),
    ACORN_FRUIT(FoodData.NUT, new String[] { "nut" }, false),
    ACORNS(FoodData.UNCRACKED_NUT),
    CHESTNUTS(FoodData.UNCRACKED_NUT),
    ROASTED_CHESTNUTS(FoodData.ROASTED_NUT, new String[] { "nut" }, false),
    CHESTNUT_DOUGH(FoodData.DOUGH),
    CHESTNUT_FLOUR(FoodData.FLOUR),
    PECAN_NUTS(FoodData.UNCRACKED_NUT),
    PECANS(FoodData.NUT, new String[] { "nut" }, false),
    PINECONE(FoodData.UNCRACKED_NUT),
    PINE_NUTS(FoodData.NUT, new String[] { "nut" }, false),
    COCONUT(FoodData.UNCRACKED_NUT),
    MELON(FoodData.MELON),
    TOFU(FoodData.TOFU),
    GROUND_SOYBEANS(FoodData.GROUND_SOYBEANS),
    RAW_HONEY(FoodData.DOUGH, new String[] { "sweetener" }, false),
    MILK_CURD(FoodData.MILK_CURD, new String[] { "categoryDairy" }, false),
    GOAT_CURD(FoodData.MILK_CURD, new String[] { "categoryDairy" }, false),
    YAK_CURD(FoodData.MILK_CURD, new String[] { "categoryDairy" }, false),
    CHEDDAR(FoodData.CHEESE_SALTED, new String[] { "categoryDairy" }, true),
    CHEVRE(FoodData.CHEESE_SALTED, new String[] { "categoryDairy" }, true),
    RAJYA_METOK(FoodData.CHEESE_SALTED, new String[] { "categoryDairy" }, true),
    GOUDA(FoodData.CHEESE_BRINED, new String[] { "categoryDairy" }, true),
    FETA(FoodData.CHEESE_BRINED, new String[] { "categoryDairy" }, true),
    SHOSHA(FoodData.CHEESE_BRINED, new String[] { "categoryDairy" }, true),
    PIZZA_DOUGH(FoodData.DOUGH),
    COOKED_PIZZA(FoodData.FLATBREAD);

    private final FoodData data;
    private final String[] nameOverrides;
    private final boolean replaceOres;

    FoodFL(FoodData data, String[] nameOverrides, boolean replaceOres) {
        this.data = data;
        this.nameOverrides = nameOverrides;
        this.replaceOres = replaceOres;
    }

    FoodFL(FoodData data) {
        this.data = data;
        this.nameOverrides = null;
        this.replaceOres = false;
    }

    public FoodData getData() {
        return data;
    }

    public String[] getNameOverrides() {
        return nameOverrides;
    }

    public boolean isReplacingOres() {
        return replaceOres;
    }
}
