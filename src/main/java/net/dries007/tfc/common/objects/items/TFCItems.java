package net.dries007.tfc.common.objects.items;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.crop.type.CropType;
import net.dries007.tfc.api.types.crop.variant.item.CropItemVariant;
import net.dries007.tfc.api.types.crop.variant.item.ICropItem;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.api.types.food.variant.Item.FoodItemVariant;
import net.dries007.tfc.api.types.food.variant.Item.IFoodItem;
import net.dries007.tfc.api.types.metal.variant.Item.IMetalItem;
import net.dries007.tfc.api.types.metal.variant.Item.MetalItemVariant;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.item.IRockItem;
import net.dries007.tfc.api.types.rock.variant.item.RockItemVariant;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.item.ISoilItem;
import net.dries007.tfc.api.types.soil.variant.item.SoilItemVariant;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.item.IWoodItem;
import net.dries007.tfc.api.types.wood.variant.item.WoodItemVariant;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.common.objects.items.ceramics.ItemMold;
import net.dries007.tfc.common.objects.items.ceramics.ItemUnfiredMold;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static net.dries007.tfc.common.objects.CreativeTabsTFC.WOOD;

public class TFCItems {

    //==== Item ======================================================================================================//

    public static final Map<Pair<RockItemVariant, RockType>, IRockItem> ROCK_ITEMS = new LinkedHashMap<>();
    public static final Map<Pair<SoilItemVariant, SoilType>, ISoilItem> SOIL_ITEMS = new LinkedHashMap<>();
    public static final Map<Pair<WoodItemVariant, WoodType>, IWoodItem> WOOD_ITEMS = new LinkedHashMap<>();
    public static final Map<Pair<MetalItemVariant, Material>, IMetalItem> METAL_ITEMS = new LinkedHashMap<>();
    public static final Map<Pair<CropItemVariant, CropType>, ICropItem> CROP_ITEMS = new LinkedHashMap<>();
    public static final Map<Pair<FoodItemVariant, FoodType>, IFoodItem> FOOD_ITEMS = new LinkedHashMap<>();

    public static final Map<OrePrefix, ItemMold> FIRED_MOLDS = new ConcurrentHashMap<>();
    public static final Map<OrePrefix, ItemUnfiredMold> UNFIRED_MOLDS = new ConcurrentHashMap<>();

    // Предметы
    public static final List<Item> ITEMS = new ArrayList<>();

    public static ItemMisc STRAW;
    public static Item HANDSTONE;
    public static Item WROUGHT_IRON_GRILL;
    public static Item GLUE;
    public static Item SPINDLE;
    public static ItemTFC MORTAR;
    public static ItemTFC HALTER;
    public static Item STICK_BUNCH;
    public static Item STICK_BUNDLE;
    public static Item FIRE_CLAY;
    public static Item WOOD_ASH;
    public static Item FIRESTARTER;
    public static Item GLASS_SHARD;
    public static Item ALABASTER_BRICK;
    public static ItemQuiver QUIVER;
    public static ItemDebug WAND;
    public static ItemBloom UNREFINED_BLOOM;
    public static ItemBloom REFINED_BLOOM;

    private TFCItems() {
    }


    public static void preInit() {

        //==== Molds =================================================================================================//

        for (var orePrefix : OrePrefix.values()) {
            var orePrefixExtension = (IOrePrefixExtension) orePrefix;
            if (orePrefixExtension.getHasMold()) {
                if (UNFIRED_MOLDS.put(orePrefix, new ItemUnfiredMold(orePrefix)) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s", orePrefix));

                if (FIRED_MOLDS.put(orePrefix, new ItemMold(orePrefix)) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s", orePrefix));
            }
        }


        //==== ItemMisc ==============================================================================================//

        ITEMS.add(STRAW = new ItemMisc("straw", Size.SMALL, Weight.VERY_LIGHT, "kindling", "straw"));
        ITEMS.add(WROUGHT_IRON_GRILL = new ItemMisc("wrought_iron_grill", Size.LARGE, Weight.HEAVY, "grill"));
        ITEMS.add(GLUE = new ItemMisc("glue", Size.TINY, Weight.LIGHT, "slimeball", "glue"));
        ITEMS.add(MORTAR = new ItemMisc("mortar", Size.TINY, Weight.VERY_LIGHT, "mortar"));
        ITEMS.add(HALTER = new ItemMisc("halter", Size.SMALL, Weight.LIGHT, "halter"));
        ITEMS.add(STICK_BUNCH = new ItemMisc("stick_bunch", Size.NORMAL, Weight.LIGHT, WOOD));
        ITEMS.add(STICK_BUNDLE = new ItemMisc("stick_bundle", Size.VERY_LARGE, Weight.MEDIUM, WOOD, "log_wood", "stick_bundle"));
        ITEMS.add(FIRE_CLAY = new ItemMisc("ceramics/fire_clay", Size.VERY_SMALL, Weight.VERY_LIGHT, "fire_clay"));
        ITEMS.add(WOOD_ASH = new ItemMisc("wood_ash", Size.VERY_SMALL, Weight.VERY_LIGHT, WOOD, "slimeball", "glue"));
        ITEMS.add(GLASS_SHARD = new ItemMisc("glass_shard", Size.VERY_SMALL, Weight.VERY_LIGHT));
        ITEMS.add(ALABASTER_BRICK = new ItemMisc("alabaster_brick", Size.VERY_SMALL, Weight.LIGHT));


        //==== ItemCraftingTool ======================================================================================//

        ITEMS.add(HANDSTONE = new ItemCraftingTool("handstone", 250, Size.NORMAL, Weight.VERY_HEAVY, "handstone"));
        ITEMS.add(SPINDLE = new ItemCraftingTool("spindle", 40, Size.NORMAL, Weight.MEDIUM, "spindle"));


        //==== Other =================================================================================================//

        ITEMS.add(FIRESTARTER = new ItemFireStarter());
        ITEMS.add(QUIVER = new ItemQuiver());
        ITEMS.add(WAND = new ItemDebug());
        ITEMS.add(UNREFINED_BLOOM = new ItemBloom(false));
        ITEMS.add(REFINED_BLOOM = new ItemBloom(true));


    }


    @Nonnull
    public static Item getWoodItem(@Nonnull WoodItemVariant variant, @Nonnull WoodType type) {
        var item = (Item) WOOD_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }

    @Nonnull
    public static Item getRockItem(@Nonnull RockItemVariant variant, @Nonnull RockType type) {
        var item = (Item) ROCK_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }

    @Nonnull
    public static Item getSoilItem(@Nonnull SoilItemVariant variant, @Nonnull SoilType type) {
        var item = (Item) SOIL_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }

    @Nonnull
    public static Item getMetalItem(@Nonnull MetalItemVariant variant, @Nonnull Material type) {
        var item = (Item) METAL_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }

    @Nonnull
    public static Item getCropItem(@Nonnull CropItemVariant variant, @Nonnull CropType type) {
        var item = (Item) CROP_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }

    @Nonnull
    public static Item getFoodItem(@Nonnull FoodItemVariant variant, @Nonnull FoodType type) {
        var item = (Item) FOOD_ITEMS.get(new Pair<>(variant, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item is null: %s", type));
    }

}
