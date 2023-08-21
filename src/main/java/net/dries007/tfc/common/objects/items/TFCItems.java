package net.dries007.tfc.common.objects.items;

import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.crop.type.CropType;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.items.ceramics.ItemMold;
import net.dries007.tfc.common.objects.items.ceramics.ItemUnfiredMold;
import net.dries007.tfc.common.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.common.objects.items.rock.ItemRock;
import net.dries007.tfc.common.objects.items.rock.ItemRockBrick;
import net.dries007.tfc.common.objects.items.wood.ItemWoodBoat;
import net.dries007.tfc.common.objects.items.wood.ItemWoodLumber;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.minecraft.item.Item;

import static net.dries007.tfc.common.objects.CreativeTabsTFC.WOOD;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.*;

public class TFCItems {

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

        //=== Molds ==================================================================================================//

        for (var orePrefix : OrePrefix.values()) {
            var orePrefixExtension = (IOrePrefixExtension) orePrefix;
            if (orePrefixExtension.getHasMold()) {
                if (UNFIRED_MOLDS.put(orePrefix, new ItemUnfiredMold(orePrefix)) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s", orePrefix));

                if (FIRED_MOLDS.put(orePrefix, new ItemMold(orePrefix)) != null)
                    throw new RuntimeException(String.format("Duplicate registry detected: %s", orePrefix));
            }
        }

        //=== ItemRock ===============================================================================================//

        for (var type : RockType.getRockTypes()) {
            if (BRICK_ITEMS.put(type, new ItemRockBrick(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s", type));

            if (ROCK_ITEMS.put(type, new ItemRock(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s", type));
        }

        //=== ItemWood ===============================================================================================//

        for (var type : WoodType.getWoodTypes()) {
            if (LUMBER_ITEMS.put(type, new ItemWoodLumber(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s", type));

            if (BOAT_ITEMS.put(type, new ItemWoodBoat(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s", type));
        }

        //=== ItemSeed ===============================================================================================//

        for (var type : CropType.getCropTypes()) {
            if (SEED_ITEMS.put(type, new ItemCropSeeds(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s", type));
        }

        //=== ItemFood ===============================================================================================//

        for (var type : FoodType.getFoodType()) {
            if (FOOD_ITEMS.put(type, new ItemFoodTFC(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s", type));
        }

        //=== ItemMisc ===============================================================================================//

        ITEM.add(STRAW = new ItemMisc("straw", Size.SMALL, Weight.VERY_LIGHT, "kindling", "straw"));
        ITEM.add(WROUGHT_IRON_GRILL = new ItemMisc("wrought_iron_grill", Size.LARGE, Weight.HEAVY, "grill"));
        ITEM.add(GLUE = new ItemMisc("glue", Size.TINY, Weight.LIGHT, "slimeball", "glue"));
        ITEM.add(MORTAR = new ItemMisc("mortar", Size.TINY, Weight.VERY_LIGHT, "mortar"));
        ITEM.add(HALTER = new ItemMisc("halter", Size.SMALL, Weight.LIGHT, "halter"));
        ITEM.add(STICK_BUNCH = new ItemMisc("stick_bunch", Size.NORMAL, Weight.LIGHT, WOOD));
        ITEM.add(STICK_BUNDLE = new ItemMisc("stick_bundle", Size.VERY_LARGE, Weight.MEDIUM, WOOD, "log_wood", "stick_bundle"));
        ITEM.add(FIRE_CLAY = new ItemMisc("ceramics/fire_clay", Size.VERY_SMALL, Weight.VERY_LIGHT, "fire_clay"));
        ITEM.add(WOOD_ASH = new ItemMisc("wood_ash", Size.VERY_SMALL, Weight.VERY_LIGHT, WOOD, "slimeball", "glue"));
        ITEM.add(GLASS_SHARD = new ItemMisc("glass_shard", Size.VERY_SMALL, Weight.VERY_LIGHT));
        ITEM.add(ALABASTER_BRICK = new ItemMisc("alabaster_brick", Size.VERY_SMALL, Weight.LIGHT));


        //=== ItemCraftingTool =======================================================================================//

        ITEM.add(HANDSTONE = new ItemCraftingTool("handstone", 250, Size.NORMAL, Weight.VERY_HEAVY, "handstone"));
        ITEM.add(SPINDLE = new ItemCraftingTool("spindle", 40, Size.NORMAL, Weight.MEDIUM, "spindle"));


        //=== Other ==================================================================================================//

        ITEM.add(FIRESTARTER = new ItemFireStarter());
        ITEM.add(QUIVER = new ItemQuiver());
        ITEM.add(WAND = new ItemDebug());
        ITEM.add(UNREFINED_BLOOM = new ItemBloom(false));
        ITEM.add(REFINED_BLOOM = new ItemBloom(true));
    }
}
