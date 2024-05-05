package net.sharkbark.cellars.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;


import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemMetalIceSaw;
import net.dries007.tfc.types.DefaultMetals;
import net.sharkbark.cellars.items.ItemToolHead;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.api.data.Constants.MODID_CELLARS;

public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<Item>();

    @ObjectHolder(MODID_CELLARS + ":bronze_ice_saw")
    public static final Item BRONZE_ICE_SAW = null;
    @ObjectHolder(MODID_CELLARS + ":bismuth_bronze_ice_saw")
    public static final Item BISMUTH_BRONZE_ICE_SAW = null;
    @ObjectHolder(MODID_CELLARS + ":black_bronze_ice_saw")
    public static final Item BLACK_BRONZE_ICE_SAW = null;
    @ObjectHolder(MODID_CELLARS + ":wrought_iron_ice_saw")
    public static final Item WROUGHT_IRON_ICE_SAW = null;
    @ObjectHolder(MODID_CELLARS + ":steel_ice_saw")
    public static final Item STEEL_ICE_SAW = null;
    @ObjectHolder(MODID_CELLARS + ":black_steel_ice_saw")
    public static final Item BLACK_STEEL_ICE_SAW = null;
    @ObjectHolder(MODID_CELLARS + ":red_steel_ice_saw")
    public static final Item RED_STEEL_ICE_SAW = null;
    @ObjectHolder(MODID_CELLARS + ":blue_steel_ice_saw")
    public static final Item BLUE_STEEL_ICE_SAW = null;

    @ObjectHolder(MODID_CELLARS + ":bronze_ice_saw_head")
    public static final Item BRONZE_ICE_SAW_HEAD = null;
    @ObjectHolder(MODID_CELLARS + ":bismuth_bronze_ice_saw_head")
    public static final Item BISMUTH_BRONZE_ICE_SAW_HEAD = null;
    @ObjectHolder(MODID_CELLARS + ":black_bronze_ice_saw_head")
    public static final Item BLACK_BRONZE_ICE_SAW_HEAD = null;
    @ObjectHolder(MODID_CELLARS + ":wrought_iron_ice_saw_head")
    public static final Item WROUGHT_IRON_ICE_SAW_HEAD = null;
    @ObjectHolder(MODID_CELLARS + ":steel_ice_saw_head")
    public static final Item STEEL_ICE_SAW_HEAD = null;
    @ObjectHolder(MODID_CELLARS + ":black_steel_ice_saw_head")
    public static final Item BLACK_STEEL_ICE_SAW_HEAD = null;
    @ObjectHolder(MODID_CELLARS + ":red_steel_ice_saw_head")
    public static final Item RED_STEEL_ICE_SAW_HEAD = null;
    @ObjectHolder(MODID_CELLARS + ":blue_steel_ice_saw_head")
    public static final Item BLUE_STEEL_ICE_SAW_HEAD = null;

    public static void registerItems(IForgeRegistry<Item> registry) {
        Item[] saws = new Item[] {
                new ItemMetalIceSaw(Metal.BRONZE, "bronze_ice_saw"),
                new ItemMetalIceSaw(Metal.BISMUTH_BRONZE, "bismuth_bronze_ice_saw"),
                new ItemMetalIceSaw(Metal.BLACK_BRONZE, "black_bronze_ice_saw"),
                new ItemMetalIceSaw(Metal.WROUGHT_IRON, "wrought_iron_ice_saw"),
                new ItemMetalIceSaw(Metal.STEEL, "steel_ice_saw"),
                new ItemMetalIceSaw(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL), "black_steel_ice_saw"),
                new ItemMetalIceSaw(Metal.RED_STEEL, "red_steel_ice_saw"),
                new ItemMetalIceSaw(Metal.BLUE_STEEL, "blue_steel_ice_saw")
        };
        registry.registerAll(saws);
        registry.registerAll(
                new ItemToolHead(Metal.BRONZE, "bronze_ice_saw_head", "icesawBlade"),
                new ItemToolHead(Metal.BISMUTH_BRONZE, "bismuth_bronze_ice_saw_head", "icesawBlade"),
                new ItemToolHead(Metal.BLACK_BRONZE, "black_bronze_ice_saw_head", "icesawBlade"),
                new ItemToolHead(Metal.WROUGHT_IRON, "wrought_iron_ice_saw_head", "icesawBlade"),
                new ItemToolHead(Metal.STEEL, "steel_ice_saw_head", "icesawBlade"),
                new ItemToolHead(TFCRegistries.METALS.getValue(DefaultMetals.BLACK_STEEL), "black_steel_ice_saw_head", "icesawBlade"),
                new ItemToolHead(Metal.RED_STEEL, "red_steel_ice_saw_head", "icesawBlade"),
                new ItemToolHead(Metal.BLUE_STEEL, "blue_steel_ice_saw_head", "icesawBlade")
        );

        for (Item saw : saws) {
            ItemStack stack = new ItemStack(saw, 1, OreDictionary.WILDCARD_VALUE);
            OreDictionary.registerOre("tool", stack);
            OreDictionary.registerOre("damageTypeSlashing", stack);
            OreDictionary.registerOre("icesaw", stack);
        }
    }
}
